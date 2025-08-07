import axios from "axios";

// Actual backend URL & port
const BASE_URL = "http://localhost:8082/api";
const SCHEDULE_URL = "http://localhost:8082/api/schedules"; 

// Fetch all packages
export const fetchTourPackages = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/packages`);
    return response.data;
  } catch (error) {
    console.error("Error fetching packages:", error);
    throw error;
  }
};

// ✅ FIXED: Fetch a package by ID (correct endpoint)
export const fetchTourById = async (id) => {
  const response = await axios.get(`${BASE_URL}/packages/${id}`);
  return response.data;
};
// Fetch all schedules for a specific package
export const fetchScheduleByPackageId = async (packageId) => {
  const response = await axios.get(`${SCHEDULE_URL}/by-package/${packageId}`);
  return response.data;
};
