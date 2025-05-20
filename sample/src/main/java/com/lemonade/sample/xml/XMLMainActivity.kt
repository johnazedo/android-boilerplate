package com.lemonade.sample.xml

import android.os.Bundle
import com.lemonade.android_boilerplate.activity.ABActivity
import com.lemonade.sample.R

class XMLMainActivity(
    override var layoutId: Int = R.layout.xml_main_activity,
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