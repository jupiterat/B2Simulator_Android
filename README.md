The goal of this README is to be our starting point for new projects, following the best development practices. It's our interpretation and adaptation of the official [architecture](https://developer.android.com/topic/architecture) guidelines provided by Google. And it's inspired by Google's [NowInAndroid](https://github.com/android/nowinandroid).

## Clean architecture with 3 main modules
- Data (for database, API and preferences code)
- Domain (for business logic and models)
- App (for UI logic, with MVVM)
    
## Other useful features
- Dependency injection (with [Hilt](http://google.github.io/hilt/))
- Android ViewModel
- Reactive programming (with [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html))
- Android architecture components to share ViewModels during configuration changes
- [Splash Screen](https://developer.android.com/develop/ui/views/launch/splash-screen) Support
- Google [Material Design](https://material.io/blog/android-material-theme-color) library
- Jetpack: Navigation, Room, LiveData
- This version brings [Modularization](https://developer.android.com/topic/modularization) => In Progress
- Jetpack compose => In Progress
