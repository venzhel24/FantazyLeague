import React, { useState } from "react";
import AuthService from "../../services/AuthService";
import "../../styles/Auth.css";

const Register = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const required = (value) => {
        if (!value) {
            return "This field is required!";
        }
    };

    const validEmail = (value) => {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(value)) {
            return "This is not a valid email.";
        }
    };

    const validUsername = (value) => {
        if (value.length < 3 || value.length > 20) {
            return "The username must be between 3 and 20 characters.";
        }
    };

    const validPassword = (value) => {
        if (value.length < 6 || value.length > 40) {
            return "The password must be between 6 and 40 characters.";
        }
    };

    const handleRegister = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        if (!username || !email || !password) {
            setMessage("All fields are required.");
            return;
        }

        AuthService.register(username, email, password)
            .then(() => {
                setMessage("Registration successful. You can now log in.");
                setSuccessful(true);
            })
            .catch((error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setMessage(resMessage);
                setSuccessful(false);
            });
    };

    return (
        <div className="register-container">
            <h1>Register</h1>
            <form onSubmit={handleRegister}>
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        className="form-control"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        onBlur={() => setMessage(validUsername(username))}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        type="text"
                        className="form-control"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        onBlur={() => setMessage(validEmail(email))}
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
                        onBlur={() => setMessage(validPassword(password))}
                    />
                </div>

                <div className="form-group">
                    <button className="register-button" type="submit">
                        Sign Up
                    </button>
                </div>

                {message && (
                    <div className={successful ? "alert-success" : "alert-danger"}>
                        {message}
                    </div>
                )}
            </form>
        </div>
    );
};

export default Register;
