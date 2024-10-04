import React from 'react';
import {Link, Outlet, useNavigate} from 'react-router-dom';
import styles from "./NavBar.module.css";
import {useEffect, useState} from "react";
import Footer from "../footer/Footer.jsx";
import {useUser} from "../../context/UserProvider.jsx";
import logo from "../../assets/solar-watch-logo.svg";

function NavBar() {
    const [isOpen, setIsOpen] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();
    const {user, logout} = useUser();

    const toggleMenu = () => {
        setIsOpen(!isOpen);
    };

    useEffect(() => {
        if (user) {
            setIsLoggedIn(true)
        }
    }, [user])

    return (
        <>
            <nav className={styles.navbar}>
                <div className={styles.logo} onClick={() => navigate("/")}><img src={logo} alt={"SolarWatch"}/></div>
                <button className={styles.hamburger} onClick={toggleMenu}>
                    ☰
                </button>
                <div className={`${styles.navLinks} ${isOpen ? styles.navLinksOpen : ''}`}>
                    {!isLoggedIn &&
                        <>
                            <Link to={"/login"} onClick={() => setIsOpen(false)}>
                                Login
                            </Link>
                            <Link to={"/signup"} onClick={() => setIsOpen(false)}>
                                SignUp
                            </Link>
                        </>
                    }
                    {isLoggedIn && <>
                        <Link to={"/solar-watch"} onClick={() => setIsOpen(false)}>
                            Get times
                        </Link>
                        <Link to={"/user-page"} onClick={() => setIsOpen(false)}>
                            User page
                        </Link>
                        <Link to={"/"} onClick={() => {
                            logout();
                            setIsLoggedIn(false);
                        }}>Logout</Link>
                    </>
                    }
                </div>
            </nav>
            <div>
                <Outlet/>
            </div>
            <Footer/>
        </>
    )
}

export default NavBar;