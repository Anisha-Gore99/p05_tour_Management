import { createSlice } from "@reduxjs/toolkit";

// Hydrate Redux from localStorage at startup
const hasUser = !!localStorage.getItem("loggedUser");

export const loggedSlice = createSlice({
  name: "logged",
  initialState: {
    loggedIn: hasUser,
  },
  reducers: {
    login: (state) => { state.loggedIn = true; },
    logout: (state) => { state.loggedIn = false; }
  }
});

export const { login, logout } = loggedSlice.actions;
export default loggedSlice.reducer;
