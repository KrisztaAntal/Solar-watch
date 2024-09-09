import {useState} from "react";
import styles from "./SunRiseForm.module.css"

function SunRiseForm({onSave}) {
    const [date, setDate] = useState("");
    const [city, setCity] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
/*
        console.log({date, city})
*/
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
                               id={"sunriseDate"}/>
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
                <button className={styles.formButton}><h2>Get sunrise</h2></button>
            </form>
        </>
    )
}

export default SunRiseForm;