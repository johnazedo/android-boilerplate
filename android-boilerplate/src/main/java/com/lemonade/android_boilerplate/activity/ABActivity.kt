package com.lemonade.android_boilerplate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.lemonade.android_boilerplate.viewmodel.ABAction
import com.lemonade.android_boilerplate.viewmodel.ABViewModel
import com.lemonade.android_boilerplate.viewmodel.Action
import com.lemonade.android_boilerplate.viewmodel.PresentationError
import com.lemonade.android_boilerplate.viewmodel.State

abstract class ABActivity: AppCompatActivity() {

    private lateinit var navController: NavController
    protected abstract var fragmentContainerId: Int
    protected abstract var layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = getNavHostFragment().navController
    }

    protected fun navigateTo(block: () -> Destination) {
        val args = block()
        if(args.destinationId == Destination.NAVIGATE_BACK) {
            navController.popBackStack()
            return
        }
        navController.navigate(args.destinationId, args.bundle, args.options)
    }

    protected inline fun <reified S: State, reified A: Action> onReceiveEvent(
        viewModel: ABViewModel<S, A>,
        crossinline handleState: (S) -> Unit = {},
        crossinline handleAction: (A) -> Unit = {},
        crossinline handleError: (PresentationError) -> Unit = {}
    ) {
        viewModel.getState.observe(this) { state -> handleState(state) }
        viewModel.getAction.observe(this) { wrapper ->
            wrapper.onValidContent { abAction ->
                when (abAction) {
                    is ABAction.PerformAction<A> -> handleAction(abAction.action)
                    is ABAction.ShowError<A> -> handleError(abAction.error)
                    is ABAction.NavigateTo<A> -> navigateTo { abAction.destination }
                }
            }
        }
    }

    private fun getNavHostFragment(): NavHostFragment {
        try {
            val navHostFragment = supportFragmentManager
                .findFragmentById(fragmentContainerId) as NavHostFragment
            return navHostFragment
        } catch (e: TypeCastException) {
            throw NoFragmentContainerViewIdException()
        }
    }
}