import { accountService } from "../Services/accountServices";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BASE_API_URL = "http://localhost:8090";

export const login = createAsyncThunk(
  "auth/login",
  async (newLogin, { rejectWithValue }) => {
    try {
      const response = await axios.post(`${BASE_API_URL}/login`, newLogin);
      console.log("Login successful:", response.data);
      accountService.saveToken(response.data.bearer);
      return response.data;
    } catch (error) {
      console.error("Login error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const register = createAsyncThunk(
  "auth/register",
  async (newUser, { rejectWithValue }) => {
    try {
      const response = await axios.post(`${BASE_API_URL}/register`, newUser);
      console.log("Registration successful:", response.data);
      return response.data;
    } catch (error) {
      console.error("Registration error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const logout = createAsyncThunk(
  "auth/logout",
  async (_, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.post(`${BASE_API_URL}/logoutt`, null, {
        headers,
      });
      console.log("Disconnection successful:", response.data);
      return response.data;
    } catch (error) {
      console.error("Registration error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

const authSlice = createSlice({
  name: "auth",
  initialState: {
    user: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(login.fulfilled, (state, action) => {
      state.user = action.payload;
    });
    builder.addCase(register.fulfilled, (state, action) => {
      state.user = action.payload;
    });
    builder.addCase(logout.fulfilled, (state, action) => {
      state.user = null;
    });
  },
});

export default authSlice.reducer;
