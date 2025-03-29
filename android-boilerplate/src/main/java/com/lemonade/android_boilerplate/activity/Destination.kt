package com.lemonade.android_boilerplate.activity

import android.os.Bundle
import androidx.navigation.NavOptions

/**
 * Destination is a class that represents a destination in the navigation graph.
 *
 * @param destinationId The ID of the destination, which is used to identify the destination in the navigation graph.
 * @param options Optional navigation options for the destination.
 * @param bundle Optional data to be passed to the destination.
 */
open class Destination(
    open val destinationId: Int,
    open val options: NavOptions? = null,
    open val bundle: Bundle? = null
) {

    /**
     * NavigateBackDirection is a pre-created subclass of Destination
     * that represents a navigation back action.
     */
    data class NavigateBackDirection(
        override val destinationId: Int = NAVIGATE_BACK
    ): Destination(destinationId)

    companion object {
        const val NAVIGATE_BACK = -1
    }
}