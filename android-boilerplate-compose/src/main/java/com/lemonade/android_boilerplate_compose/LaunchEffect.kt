package com.lemonade.android_boilerplate_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

/**
 * EventLaunchEffect observes a one-shot [Event] of type [Action] carried by
 * [ABViewModel.action] and invokes [block] for each new action.
 *
 * Because the ViewModel exposes the action via a [StateFlow], the same [Event]
 * would otherwise be re-delivered on every recomposition / configuration
 * change. To guarantee one-shot delivery, this function:
 *
 *  1. Reads the wrapped [Action] exactly once via [Event.getContent].
 *  2. Calls [consume] (typically [ABViewModel.consumeAction]) after [block]
 *     completes, so the StateFlow value goes back to `null` and the next
 *     emission — even of a structurally identical action — is observable.
 *
 * The [LaunchedEffect] is keyed on the [Event] *instance*, not on any field
 * of the action. Since [performAction] always allocates a fresh [Event], a
 * new effect is started for every emission without needing a `key` field on
 * [Action] itself.
 *
 * @param event The current [Event] wrapping an [Action]. `null` means nothing
 *              to do.
 * @param consume Function invoked after [block] completes (typically
 *                 [ABViewModel.consumeAction]).
 * @param block The behavior to execute when a fresh action arrives.
 */
@Composable
fun EventLaunchEffect(
    event: Event<Action>?,
    consume: () -> Unit,
    block: suspend CoroutineScope.(Action) -> Unit
) {
    if (event == null) return
    val action = event.getContent() ?: return
    LaunchedEffect(event) {
        block(action)
        consume()
    }
}