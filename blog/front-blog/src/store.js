import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./features/LoginSlice";
import articlesReducer from "./features/ArticleSlice";
import commentsReducer from "./features/CommentSlice";

const store = configureStore({
  reducer: {
    auth: authReducer,
    articles: articlesReducer,
    comments: commentsReducer,
  },
});

export default store;
