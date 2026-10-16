package com.lemonade.android_boilerplate_compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * State is a class that represents the state of screen information.
 */
open class State

/**
 * Action is the base class for one-shot events the ViewModel delegates to the UI.
 *
 * Concrete actions should be declared as `sealed` / `data class`es so that
 * equality, `copy`, and `hashCode` are derived from the action's own data.
 * There is no `key` field on purpose: the [ABViewModel] wraps every emitted
 * action in a fresh [Event] instance, and that identity change is what drives
 * the [androidx.compose.runtime.LaunchedEffect] in the UI side.
 */
open class Action

/**
 * ABViewModel is a base class for all ViewModels in the application.
 * The generic types must be children of [State] and [Action].
 *
 * The ViewModel exposes two hot flows:
 *  - [state]: a [StateFlow] of the current UI state (multi-collector, latest
 *    value always available).
 *  - [action]: a [StateFlow] of [Event]s wrapping [Action]s, used to deliver
 *    one-shot events to the UI. Each emission must be a *new* [Event] instance
 *    so that StateFlow's equality-based change detection re-fires.
 *
 * @param initialState The initial state of the ViewModel.
 */
open class ABViewModel<S: State, A: Action>(
    initialState: S
): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    /**
     * Current pending action, wrapped in an [Event] so it can only be consumed
     * once. `null` means "nothing to deliver".
     *
     * Because StateFlow emits only when `value` changes by equality, every
     * call to [performAction] must wrap the produced [Action] in a brand-new
     * [Event] — otherwise the emission would be silently dropped.
     */
    private val _action = MutableStateFlow<Event<A>?>(null)
    val action: StateFlow<Event<A>?> = _action.asStateFlow()

    /**
     * updateState is used to update the state.
     *
     * @param block A function that takes the current state and returns the
     *              next state.
     */
    fun updateState(block: (state: S) -> S) {
        _state.update { block(it) }
    }

    /**
     * Pushes a new one-shot [Action] to the UI.
     *
     * The action is wrapped in a fresh [Event] so the StateFlow detects it as
     * a different value and re-emits, even if a structurally identical action
     * was emitted previously.
     *
     * @param block A factory that returns the [Action] to emit.
     */
    fun performAction(block: () -> A) {
        _action.value = Event.of(block())
    }

    /**
     * Marks the current action as handled by setting it back to `null`.
     *
     * The UI must call this after consuming an [Event] — otherwise the same
     * action will be re-delivered on the next recomposition / configuration
     * change. [EventLaunchEffect] does this for you when used as designed.
     */
    fun consumeAction() {
        _action.value = null
    }

    /**
     * OnlyState is a subclass of ABViewModel that only handles state.
     * When this subclass is used you do not have to implement the
     * [ABViewModel.performAction] or [EventLaunchEffect]. The generic type
     * must be a child of [State].
     *
     * @param initialState The initial state of the ViewModel.
     */
    open class OnlyState<S: State>(
        initialState: S
    ): ABViewModel<S, NoUsedAction>(initialState)


    /**
     * OnlyAction is a subclass of ABViewModel that only handles one-time events.
     * When this subclass is used you have access to [ABViewModel.performAction]
     * and [EventLaunchEffect]. The generic type must be a child of [Action].
     */
    class OnlyAction<A: Action>: ABViewModel<NoUsedState, A>(NoUsedState())

    class NoUsedAction: Action()
    class NoUsedState: State()
}