# Product List App

Welcome to the **Product List App** repository! 🎉  
This project is an Android application that demonstrates the efficient use of modern Android development tools and libraries. It is designed to showcase a clean and scalable architecture using **MVVM**, **Coroutines**, and a robust tech stack.

---

## ✨ Features

● **Home Screen**: Displays a list of products fetched from the API and stored locally in the Room database.  

● **Favorite Screen**: Allows users to manage their favorite products.  

● **Details Screen**: Displays detailed information about a selected product.  

● **Fetch and Save**: Products are fetched from a remote API and saved in the Room database for offline access.  

● **Real-time Updates**: User actions, like adding to favorites, reflect instantly across screens.  

● **Navigation Component**: Handles seamless navigation between Home, Favorite, and Details screens.  

● **Dependency Injection with Hilt**: Ensures clean and scalable code with managed dependencies.  

● **Room Database**: Provides an offline-first experience with a local database for storing product and favorite details. 

● **Kotlin Coroutines**: Handles API calls, database operations, and updates efficiently. 

● **Coil**: Loads and displays product images efficiently.  

● **Lottie Animations**: Enhances the user experience with engaging animations.  

● **Circular Progress Indicators**: Provides intuitive feedback during data loading.

---

## 🛠️ Tech Stack

● **Language**: Kotlin  

● **Architecture**: MVVM (Model-View-ViewModel)  

● **Libraries**:  

  ○ Navigation Component  
  
  ○ Hilt for Dependency Injection  
  
  ○ Room for Local Database  
  
  ○ Kotlin Coroutines for Asynchronous Programming  
  
  ○ Coil for Image Loading  
  
  ○ Lottie for Animations  
  
  ○ Circular Progress Indicators for Loading States 

  ○ Retrofit for API Calls

---

## 🚀 How It Works

1. **Fetching and Saving Products**:
   
   ● Products are fetched from a REST API using **Kotlin Coroutines** for asynchronous network operations.
   
   ● Fetched data is saved in the local **Room database** for offline accessibility.  

2. **User Favorites**:
   
   ● Users can mark products as favorites, which are stored in the Room database.
   
   ● Favorite products can be viewed and managed from the **Favorite Screen**.  

3. **Navigation**:
 
   ● The **Navigation Component** ensures smooth transitions between the **Home**, **Favorite**, and **Details** screens.  

4. **Real-time Data Updates**:
    
   ● **Room with Flow** provides real-time updates to the UI whenever the database is modified.  

5. **Image Loading**:
    
   ● **Coil** handles efficient image loading and caching for product images.  

6. **Interactive Animations**:
     
   ● **Lottie Animations** are used to enhance user engagement on loading or empty states.  

7. **Loading States**:
    
   ● Circular progress indicators are displayed during data fetching to improve user feedback.

---

## 🤝 Contributing
Contributions are welcome! <br>
Feel free to fork this repository, create a new branch, and submit a pull request.


