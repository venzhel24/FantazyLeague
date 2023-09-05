import React, {useContext, useEffect, useState} from 'react';
import AuthService from "../../services/AuthService";
import {Link, useNavigate} from 'react-router-dom';
import Loader from "../UI/loader/Loader";
import '../../styles/Auth.css';
import {AuthContext} from "../../context";
import DefaultModal from "../UI/modal/DefaultModal";
import DefaultButton from "../UI/button/DefaultButton";
import SpinLoader from "../UI/loader/SpinLoader";

const Login = ({visible, setVisible}) => {
    const navigate = useNavigate();

    const {currentUser, setCurrentUser} = useContext(AuthContext);

    useEffect(() => {
        if(currentUser) {
            navigate('/');
        }
    }, []);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loadingAuth, setLoadingAuth] = useState(false);
    const [message, setMessage] = useState("");

    const required = (value) => {
        if (!value) {
            return "This field is required!";
        }
    };

    const handleLogin = (e) => {
        e.preventDefault();

        setMessage("");
        setLoadingAuth(true);

        if (!username || !password) {
            setMessage("Username and password are required.");
            setLoadingAuth(false);
            return;
        }

        AuthService.login(username, password)
            .then((response) => {
                navigate("/");
                window.location.reload();
            })
            .catch((error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setLoadingAuth(false);
                setMessage(resMessage);
            });
    };

    return (
        <DefaultModal visible={visible} setVisible={setVisible}>
            <h1>Login</h1>
            {message && (
                <div className="form-group">
                    <div className="error-message">{message}</div>
                </div>
            )}
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        className="form-control"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        onBlur={() => setMessage(required(username))}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        className="form-control"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onBlur={() => setMessage(required(password))}
                    />
                </div>

                <div className="form-group">
                    <button
                        className="login-button"
                        type="submit"
                        disabled={loadingAuth}
                    >
                        {loadingAuth && (
                            <SpinLoader/>
                        )}
                        <span>Login</span>
                    </button>
                </div>
            </form>
            <button className={"register-button"} onClick={() => navigate('/register')}>Register</button>
        </DefaultModal>
    );
};

export default Login;