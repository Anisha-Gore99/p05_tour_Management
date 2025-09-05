import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api'; // Replace with your actual backend URL

export const getAllCategories = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/categories`);
        return response.data;
    } catch (error) {
        console.error("Error fetching all categories:", error);
        throw error;
    }
};
