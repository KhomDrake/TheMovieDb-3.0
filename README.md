# TheMovieDb-3.0

## Introduction

I have a few years of experience coding Native Android developer apps and I was searching for a project where I could
apply everything that I know. I had developed previous versions of an app that uses The Movie Db API, but they're projects for me to learn how to build Android apps and not to show what I know.
In this project, I used all of my knowledge to build the best The Movie Db App that I can, with all of the technologies that I know. This is why this project uses Product flavours to create two versions of the same app, one with compose and one with views, so that I can show that I can build using the two technologies.

<a href="https://www.figma.com/file/TPYufv6uuELb2CW3tIQ2PJ/The-Movie-Db-3.0?type=design&node-id=47%3A1474&mode=design&t=WLWzDb0Wdf2Quv6i-1">Figma</a>

# Views Version

Currently, the View version is almost finished, there are a few things that I need to finish. This is the list of things that I wanted in this version and it is almost finished.

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
- [ ] CI/CD
    - [x] Use Github Actions
    - [x] Build app on CI
    - [x] Run Android Tests on CI
    - [ ] Generate Signed Bundle on CI
- [ ] Features
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
    - [x] Favorites
        - [x] Movies
        - [x] Tv Shows
        - [x] People
    - [ ] Settings
        - [x] Image Resolutions
        - [x] Language
        - [x] Region
        - [ ] Show adult content
- [ ] Testings
    - [ ] Ui Test
        - [x] Module App
        - [ ] Module Common
        - [ ] Module Configuration
        - [ ] Module Favorite
        - [x] Module Genre
        - [x] Module Movie
        - [ ] Module People
        - [ ] Module Search
        - [ ] Module Series
        - [ ] Module Imperiya
    - [ ] Unit Test
        - [ ] Module Network
        - [ ] Module Extensions
    - [ ] Libraries developed
        - [ ] Imperiya
            - [ ] Add more components
        - [ ] Bondsmith
            - [ ] Improve error handling

## Images

<p align="center">
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Home Movie" title="#Movie" src="./images/views/1_home_movie.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Home Series" title="#Sereis" src="./images/views/2_home_series.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search" title="#Search" src="./images/views/3_search.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search - Movies" title="#Search" src="./images/views/4_search_movies.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search - Series" title="#Search" src="./images/views/5_search_series.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search - History" title="#Search" src="./images/views/6_search_history.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search - Series - Naruto" title="#Search" src="./images/views/7_search_series_naruto.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Favorites - Movie" title="#Favorites" src="./images/views/8_favorites_home_movie.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Favorites - Series" title="#Favorites" src="./images/views/9_favorites_home_series.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Menu" title="#Menu" src="./images/views/10_menu_1.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Menu 2" title="#Menu" src="./images/views/11_menu_2.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Settings" title="#Settings" src="./images/views/12_settings.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Trending Now" title="#Movie" src="./images/views/13_movie_trending_now.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Top Rated" title="#Movie" src="./images/views/14_movie_top_rated.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Now Playing" title="#Movie" src="./images/views/15_movie_now_playing.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Upcoming" title="#Movie" src="./images/views/16_movie_upcoming.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Popular" title="#Movie" src="./images/views/17_movie_popular.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Genres" title="#Movie" src="./images/views/18_movies_genres.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Genres 2" title="#Movie" src="./images/views/19_movies_genre.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Trending Now" title="#Movie" src="./images/views/20_series_trending_now.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Top Rated" title="#Series" src="./images/views/21_series_top_rated.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Airing Today" title="#Series" src="./images/views/22_series_airing_today.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - On The Air" title="#Series" src="./images/views/23_series_on_the_air.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Popular" title="#Series" src="./images/views/24_series_popular.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Genres" title="#Series" src="./images/views/25_series_genres.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Search" title="#Series" src="./images/views/26_series_search.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Search - Search 2" title="#Series" src="./images/views/27_series_search_2.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Popular" title="#People" src="./images/views/28_people_popular.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Trending" title="#People" src="./images/views/29_people_trending.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Search" title="#People" src="./images/views/30_people_search.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - 1" title="#MovieDetail" src="./images/views/31_movie_detail_1.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - 2" title="#MovieDetail" src="./images/views/32_movie_detail_2.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - Cast" title="#MovieDetail" src="./images/views/33_movie_detail_cast.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - 3" title="#MovieDetail" src="./images/views/34_movie_detail_3.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - Review" title="#MovieDetail" src="./images/views/35_movie_detail_review.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Movie - Detail - Recommendation" title="#MovieDetail" src="./images/views/36_movie_detail_recommendation.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - 1" title="#SeriesDetail" src="./images/views/37_series_detail_1.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - 2" title="#SeriesDetail" src="./images/views/38_series_detail_2.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - 3" title="#SeriesDetail" src="./images/views/39_series_detail_3.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - Seasons" title="#SeriesDetail" src="./images/views/40_series_detail_seasons.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - Cast" title="#SeriesDetail" src="./images/views/41_series_detail_cast.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - Reviews" title="#SeriesDetail" src="./images/views/42_series_detail_reviews.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="Series - Detail - Recommendation" title="#SeriesDetail" src="./images/views/43_series_detail_recommendation.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Detail" title="#PeopleDetail" src="./images/views/44_people_detail_1.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Detail - Movies" title="#PeopleDetail" src="./images/views/45_people_detail_movies.png"/>
  </kbd>
  <kbd>
    <img width="250" style="border-radius: 2px" height="600" alt="People - Detail - Series" title="#PeopleDetail" src="./images/views/46_people_detail_series.png"/>
  </kbd>
</p>

# Compose Version

Pending...