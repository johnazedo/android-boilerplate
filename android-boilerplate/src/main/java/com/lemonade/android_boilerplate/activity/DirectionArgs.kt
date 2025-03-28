package com.lemonade.android_boilerplate.activity

import android.os.Bundle
import androidx.navigation.NavOptions

data class DirectionArgs(
    val destinationId: Int,
    val options: NavOptions? = null,
    val bundle: Bundle? = null
)
