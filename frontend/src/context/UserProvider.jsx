import {createContext, useCallback, useContext, useEffect, useState} from "react";

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

    const getMe = useCallback((token) => {
        fetch("/api/auth/me", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then((res) => res.json())
            .then((user) => {
                setUser(user);
            });
    }, []);

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
                const {token} = res;
                if (token) {
                    setToken(token);
                    getMe(token);
                    console.log("User logged in")
                }
            })
    }

    const logout = () => {
        setUser(null);
        setToken("");
        setCurrentUser("");
    }

    return (
        <UserContext.Provider value={{user, login, logout}}>
            {children}
        </UserContext.Provider>
    );
}

export const useUser = () => useContext(UserContext);

export default UserProvider;