import {useState} from "react";
import styles from "./SunsetForm.module.css";

function SunsetForm({onSave}){
    const [date, setDate] = useState('');
    const [city, setCity] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        return onSave({
            date,
            city
        })
    }

    return (
        <>
            <form className={styles.form} onSubmit={handleSubmit}>
                <div className={styles.formInput}>
                    <label>Date:<br/>
                        <input type={"date"}
                               value={date}
                               onChange={(e) => setDate(e.target.value)}
                               name={"date"}
                               id={"sunsetDate"}/>
                    </label>
                </div>
                <div className={styles.formInput}>
                    <label>City:<br/>
                        <input value={city}
                               onChange={(e) => setCity(e.target.value)}
                               name={"city"}
                               id={"city"}/>
                    </label>
                </div>
                <button className={styles.formButton} >Get time</button>
            </form>
        </>
    )
}

export default SunsetForm;