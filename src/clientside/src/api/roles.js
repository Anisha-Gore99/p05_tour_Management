const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Fetches a list of all roles from the backend.
 * @returns {Promise<Array>} A promise that resolves to an array of role objects.
 */
export const getAllRoles = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/roles/getallroles`);

        if (!response.ok) {
            throw new Error('Failed to fetch roles.');
        }
        return await response.json();
    } catch (error) {
        console.error("Error fetching roles:", error);
        throw error;
    }
};
