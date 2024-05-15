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
      console.error("Logout error:", error.response.data);
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
    builder
      .addCase(login.fulfilled, (state, action) => {
        state.user = action.payload;
        window.location.href = "/articles";
      })
      .addCase(login.pending, (state) => {})
      .addCase(login.rejected, (state, action) => {
        state.user = null;
        console.error("Login error:", action.payload);
        alert("Échec de la connexion. Veuillez vérifier vos identifiants.");
      })

      .addCase(register.fulfilled, (state, action) => {
        state.user = action.payload;
      })
      .addCase(register.pending, (state) => {})
      .addCase(register.rejected, (state, action) => {})
      .addCase(logout.fulfilled, (state, action) => {
        state.user = null;
      })
      .addCase(logout.pending, (state) => {})
      .addCase(logout.rejected, (state, action) => {});
  },
});

export default authSlice.reducer;
