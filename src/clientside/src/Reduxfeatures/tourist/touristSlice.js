import { createAsyncThunk } from '@reduxjs/toolkit';

export const registerTourist = createAsyncThunk(
  'tourist/registerTourist',
  async (touristData, thunkAPI) => {
    const response = await fetch('http://localhost:8080/api/tourist/registertourist', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(touristData)
    });
    if (!response.ok) throw new Error('Failed to register tourist');
    return await response.json(); 
  }
);
