import './styles/App.css';
import './styles/MainContent.css';
import {useContext, useEffect, useState} from "react";
import Navbar from "./components/UI/navbar/Navbar";
import AppRouter from "./components/AppRouter";
import {BrowserRouter} from "react-router-dom";
import {AuthContext} from "./context";
import AuthService from "./services/AuthService";
import jwt from "jwt-decode";
import TokenService from "./services/TokenService";
import AuthUtil from "./utils/AuthUtil";

function App() {
    const [currentUser, setCurrentUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const checkUserAuthentication = async () => {
        const timeUntilExpiration = AuthUtil.calculateTimeUntilExpiration(currentUser?.exp);
        if (timeUntilExpiration < 0) {
            console.log('Access token was expired');
            try {
                const newUser = await AuthService.refreshToken();
                const decodedNewUser = jwt(newUser);
                setCurrentUser(decodedNewUser);
                console.log(decodedNewUser);
            } catch (error) {
                console.log('Error refreshing token');
                console.log(error.message);
                AuthService.logout();
                setCurrentUser(null);
            }
        }
    };

    useEffect(() => {
        const user = AuthService.getCurrentUserTokens();
        if(user) {
            let decodedUserToken = jwt(user.accessToken);
            const expTime = AuthUtil.calculateTimeUntilExpiration(decodedUserToken.exp);
            if(expTime <= 0) {
                console.log('Access token was expired');
                AuthService.refreshToken()
                    .then((newToken) => {
                        decodedUserToken = jwt(newToken);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
            setCurrentUser(decodedUserToken);
            setIsLoading(false);
            console.log(decodedUserToken);
        } else {
            console.log('Anonymous user');
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        if (!isLoading && currentUser) {
            const intervalId = setInterval(checkUserAuthentication, 60000);
            return () => {
                clearInterval(intervalId);
            };
        }
    }, [isLoading, currentUser]);

    return (
        <AuthContext.Provider value={{
            currentUser,
            setCurrentUser,
            isLoading
        }}>
            <BrowserRouter>
                <Navbar></Navbar>
                <AppRouter></AppRouter>
            </BrowserRouter>
        </AuthContext.Provider>
    );
}

export default App;
