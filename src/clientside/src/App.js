// src/client/src/App.js

import React from 'react';
import './App.css';           
// Importantly, you'll need to include Bootstrap's CSS in your index.js or equivalent
// For example, in src/index.js, you'd add: import 'bootstrap/dist/css/bootstrap.min.css';
import UserList from './components/UserList'; // Import the UserList component

/**
 * The main application component for the React frontend.
 * This component serves as the entry point and orchestrates other components.
 */
function App() {
  return (
    // Use Bootstrap container for basic layout
    <div className="App container mt-4">
      <header className="text-center mb-4">
        {/* You can keep or remove the logo based on your design */}
        {/* <img src={logo} className="App-logo" alt="logo" /> */}

        {/* Main title of the application with Bootstrap heading class */}
        <h1 className="display-4 text-primary">Tour Management App</h1>

        {/* Standard instruction text, perhaps with some Bootstrap text classes */}
        <p className="lead text-muted">
          Welcome to your user management system.
        </p>
      </header>

      {/*
        This is where the UserList component is rendered.
        It will fetch and display data from your Spring Boot backend's 'user' table.
      */}
      <UserList />

      {/*
        You can add other components of your application here,
        such as navigation bars, forms, or other data displays.
      */}
      <footer className="text-center mt-5 text-muted">
        <p>&copy; 2024 P05 Tour Management System. All rights reserved.</p>
      </footer>
    </div>
  );
}

// Export the App component as the default export for use in index.js
export default App;