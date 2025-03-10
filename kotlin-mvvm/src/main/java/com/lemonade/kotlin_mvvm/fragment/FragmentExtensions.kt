package com.lemonade.kotlin_mvvm.fragment

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.lemonade.kotlin_mvvm.viewmodel.Action
import com.lemonade.kotlin_mvvm.viewmodel.ActionViewModel
import com.lemonade.kotlin_mvvm.viewmodel.FullViewModel
import com.lemonade.kotlin_mvvm.viewmodel.PresentationError
import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel
import com.lemonade.kotlin_mvvm.viewmodel.OneShotWrapper

/**
 * Fragment.onStateChange simplifies the process of observing state changes in a Fragment. It automatically
 * handles the observation lifecycle and ensures that the [handleState] callback is executed
 * whenever a new state of type [S] is emitted by the [viewModel].
 *
 * @param viewModel The [StateViewModel] to observe for state changes.
 * @param handleState The callback function to be executed when a new state is emitted.
 *                    It takes a single parameter of type [S], representing the new state.
 * @param S The type of the state being observed, must implement [State].
 */
inline fun <reified S : State> Fragment.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleState: (S) -> Unit,
) = viewModel.getState.observe(this) { state -> handleState(state) }

/**
 * Fragment.onActionChange simplifies the process of observing actions in a Fragment. It automatically
 * handles the observation lifecycle and ensures that the [handleAction] callback is executed
 * whenever a new action of type [A] is emitted by the [viewModel]. It uses [OneShotWrapper] to
 * ensure that actions are only handled once.
 *
 * @param viewModel The [FullViewModel] to observe for action changes.
 * @param handleAction The callback function to be executed when a new action is emitted.
 *                     It takes a single parameter of type [A], representing the new action.
 * @param S The type of the state managed by the [FullViewModel], must implement [State].
 * @param A The type of the action being observed, must implement [Action].
 */
inline fun <reified S : State, reified A : Action> Fragment.onActionChange(
    viewModel: FullViewModel<S, A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.onValidContent { action ->
        handleAction(action)
    }
}

/**
 * Fragment.onActionChange simplifies the process of observing actions in a Fragment. It automatically
 * handles the observation lifecycle and ensures that the [handleAction] callback is executed
 * whenever a new action of type [A] is emitted by the [viewModel]. It uses [OneShotWrapper] to
 * ensure that actions are only handled once.
 *
 * @param viewModel The [ActionViewModel] to observe for action changes.
 * @param handleAction The callback function to be executed when a new action is emitted.
 *                     It takes a single parameter of type [A], representing the new action.
 * @param A The type of the action being observed, must implement [Action].
 */
inline fun <reified S : State, reified A : Action> Fragment.onActionChange(
    viewModel: ActionViewModel<A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.onValidContent { action ->
        handleAction(action)
    }
}


/**
 * Fragment.onStateChange simplifies the process of observing both state changes and errors in a Fragment.
 * It automatically handles the observation lifecycle and ensures that the [handleState] callback
 * is executed whenever a new state of type [S] is emitted, and the [handleError] callback is
 * executed whenever a [PresentationError] is emitted. It uses [OneShotWrapper] to ensure that
 * errors are only handled once.
 *
 * @param viewModel The [StateViewModel] to observe for state changes and errors.
 * @param handleError The callback function to be executed when a [PresentationError] is emitted.
 *                    It takes a single parameter of type [PresentationError].
 * @param handleState The callback function to be executed when a new state is emitted.
 *                    It takes a single parameter of type [S], representing the new state.
 * @param S The type of the state being observed, must implement [State].
 */
inline fun <reified S : State> Fragment.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleError: (PresentationError) -> Unit,
    crossinline handleState: (S) -> Unit
) {
    viewModel.getState.observe(this) { state -> handleState(state) }
    viewModel.getError.observe(this) { wrapper ->
        wrapper.onValidContent { error ->
            handleError(error)
        }
    }
}

/**
 * Fragment.supportActivity This function provides a convenient way to access the activity that hosts this fragment,
 * casting it to [AppCompatActivity]. If the fragment is not attached to an activity or if
 * the activity is not an instance of [AppCompatActivity], it returns null.
 *
 * @return The [AppCompatActivity] associated with this fragment, or null if not available.
 */
fun Fragment.supportActivity(): AppCompatActivity? {
    return activity as? AppCompatActivity
}

/**
 * Fragment.supportActionBar provides a convenient way to access the action bar of the hosting activity.
 * It first retrieves the [AppCompatActivity] using [supportActivity] and then accesses its
 * [ActionBar]. If the fragment is not attached to an [AppCompatActivity] or if the activity
 * does not have an action bar, it returns null.
 *
 * @return The [ActionBar] associated with the hosting activity, or null if not available.
 */
fun Fragment.supportActionBar(): ActionBar? {
    return supportActivity()?.supportActionBar
}

/**
 * Fragment.setSupportActionBar provides a convenient way to set a custom toolbar as the action bar for the
 * hosting activity. It first retrieves the [AppCompatActivity] using [supportActivity] and then
 * calls [AppCompatActivity.setSupportActionBar] to set the provided [toolbar] as the action bar.
 *
 * @param toolbar The [Toolbar] to be set as the support action bar.
 */
fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    supportActivity()?.setSupportActionBar(toolbar)
}
