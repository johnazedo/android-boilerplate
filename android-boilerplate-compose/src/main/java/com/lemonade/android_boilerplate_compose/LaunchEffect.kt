package com.lemonade.android_boilerplate_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope


@Composable
fun EventLaunchEffect(event: Event<Action>, block: suspend CoroutineScope.(Action) -> Unit) {
    val action = event.getContent() ?: return
    if(action.key == ABViewModel.DEFAULT_KEY) return
    LaunchedEffect(action.key) {
        block(action)
    }
}