package com.lemonade.kotlin_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/**
* StateApproach is a pattern to create every view model with Live Data.
* The state is composed of all dynamic information (information that
* could be changed) posted on screen.
*
* REMEMBER: The state must be a data class and the method .copy() must
* be used to update only the target parameter.
*/
open class StateApproach<S: State>(
    private val initialState: S
) {

    private val _state = MutableLiveData(initialState)
    val getState: LiveData<S> = _state

    private val _error = MutableLiveData<OneShotWrapper<PresentationError>>()

    protected fun updateState(block: () -> S) {
        val newState = block()
        _state.value = newState
    }

    protected fun showError(block: () -> PresentationError) {
        val newError = block()
        _error.value = OneShotWrapper(newError)
    }
}


/**
 * ActionApproach is a pattern to create every view model with Live Data.
 * The action is composed of actions that will be perform something on screen, like show
 * a bottom sheet, close a fragment/activity, navigate to other screen etc...
 *
 * It is recommend use this with a sealed class or enum.
 */
open class ActionApproach<A: Action> {
    private val _action = MutableLiveData<OneShotWrapper<A>>()

    protected fun performAction(block: () -> A) {
        val newAction = block()
        _action.value = OneShotWrapper(newAction)
    }
}


open class PresentationError
open class State
open class Action