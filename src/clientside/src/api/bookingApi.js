// import axios from 'axios';

import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/bookings';

/**
 * Creates a new tour booking.
 * @param {object} bookingData The booking data payload from the form.
 * @returns {Promise<object>} The BookingDTO response from the backend.
 */
export const createBooking = async (bookingData) => {
  try {
    const response = await axios.post(`${BASE_URL}`, bookingData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response.data; // BookingDTO
  } catch (error) {
    console.error('Error creating booking:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Fetch all bookings for a given tourist ID.
 * @param {number} touristId
 * @returns {Promise<Array>} List of BookingDTOs
 */
export const getTouristBookings = async (touristId) => {
  try {
    const response = await axios.get(`${BASE_URL}/tourist/${touristId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching tourist bookings:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Fetch cancelled bookings for a given tourist ID.
 * @param {number} touristId
 * @returns {Promise<Array>} List of BookingDTOs
 */
export const getCancelledBookings = async (touristId) => {
  try {
    const response = await axios.get(`${BASE_URL}/tourist/${touristId}/cancelled`);
    return response.data;
  } catch (error) {
    console.error('Error fetching cancelled bookings:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Fetch all bookings for a given agency ID.
 * @param {number} agencyId
 * @returns {Promise<Array>} List of BookingDTOs
 */
export const getAgencyBookings = async (agencyId) => {
  try {
    const response = await axios.get(`${BASE_URL}/by-agency/${agencyId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching agency bookings:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Confirm a booking by ID.
 * @param {number} bookingId
 * @returns {Promise<string>} Confirmation message
 */
export const confirmBookingById = async (bookingId) => {
  try {
    const response = await axios.post(`${BASE_URL}/${bookingId}/confirm`);
    return response.data;
  } catch (error) {
    console.error('Error confirming booking:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Cancel a booking by ID.
 * @param {number} bookingId
 * @returns {Promise<void>}
 */
export const cancelBookingById = async (bookingId) => {
  try {
    await axios.post(`${BASE_URL}/${bookingId}/cancel`);
  } catch (error) {
    console.error('Error cancelling booking:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Restore a cancelled booking by ID.
 * @param {number} bookingId
 * @returns {Promise<string>} Restore confirmation
 */
export const restoreBookingById = async (bookingId) => {
  try {
    const response = await axios.post(`${BASE_URL}/${bookingId}/restore`);
    return response.data;
  } catch (error) {
    console.error('Error restoring booking:', error.response?.data || error.message);
    throw error;
  }
};

/**
 * Fetches available seats for a given tour schedule.
 * @param {number|string} scheduleId
 * @returns {Promise<number|null>}
 */
export const fetchAvailability = async (scheduleId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/schedules/${scheduleId}/availability`);
    return response.data.availableBookings;
  } catch (error) {
    console.error('Failed to fetch availability:', error.response?.data || error.message);
    return null;
  }
};