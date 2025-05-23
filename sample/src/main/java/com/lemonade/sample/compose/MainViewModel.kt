package com.lemonade.sample.compose

import com.lemonade.android_boilerplate_compose.ABViewModel
import com.lemonade.android_boilerplate_compose.State

data class MainState(
    val name: String? = null,
    val image: String? = null
): State() {
    companion object {
        val INITIAL_STATE = MainState()
    }
}

class MainViewModel: ABViewModel.OnlyState<MainState>(
    MainState.INITIAL_STATE
) {
    
}