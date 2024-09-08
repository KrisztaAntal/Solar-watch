import styles from "./MainPage.module.css"

function MainPage() {
    return (
        <div className={styles.main} >
            <h1>Welcome to SolarWatch!</h1>
            <h3>Please register or login to search sunset or sunrise times.</h3>
        </div>
    )
}

export default MainPage;