# Android Boilerplate Library

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Android Boilerplate is a library designed to simplify the implementation of the Model-View-ViewModel (MVVM) and Model-View-Intent (MVI) architectural pattern in Kotlin-based projects. This library aims to streamline the development process by providing essential tools and components that facilitate a clean separation of concerns, promoting scalable and maintainable codebases.

## How to use

You can add this library to your project using **JitPack**:

1. **Add JitPack to your** `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```
2. **Add the dependency to your** `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.johnazedo:android-boilerplate:Tag")
}
```
Replace Tag with the latest release version. You can find the latest version [here](https://jitpack.io/#johnazedo/android-boilerplate).

## Features for XML Layout
Package: `android-boilerplate`

Concepts:
- State (State): Represents the current screen data (usually a data class);
- Action (Action): Represents one-time events (navigation, dialogs, toasts, etc.);
- UIError (UIError): For handling user-facing errors;
- OneShotWrapper: Wrapper to prevent LiveData from emitting the same event multiple times.

### ABViewModel

The ABViewModel is an abstracted base class designed to manage state, actions, and UI events in a clean and scalable way using LiveData and Kotlin's type system.
It is intended to be subclassed depending on your use case, providing variants:

- ABViewModel – supports all features;
- ABViewModel.OnlyState – only manages State, Navigation and Error reporting (No Action);
- ABViewModel.OnlyAction – only manages Action, Navigation and Error reporting (No State);
- ABViewModel.Minimal – only handles Navigation and Error reporting.

It is possible use the state feature and one time events without create a generic class for it:

```kotlin
data class FormState(val name: String = "", val isLoading: Boolean = false) : State()

sealed class FormAction : Action() {
    object SubmitSuccess : FormAction()
}

data class FormGenericError(
    val resId: Int = R.strings.generic_error_message
): UIError(resId)

class FormViewModel : ABViewModel<FormState, FormAction>(FormState()) {

    fun submitForm(name: String) {
        setState { it.copy(isLoading = true) }

        // Simulate API
        if(api.isOK()) {
            performAction { FormAction.SubmitSuccess }
            setState { it.copy(isLoading = false, name = name) }
        } else {
            showError { FormGenericError() }
        }
        
    }
}
```

To observe the state change and one time events is necessary to use `onReceiveEvent`:
```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) { 
    super.onViewCreated(view, savedInstanceState)
    onReceiveEvent(
        viewModel, 
        handleState = ::onStateChange,
        handleAction = ::handleAction,
        handleError = ::handleError
    )
}

private fun onStateChange(state: FormState) {
    // TODO Use state
}

private fun handleAction(action: FormAction) {
    // TODO Use action
}

// Is possible to handle errors if you use the function showError in viewModel.
// But if you want to custom the error it possible create a action to show the error.
// For example: FormAction.ShowError.
private fun handleError(error: UIError) {
    // TODO Handle error
}
```

If the screen is simple and does not have actions, or state or the both, is possible to use the ABViewModel variations
```kotlin

// To handle only with state, navigation and error handle
class FormViewModel : ABViewModel.OnlyState<FormState>(FormState())

// To handle only with action, navigation and error handle
class FormViewModel : ABViewModel.OnlyAction<FormAction>()

// To use only navigation and error handle
class FormViewModel : ABViewModel.Minimal()
```

### Navigation

To use navigation with this package is necessary to make some changes in your Main Activity. First
is important to create a nav graph in navigation resource folder and set this graph into Main Activity layout.

```xml
<!-- main_graph.xml -->
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/main_graph"
    app:startDestination="@id/main_fragment">

    <fragment
        android:id="@+id/login_fragment"
        android:name="com.lemonade.sample.xml.LoginFragment"
        android:label="LoginFragment"
        />

    <fragment
        android:id="@+id/home_fragment"
        android:name="com.lemonade.sample.xml.HomeFragment"
        android:label="HomeFragment"
        />

    <fragment
        android:id="@+id/main_fragment"
        android:name="com.lemonade.sample.xml.MainFragment"
        android:label="MainFragment"
        />

</navigation>

<!-- main_activity.xml -->
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragment_container"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/main_graph"
    />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Additionally, it is necessary to implement the `ABActivity` class in your `MainActivity`. PS: `ABActivity` extends `AppCompatActivity`:

```kotlin
class MainActivity : ABActivity() {
    // TODO: Implement this
}

// In Any ViewModel
class AnyViewModel : ABViewModel.Minimal() {

    fun navigateToAny() {
        navigateTo {
            // Is possible to use NavOptions and Bundle. See Destination class
            Destination(
                destinationId = R.id.main_fragment,
            )
        }
    }
}
```
Now. Is possible to use `navigateTo` in any fragment viewModel linked with MainActivity.

If is needed to use more than a activity, use a Action to navigate to another activity and make the same setup for the new component.

### Extension functions

You can use ViewModel.coroutineRunCatching instead withContext:
```kotlin
// Use this
viewModelScope.launch {
    coroutineRunCatching(dispatcher) { 
        // suspend fun
    }.onSuccess { 
        // handle success
    }.onFailure { 
        //handle error
    }
}

// Instead
viewModelScope.launch {
    withContext(dispatcher) {
        runCatching {
            // suspend fun
        }.onSuccess { 
            // handle success
        }.onFailure { 
            // handle error
        }
    }
}
```


## Features for Jetpack-Compose
Package: `android-boilerplate-compose`

This feature is coming soon.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please open an issue or submit a pull request. For major changes, it's recommended to discuss them first by opening an issue to ensure alignment with the project's goals.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/johnazedo/android-boilerplate/blob/main/LICENSE) file for more details.


