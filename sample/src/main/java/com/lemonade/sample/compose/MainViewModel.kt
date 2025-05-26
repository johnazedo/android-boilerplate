package com.lemonade.sample.compose

import com.lemonade.android_boilerplate_compose.ABViewModel
import com.lemonade.android_boilerplate_compose.State

data class MainState(
    val contents: List<Images> = listOf()
): State() {
    companion object {
        val INITIAL_STATE = MainState()
    }
}

data class Images(
    val name: String? = null,
    val image: String? = null,
)

class MainViewModel: ABViewModel.OnlyState<MainState>(
    MainState.INITIAL_STATE
) {
    fun getData() {
        updateState {
            it.copy(contents = listOf(
                Images("test1", ""),
                Images("test2", ""),
                Images("test3", ""),
                Images("test4", ""),
                Images("test5", ""),
                Images("test6", ""),
                Images("test7", ""),
                Images("test8", ""),
                Images("test9", ""),
                Images("test10", ""),
                Images("test11", ""),
                Images("test12", "")
            ))
        }
    }
}