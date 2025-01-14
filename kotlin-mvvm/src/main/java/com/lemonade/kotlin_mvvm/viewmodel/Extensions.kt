package com.lemonade.kotlin_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
* coroutineRunCatching was created to use runCatching
* and change the coroutine dispatcher in
* an elegant way
*
* This must be used inside the function
* viewModelScope.launch
* */
suspend inline fun <R> ViewModel.coroutineRunCatching(
    dispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> R
): Result<R> {
    return try {
        Result.success(withContext(dispatcher){ block() })
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
