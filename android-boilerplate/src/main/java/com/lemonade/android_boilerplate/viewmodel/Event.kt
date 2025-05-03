package com.lemonade.android_boilerplate.viewmodel

/**
 * A generic class that represents an event that can be handled only once.
 *
 * This class is designed to wrap a piece of data (the `content`) and ensure that it is
 * processed or consumed only once.
 *
 * The `Event` class uses a boolean flag (`hasBeenHandled`) to track whether the content
 * has already been accessed. Once the content is retrieved, the flag is set to `true`,
 * preventing further access to the content.
 *
 * @param T The type of the content wrapped by this event. The `out` variance modifier
 *          indicates that this class is covariant in its type parameter, meaning that
 *          if `S` is a subtype of `T`, then `Event<S>` is a subtype of `Event<T>`.
 */
class Event<out T>() {
    private var hasBeenHandled = false
    private var content: T? = null

    private constructor(content: T) : this() {
        this.content = content
    }

    /**
     * getContent provides a way to access the content of this [Event] instance.
     * If the content has already been retrieved (i.e., handled), this function will return null.
     * Otherwise, it will return the content and mark it as handled, ensuring that it can only be
     * retrieved once.
     *
     * @return The content of type T if it hasn't been handled, null otherwise.
     */
    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * onValidContent checks if the content has already been handled and if the content itself is not null.
     * If both conditions are met, it invokes the given callback function with the non-null content.
     * After the callback is executed, the content is marked as handled.
     *
     * @param callback The callback function to be executed with the content.
     *                 It takes a single parameter of type T, which represents the content.
     */
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
        fun <T> of(content: T) = Event(content)
    }
}