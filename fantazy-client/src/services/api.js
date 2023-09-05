import axios from "axios";
import TokenService from "./TokenService";
import {BACKEND_URL} from "./Path";


const instance = axios.create({
    baseURL: BACKEND_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

instance.interceptors.request.use(
    (config) => {
        console.log("Interceptor request on fulfilled");
        const token = TokenService.getLocalAccessToken();
        if (token) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        return config;
    },
    (error) => {
        console.log("Interceptor request on rejected");
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (res) => {
        console.log("Interceptor response on fulfilled");
        return res;
    },
    async (err) => {
        console.log("Interceptor response on rejected");
        const originalConfig = err.config;
        console.log(err);

        if (originalConfig.url !== "/api/v1/auth/authenticate" && err.response) {
            if (err.response.status === 401 && err.response.status === 403 && !originalConfig._retry) {
                originalConfig._retry = true;

                try {
                    const rs = await instance.post("/api/v1/auth/refresh-token", {
                        refreshToken: TokenService.getLocalRefreshToken(),
                    });

                    const { accessToken } = rs.data?.accessToken;
                    TokenService.updateLocalAccessToken(accessToken);

                    return instance(originalConfig);
                } catch (_error) {
                    return Promise.reject(_error);
                }
            }
        }

        return Promise.reject(err);
    }
);

export default instance;