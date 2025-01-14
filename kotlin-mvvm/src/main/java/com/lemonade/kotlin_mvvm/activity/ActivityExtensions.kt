package com.lemonade.kotlin_mvvm.activity

import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.lemonade.kotlin_mvvm.viewmodel.Action
import com.lemonade.kotlin_mvvm.viewmodel.FullViewModel
import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel

inline fun <reified S: State> ComponentActivity.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleState: (S) -> Unit,
) = viewModel.getState.observe(this as LifecycleOwner) {
        state -> handleState(state)
}

inline fun <reified S: State, reified A: Action> ComponentActivity.onActionChange(
    viewModel: FullViewModel<S, A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this as LifecycleOwner) { wrapper ->
    wrapper.getContent()?.let { action ->
        handleAction(action)
    }
}