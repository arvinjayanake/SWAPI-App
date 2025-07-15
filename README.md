# Star Wars Planets App

A clean-architecture Android app built with Kotlin and Jetpack Compose, developed for the Sysco LABS technical assessment.  
The app displays a paginated, scrollable list of planets fetched from the [Star Wars API (SWAPI)](https://swapi.dev/) with offline support, details view, and modern design practices.

---

## Features

- Fetches and displays planets from SWAPI (`/planets` endpoint)
- Scrollable, paginated planet list with images and basic info
- Planet details page with additional data and higher-resolution image
- Offline support: falls back to local cache when network fails
- Graceful handling of loading and error states
- Modern UI using Jetpack Compose
- Navigation between list and details screens
- Unit-testable architecture
- Clean, documented, and maintainable code

---

## Project Structure

```text
├── data
│   ├── local
│   │   ├── db
│   │   │   ├── dao          # Room DAO interfaces
│   │   │   └── entity       # Room entity models
│   │   └── prefs            # SharedPreferences helpers
│   ├── mapper               # Data mapping extensions
│   ├── remote
│   │   ├── api              # Retrofit API interfaces
│   │   └── model            # API DTOs
│   └── repository           # Data layer repository implementations
├── di                       # Dependency injection (Koin) modules
├── domain
│   ├── model                # Domain models
│   ├── repository           # Domain repository interfaces
│   └── usecase              # Use cases for business logic
└── presentation
    ├── features
    │   ├── planetdetails    # Planet details UI & ViewModel
    │   └── planetlist       # Planet list UI & ViewModel
    ├── navigation           # App navigation graph
    ├── shared
    │   └── components       # Reusable UI components
    └── theme                # App theming
