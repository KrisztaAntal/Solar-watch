import {Link, Outlet} from 'react-router-dom';
import styles from "./NavBar.module.css";
import {useState} from "react";
import Footer from "../footer/Footer.jsx";

function NavBar() {
    const [isOpen, setIsOpen] = useState(false);

    const toggleMenu = () => {
        setIsOpen(!isOpen);
    };
    return (
        <>
            <nav className={styles.navbar}>
                <div className={styles.logo}>SolarWatch</div>
                <button className={styles.hamburger} onClick={toggleMenu}>
                    ☰
                </button>
                <div className={`${styles.navLinks} ${isOpen ? styles.navLinksOpen : ''}`}>
                    <Link to={"/solar-watch"} onClick={() => setIsOpen(false)}>
                        SolarWatch
                    </Link>
                    <Link to={"/login"} onClick={() => setIsOpen(false)}>
                        Login
                    </Link>
                    <Link to={"/signup"} onClick={() => setIsOpen(false)}>
                        SignUp
                    </Link>
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