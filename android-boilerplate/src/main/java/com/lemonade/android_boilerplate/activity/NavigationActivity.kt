package com.lemonade.android_boilerplate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class NavigationActivity: AppCompatActivity() {

    private lateinit var navController: NavController
    protected abstract var fragmentContainerId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = getNavHostFragment().navController
    }

    protected fun navigateTo(args: DirectionArgs){
        navController.navigate(args.destinationId, args.bundle, args.options)
    }

    private fun getNavHostFragment(): NavHostFragment {
        try {
            val navHostFragment = supportFragmentManager
                .findFragmentById(fragmentContainerId) as NavHostFragment
            return navHostFragment
        } catch (e: TypeCastException) {
            throw Throwable(FRAGMENT_CONTAINER_ERROR_MESSAGE)
        }
    }

    companion object {
        const val FRAGMENT_CONTAINER_ERROR_MESSAGE =
            "Please add the id of your FragmentContainerView with navGraph in this activity"
    }
}