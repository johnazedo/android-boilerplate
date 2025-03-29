package com.lemonade.android_boilerplate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemonade.android_boilerplate.activity.Destination

abstract class PresentationError
abstract class State
abstract class Action


sealed class ABAction<A: Action> {
    data class ShowError<A: Action>(val error: PresentationError): ABAction<A>()
    data class NavigateTo<A: Action>(val destination: Destination): ABAction<A>()
    data class PerformAction<A: Action>(val action: A): ABAction<A>()
}

open class ABViewModel<S: State, A: Action>(
    initialState: S
) {
    private val _state = MutableLiveData(initialState)
    val getState: LiveData<S> = _state

    private val _action = MutableLiveData<OneShotWrapper<ABAction<A>>>()
    val getAction: LiveData<OneShotWrapper<ABAction<A>>> = _action

    protected fun setState(block: () -> S) {
        val newState = block()
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

    protected fun showError(block: () -> PresentationError) {
        val newError = block()
        _action.value = OneShotWrapper.of(
            ABAction.ShowError(newError)
        )
    }

    class NoUsedAction: Action()
    class NoUsedState: State()

    open class OnlyState<S: State>(
        initialState: S
    ) : ABViewModel<S, NoUsedAction>(initialState)

    open class OnlyAction<A: Action> :
        ABViewModel<State, A>(NoUsedState())

    open class Minimal: ABViewModel<NoUsedState, NoUsedAction>(NoUsedState())
}