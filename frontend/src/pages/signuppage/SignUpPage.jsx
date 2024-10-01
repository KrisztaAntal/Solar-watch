import SignupFrom from "../../components/signupform/SignUpForm.jsx";
import styles from "./SignUpPage.module.css";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../context/UserProvider.jsx";

const createUser = (member) => {
    fetch("/api/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(member)
    }).then((res) => {
        if (!res.ok) {
            throw new Error("Could not register user");
        } else {
            console.log("User registered");
        }
    })
}


function SignUpPage() {
    const navigate = useNavigate();

    const handleRegistration = (member) => {
            createUser(member)
            navigate("/login");
    }

    return (<>
            <div className={styles.page}>
                <SignupFrom className={styles.form} onSave={handleRegistration}/>
            </div>
        </>
    )
}

export default SignUpPage;