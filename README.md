Dictionary App by Prince Ikede
----------------------------------------------------------------------------------------------------

Tools used: Android, Runnable, Handler, Dependency Injection, Kotlin, Multithreading, MutableLiveData, Coroutines, MVVM Architecture, Retrofit, Koin, Junit, Mockito, Room Database

----------------------------------------------------------------------------------------------------

Features:

- Edit text for getting user's search word
- Search button used for searching user's search word
- Up/Down sort filter for sorting based on thumbs up or thumbs down
- Clear Filter button for clearing UI (including users entered text)
- Progress bar to show loading of data
- Room database for storing data from api calls locally to prevent unnecessary network calls
- RecycleView for displaying data received from either database or api(if data doesn't exist in the api)
- Instrumented test to ensure database works properly
- Unit test to ensure sorting (either by thumbs up or by thumbs down) algorithm works properly
- Dependency injection using Koin
- Handler & Runnable for progress bar multithreading

----------------------------------------------------------------------------------------------------

Improvements that can be made:

- Recycler view could have more styling
- user tracking and analytics
