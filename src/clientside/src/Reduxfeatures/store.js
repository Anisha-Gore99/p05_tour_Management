import { configureStore } from '@reduxjs/toolkit';
import userReducer from './user/userSlice';
import tourReducer from './tour/tourSlice';
import bookingReducer from './booking/bookingSlice';
import tourAgencyReducer from './touragency/tourAgencySlice';

// /**
//  * Configures and creates the Redux store.
//  * It combines all the feature-specific reducers into a single root reducer.
//  */
export const store = configureStore({
  reducer: {
    user: userReducer,
    tour: tourReducer,
    booking: bookingReducer,
    touragency: tourAgencyReducer
    
  },
 });

