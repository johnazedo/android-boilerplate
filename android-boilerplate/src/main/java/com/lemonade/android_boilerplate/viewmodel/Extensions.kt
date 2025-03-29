package com.lemonade.android_boilerplate.viewmodel

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
 *
 * @param dispatcher The dispatcher to be used
 * @param block The block of code to be executed inside the coroutine
 *
 * @return A Result object containing the result of the block of code
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
