package com.lemonade.kotlin_mvvm

/**
 * This class is used to ensure that state events are
 * only shoot once. This is necessary, because when the
 * fragment is resumed the last state is observed.
 */
internal class OneShotWrapper<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getEvent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
