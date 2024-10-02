import styles from "./UserPage.module.css"
import {useUser} from "../../context/UserProvider.jsx";
import {useState} from "react";

const updateUsername = (newUsername, getMe, setUnderChange) => {
    fetch("api/user/change-username", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(newUsername)
    })
        .then((res) => res.json())
        .then((res) => {
                localStorage.setItem("token", res.jwt);
                getMe(res.jwt);
                setUnderChange(false);
            }
        )
}

const updateEmail = (newEmail, getMe, setUnderChange) => {
    const jwt = localStorage.getItem("token");
    fetch("/api/user/change-email", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`
        },
        body: JSON.stringify(newEmail)
    })
        .then((res) => res.json())
        .then(() => {
            getMe(jwt);
            setUnderChange(false);
        })
}

const updatePassword = (newPassword, getMe, setUnderChange) => {
    fetch("/api/user/change-password", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify((newPassword))
    })
        .then((res) => res.json())
        .then((res) => {
            localStorage.setItem("token", res.jwt);
            getMe(res.jwt);
            setUnderChange(false);
        })
}

function UserPage() {
    const [nameUnderChange, setNameUnderChange] = useState(false);
    const [emailUnderChange, setEmailUnderChange] = useState(false);
    const [passwordUnderChange, setPasswordUnderChange] = useState(false);
    const {user, getMe} = useUser();
    const [username, setUsername] = useState(user.name);
    const [email, setEmail] = useState(user.email);
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [nameError, setNameError] = useState(false);
    const [emailError, setEmailError] = useState(false);
    const [passError, setPassError] = useState(false);

    const handleUserNameChangeSubmit = (e) => {
        e.preventDefault();
        if (!username.match(/^[a-zA-Z0-9._-]+$/gi)) {
            setNameError(true);
        } else {
            setNameError(false);
            updateUsername({newUsername: username}, getMe, setNameUnderChange);
        }
    }

    const handleEmailChangeSubmit = (e) => {
        e.preventDefault();
        if (!email.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i)) {
            setEmailError(true);
        } else {
            setEmailError(false);
            updateEmail({newEmail: email}, getMe, setEmailUnderChange);
        }
    }

    const handlePasswordChangeSubmit = (e) => {
        e.preventDefault();
        if (password.length >= 1 && password === repeatedPassword) {
            setPassError(false);
            updatePassword({password: password}, getMe, setPasswordUnderChange)
        } else {
            setPassError(true);
        }
    }

    return (
        <div className={styles.pageDiv}>
            <div className={styles.userDiv}>

                <div className={styles.userName}>
                    {nameUnderChange ?
                        <form onSubmit={handleUserNameChangeSubmit} className={styles.form}>
                            {nameError && <p className={styles.error}>Invalid username.</p>}
                            <div className={styles.nameInputDivInForm}>
                                <label>Username:<br/>
                                    <input value={username}
                                           onChange={(e) => setUsername(e.target.value)}
                                           className={styles.changeInput}
                                           name={"username"}
                                           id={"username"}
                                    />
                                </label>
                                <div className={styles.buttons}>
                                    <button type={"submit"} className={styles.formButton}>Change</button>
                                    <button type={"button"} className={styles.formButton}
                                            onClick={() => setNameUnderChange(false)}>X
                                    </button>
                                </div>
                            </div>
                            <p className={styles.instruction}>
                                Username can only contain abc letters, numbers and
                                (&nbsp;_&nbsp;-&nbsp;.&nbsp;) characters
                            </p>
                        </form>
                        :
                        <>
                            <h1>{user.name}</h1>
                            <button className={styles.pencilButton} onClick={() => {
                                setNameUnderChange(true);
                                setPasswordUnderChange(false);
                                setEmailUnderChange(false);
                            }}>
                                <p className={styles.pencil}>&#x270f;</p>
                            </button>
                        </>
                    }
                </div>

                <div className={styles.email}>
                    {emailUnderChange ?
                        <form onSubmit={handleEmailChangeSubmit}>
                            {emailError &&
                                <p className={styles.error}>Invalid email form. Please add a valid email.</p>}
                            <div className={styles.emailInputDivInForm}>
                                <label>Email: <br/>
                                    <input value={email}
                                           onChange={(e) => setEmail(e.target.value)}
                                           className={styles.changeInput}
                                           name={"email"}
                                           id={"email"}
                                    />
                                </label>
                                <div className={styles.buttons}>

                                    <button type={"submit"} className={styles.formButton}>Change</button>
                                    <button type={"button"} className={styles.formButton}
                                            onClick={() => setEmailUnderChange(false)}>X
                                    </button>
                                </div>
                            </div>
                        </form>
                        :
                        <>
                            <h3>{user.email}</h3>
                            <button className={styles.pencilButton} onClick={() => {
                                setEmailUnderChange(true);
                                setPasswordUnderChange(false);
                                setNameUnderChange(false);
                            }}>
                                <p className={styles.pencil}>&#x270f;</p>
                            </button>
                        </>
                    }
                </div>

                <div>
                    {passwordUnderChange ?
                        <form onSubmit={handlePasswordChangeSubmit}>
                            <div className={styles.passwordInputDivInForm}>
                                <div className={styles.passwordChangeInputsDiv}>
                                    {passError && <p className={styles.error}>Repeated password is not matching</p>}
                                    <label>Password:<br/>
                                        <input
                                            type={"password"}
                                            className={styles.changeInput}
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}
                                            name={"password"}
                                            id={"password"}
                                        /><br/>
                                    </label>
                                    <label>Repeated password:<br/>
                                        <input
                                            type={"password"}
                                            className={styles.changeInput}
                                            value={repeatedPassword}
                                            onChange={(e) => setRepeatedPassword(e.target.value)}
                                            name={"repeatedPassword"}
                                            id={"repeatedPassword"}
                                        /><br/>
                                    </label>
                                </div>
                                <div className={styles.buttons}>

                                    <button type={"submit"} className={styles.formButton}>Change</button>
                                    <button type={"button"} className={styles.formButton}
                                            onClick={() => setPasswordUnderChange(false)}>X
                                    </button>
                                </div>

                            </div>
                        </form>
                        :
                        <button className={styles.formButton} onClick={() => setPasswordUnderChange(true)}>Change
                            password
                        </button>
                    }
                </div>

            </div>
        </div>
    )
}

export default UserPage;