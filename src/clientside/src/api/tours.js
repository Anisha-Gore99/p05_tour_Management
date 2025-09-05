import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/packages';

export const searchTourPackages = async (destination, categoryId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/search`, { 
            params: {
                destination,
                categoryId
            }
        });
        return response.data;
    } catch (error) {
        console.error("Error searching tour packages:", error);
        throw error;
    }
};

export const getTourPackageById = async (packageId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/${packageId}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching tour package details:", error);
        throw error;
    }
};

// Function to fetch details for a single tour package by its ID
export const getTourDetails = async (packageId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${packageId}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch details for tour package ID: ${packageId}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error fetching tour details for ID ${packageId}:`, error);
        throw error;
    }
};

export const createTourPackage = async (tourPackage) => {
    try {
        const response = await axios.post(API_BASE_URL, tourPackage);
        return response.data;
    } catch (error) {
        console.error("Error creating tour package:", error);
        throw error;
    }
};

export const deleteTourPackage = async (id) => {
    try {
        await axios.delete(`${API_BASE_URL}/${id}`);
    } catch (error) {
        console.error(`Error deleting tour package with ID ${id}:`, error);
        throw error;
    }
};

// to fetch all tours to group them on the frontend.
export const getAllTourPackages = async () => {
    try {
        const response = await axios.get(API_BASE_URL);
        return response.data;
    } catch (error) {
        console.error("Error fetching all tour packages:", error);
        throw error;
    }
};

// to get tour packages by a specific category ID 
export const getPackagesByCategory = async (categoryId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/byCategory/${categoryId}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching tours for category ${categoryId}:`, error);
        throw error;
    }
};
