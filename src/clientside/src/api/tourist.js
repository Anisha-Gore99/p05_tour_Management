import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/tourist'; // Gateway routes to TouristService

// Register a new tourist profile
export const registerTourist = async (touristData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/registertourist`, touristData);
    return response.data; // Expected: { touristId: ... }
  } catch (error) {
    console.error('Error registering tourist:', error);
    throw error;
  }
};

// Fetch tourist profile by user ID (updated to match backend)
export const getTouristProfile = async (uid) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/getTouristByUser/${uid}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching tourist profile:', error);
    throw error;
  }
};

// Fetch all bookings for a tourist
export const getTouristBookings = async (touristId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/bookings/tourist/${touristId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching tourist bookings:', error);
    throw error;
  }
};

// Fetch cancelled bookings for a tourist
export const getCancelledBookings = async (touristId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/bookings/tourist/${touristId}`);
    return response.data.filter(booking => booking.status === 'cancelled');
  } catch (error) {
    console.error('Error fetching cancelled bookings:', error);
    throw error;
  }
};
