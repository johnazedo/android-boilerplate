package com.lemonade.kotlin_mvvm.viewmodel

/**
 * This class is used to ensure that state events are
 * only shoot once. This is necessary, because when the
 * fragment is resumed the last state is observed.
 */
class OneShotWrapper<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
