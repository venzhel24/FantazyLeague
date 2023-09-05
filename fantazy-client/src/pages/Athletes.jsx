import React, {useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import '../styles/Table.css';
import Loader from "../components/UI/loader/Loader";
import GuestService from "../services/GuestService";

const Athletes = () => {
    const [athletes, setAthletes] = useState([]);

    const [fetchAthletes, isLoading, error] = useFetching(async () => {
        const response = await GuestService.getAllAthletes();
        setAthletes(response.data);
    });

    useEffect(() => {
        fetchAthletes();
    }, []);

    return (
            isLoading ? (
                <Loader/>
            ) : (
                <div className="column">
                    <div className="heading__table">
                        <h2>Athletes</h2>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Country</th>
                        </tr>
                        </thead>
                        <tbody>
                        {athletes.map((athlete) => (
                            <tr key={athlete.id}>
                                <td>{athlete.id}</td>
                                <td>{athlete.name}</td>
                                <td>{athlete.country}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )
    );
};

export default Athletes;