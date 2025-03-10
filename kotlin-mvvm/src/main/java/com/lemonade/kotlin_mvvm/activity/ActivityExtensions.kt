package com.lemonade.kotlin_mvvm.activity

import androidx.activity.ComponentActivity
import com.lemonade.kotlin_mvvm.viewmodel.Action
import com.lemonade.kotlin_mvvm.viewmodel.ActionViewModel
import com.lemonade.kotlin_mvvm.viewmodel.FullViewModel
import com.lemonade.kotlin_mvvm.viewmodel.PresentationError
import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel
import com.lemonade.kotlin_mvvm.viewmodel.OneShotWrapper

/**
 * ComponentActivity.onStateChange simplifies the process of observing state changes in a [ComponentActivity]. It
 * automatically handles the observation lifecycle and ensures that the [handleState] callback is
 * executed whenever a new state of type [S] is emitted by the [viewModel].
 *
 * @param viewModel The [StateViewModel] to observe for state changes.
 * @param handleState The callback function to be executed when a new state is emitted.
 *                    It takes a single parameter of type [S], representing the new state.
 * @param S The type of the state being observed, must implement [State].
 */
inline fun <reified S : State> ComponentActivity.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleState: (S) -> Unit,
) = viewModel.getState.observe(this) { state -> handleState(state) }

/**
 * ComponentActivity.onActionChange simplifies the process of observing actions in a [ComponentActivity]. It
 * automatically handles the observation lifecycle and ensures that the [handleAction] callback is
 * executed whenever a new action of type [A] is emitted by the [viewModel]. It uses
 * [OneShotWrapper] to ensure that actions are only handled once.
 *
 * @param viewModel The [FullViewModel] to observe for action changes.
 * @param handleAction The callback function to be executed when a new action is emitted.
 *                     It takes a single parameter of type [A], representing the new action.
 * @param S The type of the state managed by the [FullViewModel], must implement [State].
 * @param A The type of the action being observed, must implement [Action].
 */
inline fun <reified S : State, reified A : Action> ComponentActivity.onActionChange(
    viewModel: FullViewModel<S, A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.onValidContent { action ->
        handleAction(action)
    }
}

/**
 * ComponentActivity.onActionChange simplifies the process of observing actions in a [ComponentActivity]. It
 * automatically handles the observation lifecycle and ensures that the [handleAction] callback is
 * executed whenever a new action of type [A] is emitted by the [viewModel]. It uses
 * [OneShotWrapper] to ensure that actions are only handled once.
 *
 * @param viewModel The [ActionViewModel] to observe for action changes.
 * @param handleAction The callback function to be executed when a new action is emitted.
 *                     It takes a single parameter of type [A], representing the new action.
 * @param A The type of the action being observed, must implement [Action].
 */
inline fun <reified S : State, reified A : Action> ComponentActivity.onActionChange(
    viewModel: ActionViewModel<A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.onValidContent { action ->
        handleAction(action)
    }
}

/**
 * ComponentActivity.onStateChange simplifies the process of observing both state changes and errors in a
 * [ComponentActivity]. It automatically handles the observation lifecycle and ensures that the
 * [handleState] callback is executed whenever a new state of type [S] is emitted, and the
 * [handleError] callback is executed whenever a [PresentationError] is emitted. It uses
 * [OneShotWrapper] to ensure that errors are only handled once.
 *
 * @param viewModel The [StateViewModel] to observe for state changes and errors.
 * @param handleError The callback function to be executed when a [PresentationError] is emitted.
 *                    It takes a single parameter of type [PresentationError].
 * @param handleState The callback function to be executed when a new state is emitted.
 *                    It takes a single parameter of type [S], representing the new state.
 * @param S The type of the state being observed, must implement [State].
 */
inline fun <reified S : State> ComponentActivity.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleError: (PresentationError) -> Unit,
    crossinline handleState: (S) -> Unit,
) {
    viewModel.getState.observe(this) { state -> handleState(state) }
    viewModel.getError.observe(this) { wrapper ->
        wrapper.onValidContent { error ->
            handleError(error)
        }
    }
}
