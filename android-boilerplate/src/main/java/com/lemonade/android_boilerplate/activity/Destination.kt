package com.lemonade.android_boilerplate.activity

import android.os.Bundle
import androidx.navigation.NavOptions

open class Destination(
    open val destinationId: Int,
    open val options: NavOptions? = null,
    open val bundle: Bundle? = null
) {
    data class NavigateBackDirection(
        override val destinationId: Int = NAVIGATE_BACK
    ): Destination(destinationId)

    companion object {
        const val NAVIGATE_BACK = -1
    }
}