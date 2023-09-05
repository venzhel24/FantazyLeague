import React, {useEffect, useState} from 'react';
import {useFetching} from "../../../hooks/useFetching";
import AdminService from "../../../services/AdminService";
import ErrorBlock from "../error/ErrorBlock";
import SpinLoader from "../loader/SpinLoader";

const AdminUsersTable = () => {
    const [users, setUsers] = useState(null);
    const [fetchUsers, isUsersLoading, usersError] = useFetching(async () => {
        const usersResponse = await AdminService.getAllUsers();
        setUsers(usersResponse.data);
    });

    useEffect(() => {
        fetchUsers();
    }, []);

    if(usersError) {
        return (
            <ErrorBlock errorMessage={usersError}/>
        )
    }

    if(isUsersLoading) {
        return (
            <SpinLoader/>
        )
    }

    return (
        <div className="column">
            <div className="heading__table">
                <h2>Users</h2>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                        <td>{user.role}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default AdminUsersTable;