import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getTouristProfile } from '../../api/tourist';

const initialState = {
  user: null,
  profile: null,
  touristId: null,
  isAuthenticated: false,
  isProfileComplete: false,
  loading: false,
  error: null,
  status: 'idle'
};

// Utility to check profile completeness
const isProfileCompleteCheck = (profile) => {
  return (
    profile &&
    typeof profile.fname === 'string' &&
    typeof profile.lname === 'string' &&
    typeof profile.address === 'string' &&
    typeof profile.dob === 'string' &&
    profile.fname.trim() &&
    profile.lname.trim() &&
    profile.address.trim() &&
    profile.dob.trim()
  );
};

// Async thunk to fetch tourist profile by UID
export const fetchTouristProfile = createAsyncThunk(
  'user/fetchTouristProfile',
  async (uid, { rejectWithValue }) => {
    try {
      const profile = await getTouristProfile(uid);
      return profile;
    } catch (err) {
      if (err.response?.status === 404) {
        return null;
      }
      return rejectWithValue(err.response?.data || err.message);
    }
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    loginStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    loginSuccess: (state, action) => {
      const userData = action.payload;
      const normalizedRole = userData.role?.rname?.replace(/[^a-zA-Z]/g, '').toLowerCase() || null;

      state.user = {
        uid: userData.uid,
        uname: userData.uname,
        email: userData.email,
        phoneNo: userData.phoneNo,
        fname: userData.fname,
        lname: userData.lname,
        role: normalizedRole
      };
      state.isAuthenticated = true;
      state.loading = false;
      state.error = null;

      if (normalizedRole) {
        localStorage.setItem('userRole', normalizedRole);
      }
      if (userData.uid) {
        localStorage.setItem('userId', userData.uid);
      }
    },
    loginFailure: (state, action) => {
      state.user = null;
      state.isAuthenticated = false;
      state.loading = false;
      state.error = action.payload;
    },
    logout: (state) => {
      state.user = null;
      state.profile = null;
      state.touristId = null;
      state.isAuthenticated = false;
      state.isProfileComplete = false;
      state.loading = false;
      state.error = null;
      localStorage.removeItem('userId');
      localStorage.removeItem('userRole');
      localStorage.removeItem('touristId');
    },
    setTouristProfile: (state, action) => {
      const profile = action.payload;
      state.profile = profile;
      state.touristId = profile?.tid || null;
      state.isProfileComplete = isProfileCompleteCheck(profile);

      if (state.user && profile?.phoneNo) {
        state.user.phoneNo = profile.phoneNo;
      }

      if (profile?.tid) {
        localStorage.setItem('touristId', profile.tid);
      }
    },
    clearError: (state) => {
      state.error = null;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchTouristProfile.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchTouristProfile.fulfilled, (state, action) => {
        const profile = action.payload;
        console.log('Fetched touristId:', profile?.tid); // Debug log
        state.status = 'succeeded';

        if (!profile) {
          state.profile = null;
          state.touristId = null;
          state.isProfileComplete = false;
        } else {
          state.profile = profile;
          state.touristId = profile.tid || null;
          state.isProfileComplete = isProfileCompleteCheck(profile);

          if (state.user && profile.phoneNo) {
            state.user.phoneNo = profile.phoneNo;
          }

          if (profile.tid) {
            localStorage.setItem('touristId', profile.tid);
          }
        }
      })
      .addCase(fetchTouristProfile.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  }
});

export const {
  loginStart,
  loginSuccess,
  loginFailure,
  logout,
  setTouristProfile,
  clearError
} = userSlice.actions;

// Selectors
export const selectIsAuthenticated = (state) => state.user.isAuthenticated;
export const selectTouristProfile = (state) => state.user.profile;
export const selectTouristId = (state) => state.user.touristId;
export const selectIsProfileComplete = (state) => state.user.isProfileComplete;
export const selectUser = (state) => state.user.user;
export const selectUserStatus = (state) => state.user.status;
export const selectUserRole = (state) => state.user.user?.role;
export const isTourist = (state) => state.user.user?.role === 'tourist';
export const isTourAgency = (state) => state.user.user?.role === 'touragency';
export const isAdmin = (state) => state.user.user?.role === 'admin';
export const isLoading = (state) => state.user.loading;

export default userSlice.reducer;
