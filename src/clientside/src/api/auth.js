//import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Function to handle user login
export const loginUser = async (credentials) => {
    try {
        const response = await fetch(`${API_BASE_URL}/user/chkLogin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
        });

        if (!response.ok) {
            // Check for specific error status codes
            if (response.status === 401) {
                throw new Error('Invalid username or password.');
            }
            throw new Error('Login failed. Please try again.');
        }

        return await response.json(); // Returns a user object and/or a token
    } catch (error) {
        console.error("Error during login:", error);
        throw error;
    }
};

export const registerUser = async (userData) => {
    try {
        const response = await fetch(`${API_BASE_URL}/user/createuser`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            if (response.status === 409) {
                // Handle the specific "Conflict" error from the backend
                const errorText = await response.text();
                throw new Error(errorText || 'Username or email already exists.');
            }
            throw new Error('Registration failed. Please check your information and try again.');
        }

        return await response.json();
    } catch (error) {
        console.error("Error during registration:", error);
        throw error;
    }
};
