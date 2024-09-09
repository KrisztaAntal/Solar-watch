import styles from "./Sunset.module.css";

function Sunset({result}){
    return (
        <div className={styles.sunset}>
            <p>Time of sunset</p>
            <h1>{result.time}</h1>
            <p>{result.date}</p>
            <p>{result.city.name}, {result.city.country}, {result.city.state}</p>
            <p>Longitude: {result.city.lon}</p>
            <p>Latitude: {result.city.lat}</p>
        </div>
    )
}

export default Sunset