import LoginForm from "../../components/loginform/LoginForm.jsx";
import styles from "./LoginPage.module.css"

function LogInPage() {

    return (
        <>
            <div className={styles.page}>
                <LoginForm />
            </div>
        </>
    )
}

export default LogInPage;