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
```
---

## Technology Stack

- **Kotlin (100%)** – Modern, safe, and concise programming language
- **Jetpack Compose** – Declarative UI framework for building native Android UIs
- **Room** – Local database for offline support
- **Retrofit** – Type-safe network API client
- **OkHttp** – HTTP stack (with custom client for SSL issues)
- **Koin** – Dependency injection for modularity and testability
- **Coroutines & Flow** – Asynchronous programming and reactive state management
- **MVVM & Clean Architecture** – For testable and maintainable code
- **Material 3** – Modern UI theming

---

## Getting Started

### Prerequisites

- **Android Studio Giraffe** or later
- **Android SDK 33+**
- Internet connection for fetching planets (offline support available after first load)
- **Minimum SDK:** 26

### Clone and Run

```bash
git clone https://github.com/<your-username>/syscolab-starwars-planets.git
cd syscolab-starwars-planets
```
Open in Android Studio and click Run.

---
## Configuration

- No additional configuration needed.
- All dependencies are managed with Gradle.
- If you encounter SSL errors (due to SWAPI certificate issues), an "unsafe" OkHttpClient is used in development for seamless fetching.

---

## Usage

- **Planet List:** Displays all planets with name, climate, and image.
- **Pagination:** Scroll to load more planets.
- **Details View:** Tap a planet for more info and a high-res image.
- **Offline Mode:** If API call fails, data loads from local cache (if available).
- **Retry/No Internet:** User-friendly screens for network errors.

---

## Architecture Overview

### Clean Architecture

- **data:** Handles persistence, network, and mapping.
- **domain:** Business logic and models.
- **presentation:** UI, ViewModels, and navigation.

### Repository Pattern

- Isolates data sources (API and local database).

### Use Cases

- Encapsulate business logic, easy to test and reuse.

### ViewModels & StateFlow

- For UI state management and lifecycle awareness.

### DI with Koin

- Enables modular and testable code.