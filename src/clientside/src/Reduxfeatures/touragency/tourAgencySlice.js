import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    tourAgencyUser: null, 
    loading: false,
    error: null,
};

const touragencySlice = createSlice({
    name: 'touragency',
    initialState,
    reducers: {
        // Sets the tour agency user on successful login
        setTourAgencyUser: (state, action) => {
            state.tourAgencyUser = action.payload;
            state.loading = false;
            state.error = null;
        },
        // Clears the tour agency user on logout
        clearTourAgencyUser: (state) => {
            state.tourAgencyUser = null;
            state.loading = false;
            state.error = null;
        },
        // Sets a loading state
        tourAgencyLoginStart: (state) => {
            state.loading = true;
            state.error = null;
        },
        // Handles login failure for tour agencies
        tourAgencyLoginFailure: (state, action) => {
            state.tourAgencyUser = null;
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const { setTourAgencyUser, clearTourAgencyUser, tourAgencyLoginStart, tourAgencyLoginFailure } = touragencySlice.actions;

export default touragencySlice.reducer;