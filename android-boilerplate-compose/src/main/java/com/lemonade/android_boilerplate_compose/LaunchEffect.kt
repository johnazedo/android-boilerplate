package com.lemonade.android_boilerplate_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

/**
 * EventLaunchEffect observes a one-time [Event] of type [Action]
 * and launches a coroutine to handle the action using the provided [block].
 *
 * This function ensures that the [block] is only triggered once per unique action,
 * using the [Action.key] as a stable key for [LaunchedEffect].
 *
 * It also filters out default or placeholder actions based on [ABViewModel.DEFAULT_KEY],
 * preventing accidental execution of the [block] on irrelevant or default events.
 *
 * @param event The [Event] wrapper containing an [Action] to be handled. It ensures
 *              that the action is consumed only once.
 * @param block A suspend lambda with a [CoroutineScope] receiver that defines the
 *              behavior to execute when the [Action] is received.
 */
@Composable
fun EventLaunchEffect(event: Event<Action>, block: suspend CoroutineScope.(Action) -> Unit) {
    val action = event.getContent() ?: return
    if(action.key == ABViewModel.DEFAULT_KEY) return
    LaunchedEffect(action.key) {
        block(action)
    }
}