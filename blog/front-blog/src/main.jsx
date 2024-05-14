import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import router from './app-routing.jsx';
import { RouterProvider } from 'react-router-dom';
import { Provider } from "react-redux";
import store from './store.js';



ReactDOM.createRoot(document.getElementById('root')).render(
  <Provider store={store}>
     <RouterProvider router = {router}/>
  </Provider>
  

);
