package com.lemonade.kotlin_mvvm.viewmodel

/**
 * This class is used to ensure that state events are
 * only shoot once. This is necessary, because when the
 * fragment is resumed the last state is observed.
 */
class OneShotWrapper<out T>() {
    private var hasBeenHandled = false
    private var content: T? = null

    private constructor(content: T) : this() {
        this.content = content
    }

    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun onValidContent(callback: (T) -> Unit) {
        if(hasBeenHandled.not() && content != null) {
            callback(content!!)
        }
    }

    companion object {
        /**
         * With private constructor .of is the only way to create
         * a new object of this class.
         */
        fun <T> of(content: T) = OneShotWrapper(content)
    }
}
