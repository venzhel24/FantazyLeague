import React, {useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import Loader from "../components/UI/loader/Loader";
import GuestService from "../services/GuestService";
import {Navigate, useNavigate} from "react-router-dom";

const Events = () => {
    const [events, setEvents] = useState([]);
    const navigate = useNavigate();

    const [fetchEvents, isLoading, error] = useFetching(async () => {
        const response = await GuestService.getAllEvents();
        setEvents(response.data);
    });

    useEffect(() => {
        fetchEvents();
    }, []);

    return (
        isLoading ? (
            <Loader/>
        ) : (
            <div className="column">
                <div className="heading__table">
                    <h2>Events</h2>
                </div>
                <table>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>City</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>Status</th>
                        <th>Races</th>
                        <th>Fantazy</th>
                    </tr>
                    </thead>
                    <tbody>
                    {events.map((event) => (
                        <tr key={event.id}>
                            <td>{event.id}</td>
                            <td>{event.city}</td>
                            <td>{event.startDate}</td>
                            <td>{event.endDate}</td>
                            <td>{event.status}</td>
                            <td className={"td__link"}  onClick={() => navigate(`/event-races/${event.id}`)}>click</td>
                            <td className={"td__link"}  onClick={() => navigate(`/leaderboard/${event.id}`)}>click</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        )
    );
};

export default Events;