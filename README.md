
# News App with Jetpack Compose

This Android app is a news app built with Jetpack Compose, a modern user interface framework for Android.

## Key Features:  
*   **News Search:** Users can search for news articles using keywords.
*   **News Details:** View the full content of selected news articles.
*   **Favorites:** Save interesting articles for later reading.
*   **Category Filtering:** Filter news articles by category using a `SingleChoiceSegmentedButtonRow`.
*   **Web Browser Integration:** Open news articles in the user's preferred web browser via an `Intent`.
*   **Bottom Navigation:** Easy navigation between different sections of the app using a bottom navigation bar.
*   **Efficient Data Loading:** Used `PagingSource` and `RemoteMediator` to efficiently load and manage large news lists. This improves the performance of the application and provides a smooth user experience.

## Technologies Used

*   **Jetpack Compose:** Declarative UI framework for building modern and responsive UIs.
*   **Kotlin:** Modern programming language for Android development.
*   **Room Database:** Local database for storing favorited news articles for offline access.
*   **Dagger Hilt:** Dependency injection framework for improved code organization and testability.
*   **Navigation Compose:** Manages in-app navigation, simplifying screen transitions.
*   **Retrofit:** Networking library for fetching news data from an API (Please replace with your actual API details).
*   **Moshi:** JSON serialization/deserialization library.
*   **Coil-kt:** Image loading library with Compose support.
*   **Paging 3:** Library to efficiently load and display large lists of news articles.
*   **Material Design 3:** Provides modern UI components and styling.
*   **MVVM (Model-View-ViewModel):** Separates UI (View) from business logic (ViewModel), improving testability, maintainability, and code organization..
*   **Clean Architecture:**  Organizes the app into layers (Presentation, Domain, Data) to enforce separation of concerns and improve long-term maintainability and scalability. 
*   **Coroutines:** It simplifies asynchronous programming in Kotlin, making it easier to write efficient and readable concurrent code. It was chosen for its performance and simplified asynchronous coding.
*   **Flow:** A reactive programming library used to handle asynchronous data streams and respond to data changes over time. It was chosen for its ability to manage asynchronous data processing and ensure UI responsiveness.



## Project Video

https://github.com/user-attachments/assets/19244484-2e25-422b-9b7b-aa08e6c0c872
  