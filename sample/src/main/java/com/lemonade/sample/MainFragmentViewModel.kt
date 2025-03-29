package com.lemonade.sample

import com.lemonade.android_boilerplate.viewmodel.ABViewModel

class MainFragmentViewModel: ABViewModel.Minimal() {

    fun navigateToHomeFragment() {
        navigateTo { HomeFragmentDestination() }
    }

    fun navigateToLoginFragment() {
        navigateTo { LoginFragmentDestination() }
    }
}