import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getTourPackageById, getAllTourPackages } from '../../api/tours';

export const fetchTourDetails = createAsyncThunk(
  'tour/fetchTourDetails',
  async (packageId, { rejectWithValue }) => {
    try {
      const response = await getTourPackageById(packageId);
      return response;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const fetchAllTours = createAsyncThunk(
  'tour/fetchAllTours',
  async (_, { rejectWithValue }) => {
    try {
      const response = await getAllTourPackages();
      return response;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const tourSlice = createSlice({
  name: 'tour',
  initialState: {
    tours: [],
    currentTour: null,
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Fetch all tours
      .addCase(fetchAllTours.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllTours.fulfilled, (state, action) => {
        state.loading = false;
        state.tours = action.payload;
      })
      .addCase(fetchAllTours.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      // Fetch single tour details
      .addCase(fetchTourDetails.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchTourDetails.fulfilled, (state, action) => {
        state.loading = false;
        state.currentTour = action.payload;
      })
      .addCase(fetchTourDetails.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default tourSlice.reducer;