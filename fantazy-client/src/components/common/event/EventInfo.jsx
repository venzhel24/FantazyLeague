import React from 'react';
import classes from './EventInfo.module.css';
import {useNavigate} from "react-router-dom";

const EventInfo = ({eventData, status}) => {
    const navigate = useNavigate();

    const handleEventInfo = () => {
        navigate('/leaderboard/' + eventData.id);
    }

    if (!eventData || Object.keys(eventData).length === 0) {
        return (
            <div className={classes.event_block_no_event}>
                <h3>No {status} events</h3>
            </div>
        );
    }

    return (
        <div className={classes.event_block} onClick={handleEventInfo}>
            <h3>{eventData.city}</h3>
            <p>Status: {eventData.status}</p>
            <p>{eventData.startDate.replace(/-/g, '.')} - {eventData.endDate.replace(/-/g, '.')}</p>
        </div>
    );
};

export default EventInfo;