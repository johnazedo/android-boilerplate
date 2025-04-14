package com.lemonade.android_boilerplate.fragment

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lemonade.android_boilerplate.activity.Destination
import com.lemonade.android_boilerplate.viewmodel.ABAction
import com.lemonade.android_boilerplate.viewmodel.ABViewModel
import com.lemonade.android_boilerplate.viewmodel.Action
import com.lemonade.android_boilerplate.viewmodel.State
import com.lemonade.android_boilerplate.viewmodel.UIError

/**
 * onReceiveEvent must be add into onCreate of your activity.
 *
 * @param viewModel The ViewModel associated with the activity.
 * @param handleState A function to handle state changes.
 * It is not mandatory and the default value is an empty lambda.
 * @param handleAction A function to handle action events.
 * It is not mandatory and the default value is an empty lambda.
 * @param handleError A function to handle presentation errors.
 * It is not mandatory and the default value is an empty lambda.
 */
inline fun <reified S: State, reified A: Action> Fragment.onReceiveEvent(
    viewModel: ABViewModel<S, A>,
    crossinline handleState: (S) -> Unit = {},
    crossinline handleAction: (A) -> Unit = {},
    crossinline handleError: (UIError) -> Unit = {}
    ) {
    viewModel.getState.observe(this) { state -> handleState(state) }
    viewModel.getAction.observe(this) { wrapper ->
        wrapper.onValidContent { abAction ->
            when (abAction) {
                is ABAction.PerformAction<A> -> handleAction(abAction.action)
                is ABAction.ShowError<A> -> handleError(abAction.error)
                is ABAction.NavigateTo<A> -> navigateTo { abAction.destination }
            }
        }
    }
}

/**
 * Fragment.supportActivity This function provides a convenient way to access the activity that hosts this fragment,
 * casting it to [AppCompatActivity]. If the fragment is not attached to an activity or if
 * the activity is not an instance of [AppCompatActivity], it returns null.
 *
 * @return The [AppCompatActivity] associated with this fragment, or null if not available.
 */
fun Fragment.supportActivity(): AppCompatActivity? {
    return activity as? AppCompatActivity
}

/**
 * Fragment.supportActionBar provides a convenient way to access the action bar of the hosting activity.
 * It first retrieves the [AppCompatActivity] using [supportActivity] and then accesses its
 * [ActionBar]. If the fragment is not attached to an [AppCompatActivity] or if the activity
 * does not have an action bar, it returns null.
 *
 * @return The [ActionBar] associated with the hosting activity, or null if not available.
 */
fun Fragment.supportActionBar(): ActionBar? {
    return supportActivity()?.supportActionBar
}

/**
 * Fragment.setSupportActionBar provides a convenient way to set a custom toolbar as the action bar for the
 * hosting activity. It first retrieves the [AppCompatActivity] using [supportActivity] and then
 * calls [AppCompatActivity.setSupportActionBar] to set the provided [toolbar] as the action bar.
 *
 * @param toolbar The [Toolbar] to be set as the support action bar.
 */
fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    supportActivity()?.setSupportActionBar(toolbar)
}

/**
 * Fragment.getString provides a convenient way to retrieve a string resource from the application's
 *
 * @param resID The resource ID of the string to be retrieved.
 * @return The string value associated with the provided resource ID.
 */
fun Fragment.getString(resID: Int): String {
    return requireContext().getString(resID)
}

/**
 * Fragment.navigateTo provides a convenient way to navigate to a destination
 * within use the findNavController in you code.
 *
 * @param block A function that returns a [Destination] object.
 */
fun Fragment.navigateTo(block: () -> Destination ) {
    val args = block.invoke()
    findNavController().navigate(args.destinationId, args.bundle, args.options)
}