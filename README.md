# Todo List App

A modern Android Todo List application built with Jetpack Compose and following MVVM architecture pattern. The app demonstrates best practices in Android development including offline-first data strategy, clean architecture, and modern UI design.

## Features

- Fetch todos from JSONPlaceholder API
- Local data persistence using Room database
- Offline-first architecture with data caching
- Clean MVVM architecture with Repository pattern
- Modern UI built with Jetpack Compose
- Navigation between list and detail screens
- Error handling and loading states

## Tech Stack

- **UI**: Jetpack Compose with Material3 Design
- **Architecture**: MVVM + Repository Pattern
- **Navigation**: Jetpack Compose Navigation
- **Network**: Retrofit with Kotlin Coroutines
- **Local Storage**: Room Database
- **Dependency Injection**: Manual DI through Application class
- **State Management**: Kotlin Flow with StateFlow

## Project Structure

```
app/src/main/java/com/example/todo/
├── data/
│   ├── local/          # Room database, DAO
│   ├── remote/         # Retrofit API interface
│   ├── model/          # Data models
│   └── repository/     # Repository implementation
├── ui/
│   ├── screens/        # Compose UI screens
│   ├── theme/          # App theme and styling
│   └── TodoViewModel   # ViewModel for UI state
└── TodoApplication.kt  # Application class for DI
```

## Video Demo Script (5 minutes)

1. **Introduction (30s)**

   - Brief overview of the app and its purpose
   - Highlight key technologies used (Compose, Room, Retrofit)

2. **Architecture Overview (1min)**

   - Explain MVVM + Repository pattern
   - Show how data flows from API to UI
   - Demonstrate offline-first approach

3. **Feature Demo (2min)**

   - Show todo list screen with loading state
   - Demonstrate offline capabilities
   - Navigate to detail screen
   - Show error handling

4. **Code Walkthrough (1min)**

   - Highlight key implementations:
     - Compose UI components
     - ViewModel state management
     - Repository pattern
     - Room and Retrofit integration

5. **Conclusion (30s)**
   - Recap key features and benefits
   - Discuss potential improvements
   - Thank the audience

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Run the app on an emulator or physical device

