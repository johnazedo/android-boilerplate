package com.lemonade.sample

import android.os.Bundle
import com.lemonade.android_boilerplate.activity.ABActivity

class MainActivity(
    override var layoutId: Int = R.layout.main_activity,
    override var fragmentContainerId: Int = R.id.fragment_container
) : ABActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use navigateTo in onCreate to set the first fragment of your app
        navigateTo {
           MainFragmentDestination()
        }
    }
}