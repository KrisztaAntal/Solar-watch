import {useState} from "react";
import styles from "./SignUpForm.module.css";

function SignupFrom({onSave}) {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [usernameError, setUsernameError] = useState(false);
    const [passError, setPassError] = useState(false);
    const [emailError, setEmailError] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        const validEmail = email.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i);
        const validUser = username.match(/^[a-zA-Z0-9._-]+$/gi);
        const validPassword = password === passwordRepeat;

        changeErrorStateBasedOnUserDataValidation(validEmail, validUser, validPassword);

        if (validPassword && validEmail && validUser) {
            return onSave({
                username,
                email,
                password
            });
        }
    }

    const changeErrorStateBasedOnUserDataValidation = (validEmail, validUser, validPassword) => {
        if (validUser) {
            setUsernameError(false);
        } else {
            setUsernameError(true);
        }
        if (validEmail) {
            setEmailError(false);
        } else {
            setEmailError(true);
        }
        if (validPassword) {
            setPassError(false);
        } else {
            setPassError(true);
        }
    }

    return (
        <form className={styles.form} onSubmit={handleSubmit}>
            <div className={styles.formInput}>
                <label>Username:<br/>
                    {usernameError && <p className={styles.error}>Invalid username.</p>}
                    <input value={username}
                           onChange={(e) => setUsername(e.target.value)}
                           name={"username"}
                           id={"username"}/>
                </label>
                <p className={styles.instruction}>Username can only contain abc letters, numbers and (&nbsp;_&nbsp;-&nbsp;.&nbsp;) characters</p>
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
                <label>Repeated password:<br/>
                    {passError && <p className={styles.error}>Repeated password is different.</p>}
                    <input type={"password"} value={passwordRepeat}
                           onChange={(e) => setPasswordRepeat(e.target.value)}
                           name={"passwordRepeat"}
                           id={"passwordRepeat"}/><br/>
                </label>
            </div>
            <button className={styles.formButton}><h2>SignUp</h2></button>
        </form>
    )
}

export default SignupFrom;