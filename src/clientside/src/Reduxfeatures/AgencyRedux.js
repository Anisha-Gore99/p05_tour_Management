import { createSlice } from '@reduxjs/toolkit';

const agencySlice = createSlice({
  name: 'agency',
  initialState: { data: null },
  reducers: {
    setAgency: (state, action) => { state.data = action.payload; },
    clearAgency: (state) => { state.data = null; },
  },
});

export const { setAgency, clearAgency } = agencySlice.actions;
export default agencySlice.reducer;