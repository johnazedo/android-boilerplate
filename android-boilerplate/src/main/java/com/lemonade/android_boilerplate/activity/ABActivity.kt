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

    protected lateinit var navController: NavController
    protected abstract var fragmentContainerId: Int
    protected abstract var layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = getNavHostFragment().navController
    }

    /**
     * navigateTo provides a convenient way to navigate to a destination. If the destinationId is
     * navigation back (-1), it will pop the back stack. Otherwise, it will navigate to the destination
     * sent in block parameter.
     *
     * @param block A function that returns a [Destination] object.
     */
    protected fun navigateTo(block: () -> Destination) {
        val args = block()
        if(args.destinationId == Destination.NAVIGATE_BACK) {
            navController.popBackStack()
            return
        }
        navController.navigate(args.destinationId, args.bundle, args.options)
    }

    /**
     * onReceiveEvent must be add into onCreate of your activity.
     *
     * @param viewModel The ViewModel associated with the activity.
     * @param handleState A function to handle state changes.
     * It is not mandatory and the default value is an empty lambda.
     * @param handleAction A function to handle action events.
     * It is not mandatory and the default value is an empty lambda.
     * @param handleError A function to handle presentation errors.
     * It is not mandatory and the default value is an empty lambda.
     */
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

    /**
     * getNavHostFragment provides a convenient way to access the NavHostFragment associated with activity.
     * This function get the fragmentContainerID and try to cast it to NavHostFragment, to use its
     * navController for all navigation's.
     *
     * @return The NavHostFragment associated with the activity.
     * @throws NoFragmentContainerViewIdException if the fragmentContainerId cannot be cast to NavHostFragment..
     */
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