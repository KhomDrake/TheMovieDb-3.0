# TheMovieDb-3.0

An Android TMDB App with 2 versions, one using XML and one using Compose for creating the UI. The purpose of this is app is to demonstrate my knowledge of creating
android apps using the modern technologies and libraries: Coroutine, Koin, Espresso, Jetpack Libraries(ViewModel, Compose, Room) and Material Design using the MVVM with Clean Architecture architecture.

# Tech stack and libraries

* Minimum sdk of 26
* Developed with the <a href="https://kotlinlang.org/docs/home.html">Kotlin</a> language
* Jetpack Libraries
    * <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">View Model<a/>: Class which exposes data to the UI and encapsulates business logic. Big advantage is being lifecycle aware and allowing to persist states through configuration changes.
    * <a href="https://developer.android.com/training/data-storage/room">Room<a/>: Persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    * <a href="https://developer.android.com/topic/libraries/app-startup">Startup<a/>: Provides a straightforward, performant way to initialize components at application startup.
    * <a href="https://developer.android.com/topic/libraries/architecture/datastore">DataStore<a/>: Is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
    * <a href="https://developer.android.com/topic/libraries/architecture/paging/v3-overview">Paging3<a/>: Helps you load and display pages of data from a larger dataset from local storage or over a network.
    * <a href="">LiveData<a/>: An observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
* Architecture
    * Model View View-Model (MVVM)
    * Clean Architecture
* <a href="https://square.github.io/retrofit/">Retrofit<a/>: A type-safe HTTP client for Android and Java.
* <a href="https://github.com/matheus-corregiari/arch-toolkit">Arch-Toolkit<a/>: Collections of libraries to solve daily problems in the Android Development
  * <a href="https://github.com/matheus-corregiari/arch-toolkit/blob/master/toolkit/delegate">Delegate<a/>: Some custom implementations for delegated properties
  * <a href="https://github.com/matheus-corregiari/arch-toolkit/blob/master/toolkit/event-observer">EventObserver<a/>: Way to observe async events with LiveData and Flow
  * <a href="https://github.com/matheus-corregiari/arch-toolkit/blob/master/toolkit/statemachine">StateMachine<a/>: A simple way to handle view change states
* <a href="https://coil-kt.github.io/coil/">Coil<a/>: An image loading library for Android backed by Kotlin Coroutines.
* <a href="https://insert-koin.io/docs/quickstart/android/">Koin<a/>: Dependency injection library using Kotlin
* <a href="https://github.com/JakeWharton/ThreeTenABP">Threetenabp<a/>: Library for handling Date and Timezones
* <a href="https://github.com/jacoco/jacoco">Jacoco<a/>: Java code coverage library.
* <a href="https://github.com/JakeWharton/timber">Timber<a/>: A better way to do logging in Android
* <a href="https://github.com/mrocigno/big-brother">Bigbrother<a/>: A tool that allows you to create another tools or use the provided by default. For example, interface to see logs and network requests during app execution.
* <a href="https://m3.material.io/develop/android/mdc-android">Material3<a/>: Material Design guideline version 3.0 (using this version in the compose version of the app)
* <a href="https://m2.material.io/">Material2<a/>: Material Design guideline version 2.0
* Testing Libraries
   * <a href="https://developer.android.com/training/testing/espresso">Espresso<a/>: Android library for UI Testing
   * <a href="https://mockk.io/">Mockk<a/>: Mocking library using Kotlin
* <a href="https://github.com/tinkoff-mobile-tech/ScrollingPagerIndicator">ScrollingPagerindicator<a/>: Pager indicator library for android

# Architecture

In this app i'm using MVVM with Clean Architecture and I will explain why I made this decision. First, let's explain what MVVM and Clean Architecture are.

### MVVM

MVVM or Model View View-Model, is an architecture that separates your code into three main components: 

* View: Represents the UI, in the case of android, is the Activity or fragment.
* ViewModel: Exposes data to the View and encapsulates business logic, it acts as the link between Model and View.
* Model: Data of the application, which can be the network data or database data. Also, encapssulates some business logic related on how to handle Network and Database data.

![image](https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/b4de0018-7a43-493e-b364-22408befc950)

MVVM is the recommended Architecture pattern for Android development, you can find more information about <a href="https://developer.android.com/topic/architecture#recommended-app-arch">here<a/>

### Clean Architecture

Clean Architecture is an architecture that separates your code into three layers:

* Presentation Layer: Includes everything related to the user interface, like fragments, activities and view models.
* Domain Layer: Includes use cases that contain business logic. This layer will communicate with the data layer and return treated data to the presentation layer. This layer is optional
* Data Layer: Includes your network, database and shared preferences data and logic.
<br>
<img height="400" alt="Clean Architecture image 1" src="https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/74748151-9b0d-4cb6-8cba-1ab935941472">
<br>
<img height="400" alt="Clean Architecture image 2" src="https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/9437a93b-3a0a-40c1-8e52-c043e0dd894f">
<br>
This architecture is also recommend to be used for Android development, especially when the project is big. For small projects, is best to use only MVVM.

#### Use Case

In the clean architecture, we have now the new concept of Use Case, which is used to remove the business logic from the view model allowing to share that logic without needing to share the same view model.


### Why I use them both?

When I started to develop this project, I already knew that the project would have two versions, but I didn't yet know the detail of how I would implement this. So, the first thing that I did was to develop the View version using all of my knowledge of how to create an android app, which includes using MVVM for Architecture pattern, but after I finished the View version and need to change the code for having two version, I needed to change my architecture.
<br/>
I found this article from toptal where they explain why to use both as well: <a href="https://www.toptal.com/android/android-apps-mvvm-with-clean-architecture">Article<a/>
<br/>
<br/>
During the development of the View version, the project modularization of project looked like this:
<br/>
<img width="400" height="400" alt="Previous app modularization" src="https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/7ef23be6-3bc8-4ea6-8e6b-185cc0acb150">
<br/>
Module App which depends on features modules(like configuration, movies, favorites, etc...) and every feature module depends on Network, Ui-Library and common module. 

* Feature module: code related to one feature, this includes activities, fragments and view models
* Network module: every repository(single source of truth) and models for network and database.
* Ui-Library module: every ui related component for the whole app, like Buttons, Colors Tokens, Text Styles, etc.. . I plan to transform this module into a library to be used in other projects
* Common module: common code for app that shouldn't be in the other two modules, like common models(Genre, Movies, People, etc...) that should be used in app.

This modularization is okay when the app only had one version. The solution to accomplish two version is by using <a href="https://developer.android.com/build/build-variants">build variants<a/>, where we can divide our build code by different build variants, like this:
<br>
<img height="200" alt="Build Variants folder" src="https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/8e2f69fd-fc78-4566-b579-d055b8557f3d">
<br>
The folder **main** contains every code that should exist for every build variant and the other two folders, **views** and **compose** contains only code related to that version. 
<br>
Normally, modules only has the main folder and folders for tests, debug and product version of the app, which is okay when you have the modularization that I had, but now I need to have other two folders. Because the way I organize the modules and files, making the separation of code that needs to be in main and the code that needs to be in compose or views, would be a little complicated and that's where Clean Architecture helped, because the modularization changed into this:
<br>
<img width="500" height="500" alt="New app modularization" src="https://github.com/KhomDrake/TheMovieDb-3.0/assets/23155918/90aa024b-a066-42c7-8253-8ef499c919cd">
<br>
Now, every feature module was divided into three modules, one for each layer of the clean architecture. This way, every feature folder has everything that the feature needs, from the Repository to the Use Case and then the ViewModel and Activities. <br>
The modules data and domain only have the main folder, because they shouldn't be different between the version, the data and business logic should be the same.<br>
Also, the module network was divided into four modules, three modules responsible for handling source of data and one reponsible for having everything in common with the source of data.
<br>
<br>
Using the Clean Architecture to organize the project modules and to add the use case, which helps to share between the business logic, I was able to have two version of the app, that shares business logic and model between the versions. If in the future I want to add another version, the process would be very simple.

# Design

I made a Figma Design for the App to use as footprint to develop the App: <a href="https://www.figma.com/file/TPYufv6uuELb2CW3tIQ2PJ/The-Movie-Db-3.0?type=design&node-id=47%3A1474&mode=design&t=WLWzDb0Wdf2Quv6i-1">Figma</a>
