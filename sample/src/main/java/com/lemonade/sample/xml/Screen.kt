package com.lemonade.sample.xml

import com.lemonade.android_boilerplate.activity.Destination
import com.lemonade.sample.R

/*
 * To represent navigation you have two choices,
 * OR create a enum with resID to use put inside DirectionArgs like
 * this: DirectionArgs(Screen.MAIN.resID)
 *
 * OR import You can import DirectionArgs and create a specific class.
 * This is the recommended way to do it.
 */

enum class Screen(val resID: Int) {
    MAIN(R.id.main_fragment),
    HOME(R.id.home_fragment),
    LOGIN(R.id.login_fragment);

    fun getID() = resID
}

data class MainFragmentDestination(
    override val destinationId: Int = R.id.main_fragment
): Destination(destinationId)

data class HomeFragmentDestination(
    override val destinationId: Int = R.id.home_fragment
): Destination(destinationId)

data class LoginFragmentDestination(
    override val destinationId: Int = R.id.login_fragment
): Destination(destinationId)