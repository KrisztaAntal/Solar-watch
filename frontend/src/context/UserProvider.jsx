import React, {createContext, useContext, useEffect, useState} from 'react'

const UserContext = createContext({});

const getToken = () => localStorage.getItem("token");
const setToken = (token) => localStorage.setItem("token", token);

const setCurrentUser = (user) => sessionStorage.setItem("currentUser", user);

const getInitialState = () => {
    const currentUser = sessionStorage.getItem("currentUser");
    return currentUser ? JSON.parse(currentUser) : null
}

// eslint-disable-next-line react/prop-types
const UserProvider = ({children}) => {
    const [user, setUser] = useState(getInitialState);

    const getMe = (token) => {
        fetch("/api/auth/me", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then((res) => res.json())
            .then((user) => {
                setUser(user);
            });
    };

    useEffect(() => {
        sessionStorage.setItem("currentUser", JSON.stringify(user))
    }, [user])

    const login = (credentials) => {
        fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(credentials)
        })
            .then((res) => res.json())
            .then((res) => {
                const {jwt} = res;
                if (jwt) {
                    setToken(jwt);
                    getMe(jwt);
                    console.log("User logged in");
                    console.log(jwt)
                }
            })
    }

    const logout = () => {
        setUser(null);
        setToken("");
        setCurrentUser("");
    }

    return (
        <UserContext.Provider value={{user, login, logout, getMe}}>
            {children}
        </UserContext.Provider>
    );
}

// eslint-disable-next-line react-refresh/only-export-components
export const useUser = () => useContext(UserContext);

export default UserProvider;