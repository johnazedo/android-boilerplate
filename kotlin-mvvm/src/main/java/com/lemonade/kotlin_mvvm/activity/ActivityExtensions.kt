package com.lemonade.kotlin_mvvm.activity

import androidx.activity.ComponentActivity
import com.lemonade.kotlin_mvvm.viewmodel.Action
import com.lemonade.kotlin_mvvm.viewmodel.FullViewModel
import com.lemonade.kotlin_mvvm.viewmodel.PresentationError
import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel


inline fun <reified S: State> ComponentActivity.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleState: (S) -> Unit,
) = viewModel.getState.observe(this) {
        state -> handleState(state)
}

inline fun <reified S: State, reified A: Action> ComponentActivity.onActionChange(
    viewModel: FullViewModel<S, A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.onValidContent {
        action -> handleAction(action)
    }
}

inline fun <reified S: State> ComponentActivity.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleError: (PresentationError) -> Unit,
    crossinline handleState: (S) -> Unit,
)  {
    viewModel.getState.observe(this) { state -> handleState(state) }
    viewModel.getError.observe(this) { wrapper ->
        wrapper.onValidContent { error ->
            handleError(error)
        }
    }
}
