import styles from "./UserPage.module.css"
import {useUser} from "../../context/UserProvider.jsx";
import {useState} from "react";

const updateUsername = (newUsername, getMe, setUnderChange) => {
    console.log(newUsername)
    fetch("api/user/change-username", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(newUsername)
    }).then((res) => {
        return res.json()
    }).then((res) => {
            localStorage.setItem("token", res.jwt);
            getMe(res.jwt);
            setUnderChange(false);
        }
    )
}

function UserPage() {
    const [underChange, setUnderChange] = useState(false);
    const {user, getMe} = useUser();
    const [username, setUsername] = useState(user.name);
    const [nameError, setNameError] = useState(false);

    const handleUserNameChangeSubmit = (e) => {
        e.preventDefault();
        if (!username.match(/^[a-zA-Z0-9._-]+$/gi)) {
            setNameError(true);
        } else {
            setNameError(false);
            updateUsername({newUsername: username}, getMe, setUnderChange);
        }
    }

    return (
        <div className={styles.pageDiv}>
            <div className={styles.userDiv}>
                <div className={styles.userName}>
                    {underChange ? <>
                        <form onSubmit={handleUserNameChangeSubmit} className={styles.form}>
                            {nameError && <p>Invalid username</p>}
                            <div className={styles.inputDivInForm}>
                                <input value={username}
                                       onChange={(e) => setUsername(e.target.value)}
                                       name={"username"}
                                       id={"username"}/>
                                <button type={"submit"} className={styles.formButton}>Change</button>
                            </div>
                            <p className={styles.instruction}>Username can only contain abc letters, numbers and
                                (&nbsp;_&nbsp;-&nbsp;.&nbsp;) characters</p>
                        </form>
                    </> : <>
                        <h1>{user.name}</h1>
                        <button className={styles.pencilButton} onClick={() => setUnderChange(true)}><p
                            className={styles.pencil}>&#x270f;</p></button>
                    </>
                    }
                </div>
                <h3>{user.email}</h3>
            </div>
        </div>
    )
}

export default UserPage;