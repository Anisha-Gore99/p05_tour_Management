import axios from 'axios';

const SCHEDULE_API_URL = 'http://localhost:8080/api';

export const getSchedulesByPackage = async (packageId) => {
    try {
        const response = await axios.get(`${SCHEDULE_API_URL}/by-package/${packageId}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching schedules for package ${packageId}:`, error);
        throw error;
    }
};