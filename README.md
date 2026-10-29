# 🌐 API Explorer – Android Application

An Android application developed using **Java and Android Studio** to demonstrate how to access web services using HTTP requests. The app fetches user information and posts from a REST API and displays the retrieved JSON data in a simple, user-friendly interface.

## 📱 Features

- **Fetch Users:** Retrieve user profiles, email addresses, phone numbers, cities, and company names.
- **Fetch Posts:** Retrieve and display post titles and descriptions.
- **HTTP GET Requests:** Communicate with a remote REST API.
- **JSON Parsing:** Process server responses using `JSONObject` and `JSONArray`.
- **Loading Indicator:** Display progress while fetching data.
- **Connection Status:** Show API connection status and error messages.
- **Clear Results:** Clear the displayed information.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application logic |
| Android Studio | Development environment |
| XML | User interface design |
| HttpURLConnection | HTTP communication |
| JSON | Data exchange and parsing |
| JSONPlaceholder | Sample REST API |

## 🔗 API Endpoints

**Users API**
```text
https://jsonplaceholder.typicode.com/users
```

**Posts API**
```text
https://jsonplaceholder.typicode.com/posts
```

JSONPlaceholder is a free testing API that provides sample data for development and learning.

## ⚙️ How It Works

1. The user launches the API Explorer application.
2. The user taps **GET USERS** or **GET POSTS**.
3. The application sends an HTTP GET request to the selected API endpoint.
4. The server returns a JSON response.
5. The application parses the JSON data.
6. The retrieved information is displayed on the screen.
7. Connection errors are displayed if the request fails.

## 🚀 Getting Started

### Prerequisites

- Android Studio
- Java Development Kit (JDK)
- Android device or emulator
- Active internet connection

### Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/YOUR-USERNAME/APIExplorer.git
   ```

2. Open the project in Android Studio.
3. Allow Gradle to sync and finish building the project.
4. Connect an Android device or start an emulator.
5. Click **Run ▶** to install and launch the application.

Replace `YOUR-USERNAME` with your GitHub username and adjust the repository URL if your repository has a different name.

## 🔐 Permissions

The application requires internet access. The following permission is declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 📂 Project Structure

```text
APIExplorer/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/apiexplorer/
│           │       └── MainActivity.java
│           ├── res/
│           │   └── layout/
│           │       └── activity_main.xml
│           └── AndroidManifest.xml
├── README.md
└── build.gradle
```

*Note: The exact Gradle file locations may vary depending on the Android Studio project structure.*

## 🎯 Learning Outcomes

- Understanding RESTful web services.
- Implementing HTTP GET requests in Android.
- Connecting an Android app to an online API.
- Parsing JSON responses.
- Performing network operations in a background thread.
- Handling network errors and updating the UI.

## 🔮 Future Enhancements

- Add HTTP POST, PUT, and DELETE operations.
- Add user search and filtering.
- Display user profiles in attractive cards.
- Add individual post details.
- Implement pull-to-refresh and local data caching.

## 👨‍💻 Author

**Hardik Patel**

## 📄 License

This project is intended for educational and learning purposes. You may add an open-source license, such as the MIT License, if you wish to permit reuse under its terms.
