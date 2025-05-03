package com.lemonade.android_boilerplate_compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * State is a class that represents the state of screen information
 */
open class State

/**
 * Action is a class that represents events (of type [Event]) that viewModel delegate.
 */
open class Action(
    /**
     * key is a unique identifier for the action. This is important
     * when LaunchEffect is used in compose
     */
    val key: String = UUID.randomUUID().toString()
)

/**
 * ABViewModel is a base class for all ViewModels in the application.
 * The generic types must be children of [State] and [Action].
 *
 * @param initialState The initial state of the ViewModel.
 */
open class ABViewModel<S: State, A: Action>(
    initialState: S
): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event = MutableStateFlow<Event<Action>>(Event.of(DefaultAction()))
    val event = _event.asStateFlow()

    /**
     * updateState is a function that is used to update the state.
     * The type of callback's return, passed by parameter, must be children of [State].
     * The type of callback's parameter, passed by parameter, must be children of [State].
     *
     * @param block The block of code with [State] parameter that return a [State]
     */
    fun updateState(block: (state: S) -> S) {
        _state.update { block(it) }
    }

    /**
     * performAction is a function that is used to perform an action.
     * The type of callback's return, passed by parameter, must be children of [Action].
     *
     * @param block The block of code that return a [Action]
     */
    fun performAction(block: () -> A) {
        _event.update { Event.of(block()) }
    }

    /**
     * OnlyState is a subclass of ABViewModel that only handles with state.
     * When this subclass is used you do not have to implement the [ABViewModel.performAction] or
     * [EventLaunchEffect]. The generic type must be children of [State].
     *
     * @param initialState The initial state of the ViewModel.
     */
    class OnlyState<S: State>(
        initialState: S
    ): ABViewModel<S, NoUsedAction>(initialState)

    class DefaultAction: Action(DEFAULT_KEY)
    class NoUsedAction: Action(DEFAULT_KEY)

    companion object {
        const val DEFAULT_KEY = "DEFAULT_KEY"
    }
}