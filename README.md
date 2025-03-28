# Android Boilerplate Library

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Android Boilerplate is a library designed to simplify the implementation of the Model-View-ViewModel (MVVM) architectural pattern in Kotlin-based projects. This library aims to streamline the development process by providing essential tools and components that facilitate a clean separation of concerns, promoting scalable and maintainable codebases.

## Features

- **Simplified MVVM Implementation**: Provides base classes and utilities to reduce boilerplate code associated with the MVVM pattern.
- **Kotlin-Friendly**: Leverages Kotlin's language features to offer concise and expressive APIs.
- **Sample Application**: Includes a sample project demonstrating the library's integration and usage.

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

## Getting Started

To integrate the Android Boilerplate library into your project, follow these steps:

1. **Clone the Repository**

```bash
git clone https://github.com/johnazedo/android-boilerplate.git
```
2. **Navigate to the Project Directory:**
```bash
cd android-boilerplate
```
3. **Open the Project:** Use your preferred IDE (such as IntelliJ IDEA or Android Studio) to open the project.

4. **Build the Project:** Ensure that your development environment is set up correctly and build the project to resolve dependencies.


## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please open an issue or submit a pull request. For major changes, it's recommended to discuss them first by opening an issue to ensure alignment with the project's goals.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/johnazedo/android-boilerplate/blob/main/LICENSE) file for more details.


