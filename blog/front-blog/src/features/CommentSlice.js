import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { accountService } from "../Services/accountServices";

const BASE_API_URL = "http://localhost:8090";

// Fetch all comments for an article
export const fetchCommentsByArticleId = createAsyncThunk(
  "comments/fetchByArticleId",
  async (articleId, { rejectWithValue }) => {
    try {
      const response = await axios.get(
        `${BASE_API_URL}/articles/${articleId}/comments`
      );
      return response.data;
    } catch (error) {
      console.error("Fetch comments by article ID error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Create a new comment
export const createComment = createAsyncThunk(
  "comments/create",
  async (newComment, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.post(
        `${BASE_API_URL}/comments`,
        newComment,
        { headers }
      );
      return response.data;
    } catch (error) {
      console.error("Create comment error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Update a comment
export const updateComment = createAsyncThunk(
  "comments/update",
  async ({ articleId, commentId, updatedComment }, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.put(
        `${BASE_API_URL}/articles/${articleId}/comments/${commentId}`,
        updatedComment,
        { headers }
      );
      return response.data;
    } catch (error) {
      console.error("Update comment error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

// Delete a comment
export const deleteComment = createAsyncThunk(
  "comments/delete",
  async ({ articleId, commentId }, { rejectWithValue }) => {
    try {
      const headers = accountService.getToken();
      const response = await axios.delete(
        `${BASE_API_URL}/articles/${articleId}/comments/${commentId}`,
        {
          headers,
        }
      );
      return response.data;
    } catch (error) {
      console.error("Delete comment error:", error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

const commentSlice = createSlice({
  name: "comments",
  initialState: {
    comments: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCommentsByArticleId.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchCommentsByArticleId.fulfilled, (state, action) => {
        state.loading = false;
        state.comments = action.payload;
      })
      .addCase(fetchCommentsByArticleId.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(createComment.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createComment.fulfilled, (state, action) => {
        state.loading = false;
        state.comments.push(action.payload);
      })
      .addCase(createComment.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(updateComment.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(updateComment.fulfilled, (state, action) => {
        state.loading = false;
        const index = state.comments.findIndex(
          (comment) => comment.id === action.payload.id
        );
        if (index !== -1) {
          state.comments[index] = action.payload;
        }
      })
      .addCase(updateComment.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(deleteComment.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(deleteComment.fulfilled, (state, action) => {
        state.loading = false;
        state.comments = state.comments.filter(
          (comment) => comment.id !== action.meta.arg.commentId
        );
      })
      .addCase(deleteComment.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default commentSlice.reducer;
