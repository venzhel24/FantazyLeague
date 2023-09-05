import React, {useContext, useEffect} from 'react';
import {Route, Routes} from "react-router-dom";
import {AuthContext} from "../context";
import {adminRoutes, guestRoutes, userRoutes} from "../router/routes";
import Loader from "./UI/loader/Loader";
import ErrorBlock from "./UI/error/ErrorBlock";

const AppRouter = () => {
    const {currentUser, setCurrentUser, isLoading} = useContext(AuthContext);

    if (isLoading) {
        return <Loader/>;
    }

    return (
        <div className="main_content">
            <Routes>
                {currentUser?.role.includes('ROLE_USER')
                    ? userRoutes.map((route) => (
                        <Route
                            element={route.element}
                            path={route.path}
                            exact={route.exact}
                            key={route.path}
                        />
                    ))
                    : currentUser?.role.includes('ROLE_ADMIN')
                        ? adminRoutes.map((route) => (
                            <Route
                                element={route.element}
                                path={route.path}
                                exact={route.exact}
                                key={route.path}
                            />
                        ))
                        : guestRoutes.map((route) => (
                            <Route
                                element={route.element}
                                path={route.path}
                                exact={route.exact}
                                key={route.path}
                            />
                        ))}
            </Routes>
        </div>
    );
};

export default AppRouter;