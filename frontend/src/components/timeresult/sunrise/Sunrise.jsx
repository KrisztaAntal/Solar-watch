import styles from "./Sunrise.module.css";

function Sunrise({result}) {
    return (
        <div className={styles.sunrise}>
            <p className={styles.timeOfSunriseP}>Time of sunrise</p>
            <h1 className={styles.time}>{result.time}</h1>
            <h4 className={styles.utc}>(UTC)</h4>
            <div className={styles.infoDiv}>
                <p>{result.date}</p>
                <p>{result.city.name}, {result.city.country}, {result.city.state}</p>
                <p>Longitude: {result.city.lon}</p>
                <p>Latitude: {result.city.lat}</p>
            </div>
        </div>
    )
}

export default Sunrise;