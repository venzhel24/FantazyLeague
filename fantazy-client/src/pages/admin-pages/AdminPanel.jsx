import React, {useContext} from 'react';
import {AuthContext} from "../../context";
import ErrorBlock from "../../components/UI/error/ErrorBlock";
import Loader from "../../components/UI/loader/Loader";
import DefaultButton from "../../components/UI/button/DefaultButton";
import '../../styles/Admin.css';
import {useNavigate} from "react-router-dom";

const AdminPanel = () => {
    const navigate = useNavigate();
    const {currentUser, setCurrentUser, isLoading} = useContext(AuthContext);

    if (isLoading) {
        return (
            <Loader></Loader>
        )
    }

    if (!currentUser || !currentUser.role.includes('ROLE_ADMIN')) {
        return (
            <div className={"column"}>
                <ErrorBlock errorMessage={'You are not admin'}></ErrorBlock>
            </div>
        )
    }


    return (
        <div className={"column"}>
            <div className={"heading__block"}>
                <h2>Select option</h2>
            </div>
            <div className={"panel_block"}>
                <DefaultButton onClick={() => navigate('/admin/upload')}>Upload results</DefaultButton>
                <DefaultButton onClick={() => navigate('/admin/users')}>Users</DefaultButton>
                <DefaultButton onClick={() => navigate('/admin/create-event')}>Add event</DefaultButton>
                <DefaultButton>Manage athletes</DefaultButton>
                <DefaultButton>Manage events</DefaultButton>
                <DefaultButton>Manage races</DefaultButton>
            </div>
        </div>
    )
        ;
};

export default AdminPanel;