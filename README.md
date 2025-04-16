# NIT3213 Assessment Application

An Android application that demonstrates proficiency in API integration, user interface design, and Android development best practices. This application implements a dynamic topic system where the content is determined by the authentication response.

## Features

- **Authentication System**: Secure login with username and password
- **Dynamic Content**: Content changes based on the received keypass
- **Art Gallery Dashboard**: Displays a list of artworks
- **Detailed View**: Shows comprehensive information about selected artworks
- **Modern Architecture**: Implements MVVM pattern with clean architecture principles

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Koin
- **API Integration**: Retrofit
- **Asynchronous Operations**: Coroutines
- **UI**: ViewBinding, RecyclerView
- **Testing**: JUnit, Mockito, Coroutines Test

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/myassssmentapplication/
│   │   │       ├── api/
│   │   │       ├── di/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── ui/
│   │   └── res/
│   └── test/
│       └── java/
│           └── com/example/myassssmentapplication/
│               └── ui/
```

## API Integration

The application interacts with the NIT3213 API:

- **Base URL**: `https://nit3213api.onrender.com/`
- **Authentication Endpoint**: `/sydney/auth` (POST)
- **Dashboard Endpoint**: `/dashboard/{keypass}` (GET)

### Authentication Request
```json
{
    "username": "YourFirstName",
    "password": "sYourStudentID"
}
```

### Authentication Response
```json
{
    "keypass": "topicName"
}
```

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone [repository-url]
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository and select it

3. **Configure Gradle**
   - Wait for Gradle sync to complete
   - Ensure all dependencies are properly downloaded

4. **Run the Application**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio
   - Select your target device
   - Wait for the app to install and launch

## Testing

The project includes unit tests for critical components:

- `LoginViewModelTest`: Tests authentication logic
- `DashboardViewModelTest`: Tests dashboard data handling

To run tests:
1. Open the test file in Android Studio
2. Click the "Run" button next to the test class
3. View results in the "Run" window

## Dependencies

The project uses the following main dependencies:

```gradle
dependencies {
    // AndroidX
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'
    
    // Koin
    implementation 'io.insert-koin:koin-android:3.1.5'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:4.3.1'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0'
}
```

## Architecture

The application follows the MVVM architecture pattern:

- **Model**: Data classes and repository layer
- **View**: Activities and UI components
- **ViewModel**: Business logic and state management

### Key Components

1. **Login Screen**
   - Handles user authentication
   - Manages login state
   - Navigates to Dashboard on success

2. **Dashboard Screen**
   - Displays list of entities
   - Implements RecyclerView
   - Handles item selection

3. **Details Screen**
   - Shows detailed entity information
   - Implements proper layout
   - Handles navigation

## Error Handling

The application implements comprehensive error handling for:

- Network errors
- Authentication failures
- Invalid input
- API response errors

## Contributing

This is an assessment project and contributions are not expected. However, if you find any issues or have suggestions, feel free to create an issue.

## License

This project is part of the NIT3213 course assessment and is not intended for public distribution.

## Author

Prinsh Thapa
Student ID: s4682888
Course: NIT3213 Android Application Development 