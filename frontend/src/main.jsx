import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import MainPage from "./pages/mainpage/MainPage.jsx";
import NavBar from "./components/navbar/NavBar.jsx";



const router = createBrowserRouter([{
    path: "/",
    element: <NavBar/>,
    children: [
        {
            path: "/",
            element: <MainPage/>
        },
        ]
}
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <RouterProvider router={router}/>
  </React.StrictMode>,
)
