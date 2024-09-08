import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import MainPage from "./pages/mainpage/MainPage.jsx";
import NavBar from "./components/navbar/NavBar.jsx";
import UserProvider from "./context/UserProvider.jsx";
import SignUpPage from "./pages/signuppage/SignUpPage.jsx";
import LogInPage from "./pages/loginpage/LoginPage.jsx";


const router = createBrowserRouter([{
    path: "/",
    element: <NavBar/>,
    children: [
        {
            path: "/",
            element: <MainPage/>
        },
        {
            path: "/signup",
            element: <SignUpPage/>
        },
        {
            path: "/login",
            element: <LogInPage/>
        }
    ]
}
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <UserProvider>
            <RouterProvider router={router}/>
        </UserProvider>
    </React.StrictMode>,
)
