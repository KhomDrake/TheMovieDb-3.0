# TheMovieDb-3.0

An Android TMDB App with 2 versions, one using XML and one using Compose for creating the UI. The purpose of this is app is to demonstrate my knowledge of creating
android apps using the modern technologies and libraries: Coroutine, Koin, Espresso, Jetpack Libraries(ViewModel, Compose, Room) and Material Design using the MVVM architecture.

# Tech stack and libraries

## General

* Minimum sdk of 26
* Developed with the <a href="https://kotlinlang.org/docs/home.html">Kotlin</a> language
* Jetpack Libraries
    * View Model
    * Room
    * Startup
    * DataStore
    * Paging3
    * LiveData
* Arhictecture
    * Model View View-Model (MVVM)
    * Clean Architecture
* Retrofit
* Toolkit
  * Delegate
  * LiveData
  * StateMachine
* Coil
* Koin
* Threetenabp
* Jacoco
* Timber
* Bigbrother
* 


## Compose

* Material2

## XML/Views

* Espresso
* Pagerindicator
* Material2

# Architecture

The objective of the project is to display my knowledge of Android Development by creating a App using
the <a href="https://developer.themoviedb.org/reference/intro/getting-started">The Movie Database API</a> where I use
XML and Compose to create the UI. I could use both XML and Compose together in the same code, but I found it
better to create two product flavors, Views and Compose, where is used XML in the Views Flavor and Compose
in the Compose Flavor. That way, I can show fully the use of both views and compose.
</br>
This separation between flavors was also important for architecture purposes, because I could display a ideal architecture for
this kind of situation.
</br>
I made a Figma Design for the App to use as footprint to develop the App: <a href="https://www.figma.com/file/TPYufv6uuELb2CW3tIQ2PJ/The-Movie-Db-3.0?type=design&node-id=47%3A1474&mode=design&t=WLWzDb0Wdf2Quv6i-1">Figma</a>

# General Tasks

- [x] Libraries/Technologies
    - [x] DataStore
    - [x] Room
    - [x] LiveData/ViewModel
    - [x] Coil
    - [x] Moshi
    - [x] Startup
    - [x] Paging
    - [x] <a href="https://github.com/mrocigno/big-brother">Big brother</a>
    - [ ] <a href="https://github.com/gustafah/mock-interceptor">Mock Interceptor</a>
- [ ] Personal Libraries
    - [ ] Imperiya - Design Library
        - [x] Sample App
            - [x] Compose Version
            - [x] Views Version
        - [ ] UI Tests
            - [ ] Compose Components
            - [ ] Views Components
    - [ ] Bondsmith - Library for better request handling
        - [ ] Unit Tests
        - [x] Sample App
- [ ] CI/CD
    - [x] Use Github Actions
    - [x] Build app on CI
    - [x] Run Android Tests on CI
    - [ ] Generate Signed Bundle on CI
- [x] Features Views
    - [x] Movie
        - [x] Detail
        - [x] Search
        - [x] By genres
    - [x] Tv Show
        - [x] Detail
        - [x] Search
        - [x] By genres
    - [x] People
        - [x] Detail
        - [x] Search
    - [x] Genres
        - [x] Movies
        - [x] Tv Shows
    - [x] Favorites
        - [x] Movies
        - [x] Tv Shows
        - [x] People
    - [x] Settings
        - [x] Image Resolutions
        - [x] Language
        - [x] Region
- [x] Features Compose
    - [x] Movie
        - [x] Detail
        - [x] Search
        - [x] By genres
    - [x] Tv Show
        - [x] Detail
        - [x] Search
        - [x] By genres
    - [x] People
        - [x] Detail
        - [x] Search
    - [x] Genres
        - [x] Movies
        - [x] Tv Shows
    - [x] Favorites
        - [x] Movies
        - [x] Tv Shows
        - [x] People
    - [x] Settings
        - [x] Image Resolutions
        - [x] Language
        - [x] Region

# XML Version

## Tasks

- [ ] UI Tests
    - [x] Module App
    - [x] Module Configuration Presentation
    - [x] Module Genre Presentation
    - [x] Module Movie Presentation
    - [ ] Module Search Presentation

# Compose Version

## Videos

<video width="320" height="240" controls>
  <source src="./videos/compose/compose_1.mp4" type="video/mp4">
</video>

<video width="320" height="240" controls>
  <source src="./videos/compose/compose_2.mp4" type="video/mp4">
</video>

<video width="320" height="240" controls>
  <source src="./videos/compose/compose_3.mp4" type="video/mp4">
</video>

## Tasks

- [ ] UI Tests
    - [ ] Module App
    - [x] Module Configuration Presentation
    - [ ] Module Genre Presentation
    - [ ] Module Movie Presentation
    - [ ] Module Search Presentation
- [ ] Animation
    - [x] Changing state animation
    - [ ] Collapsing Toolbar animation
