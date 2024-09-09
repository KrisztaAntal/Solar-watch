import styles from "./Sunrise.module.css";

function Sunrise({result}){
    return (
        <div className={styles.sunrise}>
            <p>Time of sunrise</p>
            <h1>{result.time}</h1>
            <p>{result.date}</p>
            <p>{result.city.name}, {result.city.country}, {result.city.state}</p>
            <p>Longitude: {result.city.lon}</p>
            <p>Latitude: {result.city.lat}</p>
        </div>
    )
}
export default Sunrise;