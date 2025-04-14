package com.lemonade.android_boilerplate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemonade.android_boilerplate.activity.Destination

@Deprecated(
    message = "PresentationError is deprecated",
    replaceWith = ReplaceWith("UIError")
)
/**
 * PresentationError is a class that represents errors that
 * need be shown to the user.
 */
abstract class PresentationError(
    val message: String,
)

/**
 * UIError is a class that represents errors that
 * need be shown to the user.
 */
abstract class UIError {
    val message: String? = null
    val resID: Int? = null
}

/**
 * State is a class that represents the state of information's
 * used in a screen (activity or fragment). The last state emitted should be
 * saved to avoid lost of information, differently from actions
 * that a one time events.
 *
 * It is recommended use State as a data class
 */
abstract class State

/**
 * Action is a class that represents events that viewModel delegate
 * to activity or fragment and is not a state. This can be, for example,
 * open a BottomSheet, show a Toast, execute specific function etc.
 *
 * It is recommended use Action as sealed class
 */
abstract class Action

/**
 * ABAction is a sealed class that represents all types of
 * actions that can be performed in the application.
 */
sealed class ABAction<A: Action> {
    data class ShowError<A: Action>(val error: UIError): ABAction<A>()
    data class NavigateTo<A: Action>(val destination: Destination): ABAction<A>()
    data class PerformAction<A: Action>(val action: A): ABAction<A>()
}

/**
 * ABViewModel is a base class for all ViewModels in the application.
 *
 * @param initialState The initial state of the ViewModel.
 */
open class ABViewModel<S: State, A: Action>(
    initialState: S
): ViewModel() {
    private val _state = MutableLiveData(initialState)
    val getState: LiveData<S> = _state

    private val _action = MutableLiveData<OneShotWrapper<ABAction<A>>>()
    val getAction: LiveData<OneShotWrapper<ABAction<A>>> = _action

    protected fun setState(block: (state: S) -> S) {
        if(getState.value == null) throw NotNullStateException()
        val newState = block(getState.value!!)
        _state.value = newState
    }

    protected fun performAction(block: () -> A) {
        val newAction = block()
        _action.value = OneShotWrapper.of(
            ABAction.PerformAction(newAction)
        )
    }

    protected fun navigateTo(block: () -> Destination) {
        val newDestination = block()
        _action.value = OneShotWrapper.of(
            ABAction.NavigateTo(newDestination)
        )
    }

    protected fun showError(block: () -> UIError) {
        val newError = block()
        _action.value = OneShotWrapper.of(
            ABAction.ShowError(newError)
        )
    }

    class NoUsedAction: Action()
    class NoUsedState: State()

    /**
     * OnlyState is a subclass of ABViewModel that only handles
     * changes of state, directions and errors.
     *
     * @param initialState The initial state of the ViewModel.
     */
    open class OnlyState<S: State>(
        initialState: S
    ) : ABViewModel<S, NoUsedAction>(initialState)

    /**
     * OnlyAction is a subclass of ABViewModel that only handles
     * changes of action, directions and errors.
     */
    open class OnlyAction<A: Action> :
        ABViewModel<State, A>(NoUsedState())

    /**
     * Minimal is a subclass of ABViewModel that
     * only handles changes of directions and errors.
     */
    open class Minimal: ABViewModel<NoUsedState, NoUsedAction>(NoUsedState())
}

class NotNullStateException: Throwable("Initial state cannot be null. Please, " +
        "use ABViewModel.OnlyState or ABViewModel to use setState function")