import styles from "./MainPage.module.css"
import {useUser} from "../../context/UserProvider.jsx";

function MainPage() {
    const {user} = useUser();
    return (
        <div className={styles.main} >
            <h1>Welcome to SolarWatch!</h1>
            {!user && <h3>Please register or login to search sunset or sunrise times.</h3>}
        </div>
    )
}

export default MainPage;