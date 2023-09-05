import {BACKEND_URL} from "./Path";
import axios from "axios";
import api from "./api";
import TokenService from "./TokenService";
import {useContext} from "react";
import {AuthContext} from "../context";
import AuthUtil from "../utils/AuthUtil";

const AUTH_URL = BACKEND_URL + '/api/v1/auth'

export default class AuthService {
    static login(username, password) {
        return api
            .post("/api/v1/auth/authenticate", {
                username,
                password
            })
            .then(response => {
                console.log(response.data);
                if (response.data.accessToken) {
                    TokenService.setUserTokens(response.data);
                }

                return response.data;
            });
    }

    static logout() {
        TokenService.removeUserTokens();
    }

    static register(username, email, password) {
        return api.post("/api/v1/auth/register", {
            username,
            email,
            password
        });
    }

    static async refreshToken() {
        try {
            const refreshToken = TokenService.getLocalRefreshToken();
            if(AuthUtil.isTokenExpired(refreshToken)) {
                throw new Error('refresh token was expired');
            }
            const response = await axios.post(
                AUTH_URL + '/refresh-token',
                {},
                {
                    headers: {
                        Authorization: 'Bearer ' + refreshToken
                    }
                }
            );
            const newAccessToken = response.data?.accessToken;
            TokenService.updateLocalAccessToken(newAccessToken);
            console.log('Token refreshed');
            return newAccessToken;
        } catch (error) {
            console.log('Error refresh token');
            throw error;
        }
    }

    static getCurrentUserTokens() {
        return TokenService.getUserTokens();
    }
}