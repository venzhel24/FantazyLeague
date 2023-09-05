import React, {useContext, useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import '../../../styles/Navbar.css';
import {AuthContext} from "../../../context";
import AuthService from "../../../services/AuthService";
import DefaultModal from "../modal/DefaultModal";
import DefaultButton from "../button/DefaultButton";
import Login from "../../auth/Login";

const Navbar = () => {
    const {currentUser, setCurrentUser, isLoading} = useContext(AuthContext);
    const [modalLogin, setModalLogin] = useState(false);
    const navigate = useNavigate();

    const handleLogout = () => {
        AuthService.logout();
        setCurrentUser(null);
        navigate(window.location.pathname);
    };


    return (
        <header>
            <div className="header_left">
                <img src="/images/logo.png" alt="Logo"/>
            </div>
            <div className="header_center">
                <Link to={"/"}>Home</Link>
                <Link to={"/athletes"}>Athletes</Link>
                <Link to={"/events"}>Events</Link>
                <Link to={"/athletess"}>athletes new</Link>
                <Link to={"/"}>Home</Link>
                {currentUser && currentUser.role.includes('ROLE_ADMIN') && <Link to={"admin-panel"}>Admin</Link>}
            </div>
            <div className="header_right">
                {currentUser
                    ? (
                        <div className={"navbar_user_block"}>
                            <p>{currentUser.sub}</p>
                            <Link to="/" onClick={handleLogout}>Log out</Link>
                        </div>
                    )
                    : (
                        <div className={"login_button_block"}>
                            <button onClick={() => setModalLogin(true)}>log in</button>
                        </div>
                    )}
            </div>
            {!currentUser ? (
            <Login
                visible={modalLogin}
                setVisible={setModalLogin}
            >
            </Login>
            ) : null
            }
        </header>
    );
};

export default Navbar;