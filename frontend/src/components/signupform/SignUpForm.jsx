import {useState} from "react";
import styles from "./SignUpForm.module.css";

function SignupFrom({onSave}) {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [passError, setPassError] = useState(false);
    const [emailError, setEmailError] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
        if (email.match(emailRegex)) {
            setEmailError(false)
        } else {
            setEmailError(true)
        }
        if (password === passwordRepeat) {
            setPassError(false);
        } else {
            setPassError(true);
        }
        if (password === passwordRepeat && email.match(emailRegex)) {
            return onSave({
                username,
                email,
                password
            })
        }
    }

    return (
        <>
            <form className={styles.form} onSubmit={handleSubmit}>
                <div className={styles.formInput}>
                    <label>Username:<br/>
                        <input value={username}
                               onChange={(e) => setUsername(e.target.value)}
                               name={"username"}
                               id={"username"}/>
                    </label>
                </div>
                <div className={styles.formInput}>
                    <label>Email:<br/>
                        {emailError && <p className={styles.error}>Wrong email form, please add a valid email.</p>}
                        <input value={email}
                               type={email}
                               onChange={(e) => setEmail(e.target.value)}
                               name={"email"}
                               id={"email"}/>
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
                <div className={styles.formInput}>
                    <label>Password again:<br/>
                        {passError && <p className={styles.error}>Repeated password is different.</p>}
                        <input type={"password"} value={passwordRepeat}
                               onChange={(e) => setPasswordRepeat(e.target.value)}
                               name={"passwordRepeat"}
                               id={"passwordRepeat"}/><br/>
                    </label>
                </div>
                <button className={styles.formButton}><h2>SignUp</h2></button>
            </form>
        </>
    )
}

export default SignupFrom;