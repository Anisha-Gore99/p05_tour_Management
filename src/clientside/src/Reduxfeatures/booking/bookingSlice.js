import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import {
  getTouristBookings,
  getCancelledBookings
} from '../../api/tourist';
import {
  createBooking,
  cancelBookingById
} from '../../api/bookingApi';

const initialState = {
  booking: null,
  bookings: [],
  cancelledBookings: [],
  loading: false,
  status: 'idle', // 'idle' | 'loading' | 'succeeded' | 'failed'
  error: null
};

// Create Booking
export const requestBooking = createAsyncThunk(
  'booking/requestBooking',
  async (bookingData, { rejectWithValue }) => {
    try {
      const response = await createBooking(bookingData);
      return response;
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);

// Fetch All Bookings for Tourist
export const fetchTouristBookings = createAsyncThunk(
  'booking/fetchTouristBookings',
  async (touristId, { rejectWithValue }) => {
    try {
      const bookings = await getTouristBookings(touristId);
      return bookings;
    } catch (err) {
      return rejectWithValue(err.response?.data || err.message);
    }
  }
);

// Fetch Cancelled Bookings
export const fetchCancelledBookings = createAsyncThunk(
  'booking/fetchCancelledBookings',
  async (touristId, { rejectWithValue }) => {
    try {
      const cancelledBookings = await getCancelledBookings(touristId);
      return cancelledBookings;
    } catch (err) {
      return rejectWithValue(err.response?.data || err.message);
    }
  }
);

// Cancel Booking
export const cancelBooking = createAsyncThunk(
  'booking/cancelBooking',
  async (bookingId, { rejectWithValue }) => {
    try {
      await cancelBookingById(bookingId);
      return bookingId;
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);

const bookingSlice = createSlice({
  name: 'booking',
  initialState,
  reducers: {
    clearBookingState: (state) => {
      state.booking = null;
      state.bookings = [];
      state.cancelledBookings = [];
      state.loading = false;
      state.status = 'idle';
      state.error = null;
    }
  },
  extraReducers: (builder) => {
    builder
      // Create Booking
      .addCase(requestBooking.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(requestBooking.fulfilled, (state, action) => {
        state.loading = false;
        state.booking = action.payload;
        state.error = null;
      })
      .addCase(requestBooking.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
        state.booking = null;
      })

      // Fetch Tourist Bookings
      .addCase(fetchTouristBookings.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchTouristBookings.fulfilled, (state, action) => {
        console.log('Fetched bookings:', action.payload);
        state.status = 'succeeded';
        state.bookings = Array.isArray(action.payload) ? action.payload : [];
        state.error = null;
      })
      .addCase(fetchTouristBookings.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
        console.error('Booking fetch failed:', action.payload);
      })

      // Fetch Cancelled Bookings
      .addCase(fetchCancelledBookings.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchCancelledBookings.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.cancelledBookings = Array.isArray(action.payload) ? action.payload : [];
        state.error = null;
      })
      .addCase(fetchCancelledBookings.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })

      // Cancel Booking
      .addCase(cancelBooking.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(cancelBooking.fulfilled, (state, action) => {
        const cancelledId = action.payload;
        state.status = 'succeeded';
        state.bookings = state.bookings.map((b) =>
          (b.bookingId || b.id) === cancelledId ? { ...b, status: 'CANCELLED' } : b
        );
      })
      .addCase(cancelBooking.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  }
});

// Selectors
export const selectTouristBookings = (state) => state.booking.bookings;
export const selectCancelledBookings = (state) => state.booking.cancelledBookings;
export const selectBookingStatus = (state) => state.booking.status;
export const selectBookingError = (state) => state.booking.error;

export const { clearBookingState } = bookingSlice.actions;
export default bookingSlice.reducer;
