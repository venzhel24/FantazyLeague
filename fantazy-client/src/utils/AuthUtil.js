import jwt from "jwt-decode";

export default class AuthUtil {
    static authHeader() {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user && user.accessToken) {
            return {Authorization: 'Bearer ' + user.accessToken};
        } else {
            return {};
        }
    }

    static calculateTimeUntilExpiration(timeExp) {
        const currentTimeInSeconds = Math.floor(Date.now() / 1000);
        console.log('Token expired in:', timeExp - currentTimeInSeconds);
        return (timeExp - currentTimeInSeconds);
    };

    static isTokenExpired(token) {
        const decodedExpTime = jwt(token).exp;
        const currentTimeInSeconds = Math.floor(Date.now() / 1000);
        return decodedExpTime - currentTimeInSeconds < 0;

    }

}