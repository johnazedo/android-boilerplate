package com.lemonade.kotlin_mvvm.fragment

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.lemonade.kotlin_mvvm.viewmodel.Action
import com.lemonade.kotlin_mvvm.viewmodel.FullViewModel
import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel

inline fun <reified S: State> Fragment.onStateChange(
    viewModel: StateViewModel<S>,
    crossinline handleState: (S) -> Unit,
) = viewModel.getState.observe(this) {
        state -> handleState(state)
}

inline fun <reified S: State, reified A: Action> Fragment.onActionChange(
    viewModel: FullViewModel<S, A>,
    crossinline handleAction: (A) -> Unit
) = viewModel.getAction.observe(this) { wrapper ->
    wrapper.getContent()?.let { action ->
        handleAction(action)
    }
}

fun Fragment.supportActivity() : AppCompatActivity? {
    return activity as? AppCompatActivity
}

fun Fragment.supportActionBar() : ActionBar? {
    return supportActivity()?.supportActionBar
}

fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    supportActivity()?.setSupportActionBar(toolbar)
}

