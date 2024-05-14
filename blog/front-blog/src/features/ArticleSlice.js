import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { accountService } from "../Services/accountServices";

const BASE_API_URL = "http://localhost:8090";

// Fetch all articles
export const fetchAllArticles = createAsyncThunk(
  "articles/fetchAll",
  async (_, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${BASE_API_URL}/articles`);
      return response.data;
    } catch (error) {
      console.error("Fetch all articles error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Fetch article by ID
export const fetchArticleById = createAsyncThunk(
  "articles/fetchById",
  async (articleId, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${BASE_API_URL}/articles/${articleId}`);
      return response.data;
    } catch (error) {
      console.error("Fetch article by ID error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Fetch articles by category
export const fetchArticlesByCategory = createAsyncThunk(
  "articles/fetchByCategory",
  async (categoryName, { rejectWithValue }) => {
    try {
      const response = await axios.get(
        `${BASE_API_URL}/articles/category/${categoryName}`
      );
      return response.data;
    } catch (error) {
      console.error("Fetch articles by category error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Create a new article
export const createArticle = createAsyncThunk(
  "articles/create",
  async (newArticle, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.post(
        `${BASE_API_URL}/articles`,
        newArticle,
        { headers }
      );
      return response.data;
    } catch (error) {
      console.error("Create article error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Update an article
export const updateArticle = createAsyncThunk(
  "articles/update",
  async ({ id, updatedArticle }, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.put(
        `${BASE_API_URL}/articles/${id}`,
        updatedArticle,
        { headers }
      );
      return response.data;
    } catch (error) {
      console.error("Update article error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Delete an article
export const deleteArticle = createAsyncThunk(
  "articles/delete",
  async (id, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.delete(`${BASE_API_URL}/articles/${id}`, {
        headers,
      });
      return response.data;
    } catch (error) {
      console.error("Delete article error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

const articleSlice = createSlice({
  name: "articles",
  initialState: {
    articles: [],
    article: null,
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllArticles.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllArticles.fulfilled, (state, action) => {
        state.loading = false;
        state.articles = action.payload;
      })
      .addCase(fetchAllArticles.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchArticleById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchArticleById.fulfilled, (state, action) => {
        state.loading = false;
        state.article = action.payload;
      })
      .addCase(fetchArticleById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchArticlesByCategory.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchArticlesByCategory.fulfilled, (state, action) => {
        state.loading = false;
        state.articles = action.payload;
      })
      .addCase(fetchArticlesByCategory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(createArticle.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createArticle.fulfilled, (state, action) => {
        state.loading = false;
        state.articles.push(action.payload);
      })
      .addCase(createArticle.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(updateArticle.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(updateArticle.fulfilled, (state, action) => {
        state.loading = false;
        const index = state.articles.findIndex(
          (article) => article.id === action.payload.id
        );
        if (index !== -1) {
          state.articles[index] = action.payload;
        }
      })
      .addCase(updateArticle.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(deleteArticle.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(deleteArticle.fulfilled, (state, action) => {
        state.loading = false;
        state.articles = state.articles.filter(
          (article) => article.id !== action.meta.arg
        );
      })
      .addCase(deleteArticle.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default articleSlice.reducer;
