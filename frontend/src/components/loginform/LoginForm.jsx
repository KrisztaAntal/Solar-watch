import styles from "./LoginForm.module.css";
import {useEffect, useState} from "react";
import {useUser} from "../../context/UserProvider.jsx";
import {useNavigate} from "react-router-dom";

function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loginError, setLoginError] = useState(false);
    const [firstAttempt, setFirstAttempt] = useState(true);
    const navigate = useNavigate();
    const {login, loggedIn} = useUser();

    const handleSubmit = (e) => {
        e.preventDefault();
        login({
            username,
            password
        });
        setFirstAttempt(false)
    }

    useEffect(() => {
        if (loggedIn) {
            navigate("/solar-watch");
            setLoginError(false);
        } else {
            if (!firstAttempt) {
                setLoginError(true);
            }
        }
    }, [loggedIn, navigate, firstAttempt])

    return (
        <form className={styles.form} onSubmit={handleSubmit}>
            {loginError && <p className={styles.error}>Invalid username or password.</p>}
            <div className={styles.formInput}>
                <label>Username:<br/>
                    <input value={username}
                           onChange={(e) => setUsername(e.target.value)}
                           name={"username"}
                           id={"username"}/>
                </label>
            </div>
            <div className={styles.formInput}>
                <label>Password:<br/>
                    <input type={"password"} value={password}
                           onChange={(e) => setPassword(e.target.value)}
                           name={"password"}
                           id={"password"}/><br/>
                </label>
            </div>
            <button className={styles.formButton}><h2>Login</h2></button>
        </form>
    )
}

export default LoginForm;