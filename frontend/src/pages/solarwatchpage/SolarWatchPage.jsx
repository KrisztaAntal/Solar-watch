import {useState} from "react";
import {useNavigate} from "react-router-dom";
import styles from "./SolarWatchPage.module.css"
import Sunrise from "../../components/timeresult/sunrise/Sunrise.jsx";
import Sunset from "../../components/timeresult/sunset/Sunset.jsx";
import SunRiseForm from "../../components/sunriseform/SunriseForm.jsx";
import SunsetForm from "../../components/sunsetform/SunsetForm.jsx";


const getSunrise = (sunriseRequest, navigate) => {
    const token = localStorage.getItem("token");
    return fetch(`/api/sunrise?date=${sunriseRequest.date}&city=${sunriseRequest.city}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`
        },
    }).then((res) => {
        if (res.status === 401) {
            navigate("/login");
        }
        return res.json();
    });
}

const getSunset = (sunsetRequest, navigate) => {
    const token = localStorage.getItem("token");
    return fetch(`/api/sunset?date=${sunsetRequest.date}&city=${sunsetRequest.city}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`
        },
    }).then((res) => {
        if (res.status === 401) {
            navigate("/login");
        }
        return res.json();
    });
}

function SolarWatchPage() {
    const [setOrRise, setSetOrRise] = useState("");
    const [sunRiseTimeList, setSunRiseTimeList] = useState(null);
    const [sunSetTimeList, setSunSetTimeList] = useState(null);
    const navigate = useNavigate();

    const handleSunset = (sunsetRequest) => {
        getSunset(sunsetRequest, navigate)
            .then((res) => {
                    setSunSetTimeList(res);
                    setSetOrRise("");
                }
            );
    }
    const handleSunrise = (sunriseRequest) => {
        getSunrise(sunriseRequest, navigate)
            .then((res) => {
                    setSunRiseTimeList(res);
                    setSetOrRise("");
                }
            );
    }


    return (
        <div className={styles.page}>
            {setOrRise === "" && <div className={styles.getTimeButtons}>
                <button
                    className={styles.timeButton}
                    onClick={() => {
                        setSetOrRise("rise");
                        setSunSetTimeList("");
                        setSunRiseTimeList("");
                    }}
                ><h2>Get time of sunrise</h2>
                </button>
                <button
                    className={styles.timeButton}
                    onClick={() => {
                        setSetOrRise("set");
                        setSunSetTimeList("");
                        setSunRiseTimeList("");
                    }}
                ><h2>Get time of sunset</h2>
                </button>
            </div>
            }
            <div className={styles.sunriseDiv}>
                {sunRiseTimeList && sunRiseTimeList.map((sunriseTime) => {
                    return <Sunrise result={sunriseTime} key={sunriseTime.time}/>
                })}
            </div>
            <div className={styles.sunsetDiv}>
                {sunSetTimeList && sunSetTimeList.map((sunsetTime) => {
                    return <Sunset result={sunsetTime} key={sunsetTime.time}/>
                })}
            </div>
            {setOrRise === "rise" && <SunRiseForm onSave={handleSunrise}/>}
            {setOrRise === "set" && <SunsetForm onSave={handleSunset}/>}
        </div>
    )
}

export default SolarWatchPage;