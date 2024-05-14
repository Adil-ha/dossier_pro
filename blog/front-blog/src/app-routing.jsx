import { createBrowserRouter, redirect, Navigate } from "react-router-dom";
import App from "./App";
import ErrorPage from "./Pages/ErrorPage";
import Register from "./Pages/Auth/Register";
import Articles from "./Pages/Articles/Articles";
import ArticleDetails from "./Pages/Articles/ArticleDetails";
import Login from "./Pages/Auth/Login";
import { accountService } from "./Services/accountServices";

const authCheck = () => {
    if (accountService.isLogged()) {
      return true;
    } else {
      return redirect("/login");
    }
}


const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        errorElement: <ErrorPage/>,
        children:[
            {
                path: "/",
                element: <Navigate to="/articles" />, 
            },
            {
                path: "/articles",
                element:<Articles/>,
               
            },
            {
                path: "/articleDetails/:id",
                element:<ArticleDetails/>,
            
            }
        ]
    },
    {
        path: "/login",
        element : <Login/>,
       
    },
    {
        path: "/register",
        element : <Register/>,
      
    }
    

])

export default router;