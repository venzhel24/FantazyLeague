import React, {useEffect, useState} from 'react';
import '../styles/App.css';
import EventInfo from "../components/common/event/EventInfo";
import Loader from "../components/UI/loader/Loader";
import {useFetching} from "../hooks/useFetching";
import AllEventLink from "../components/common/event/AllEventLink";
import GuestService from "../services/GuestService";
import ErrorBlock from "../components/UI/error/ErrorBlock";

const Home = () => {
    const [lastEvent, setLastEvent] = useState({});
    const [currentEvent, setCurrentEvent] = useState({});
    const [nextEvent, setNextEvent] = useState({});
    const [fetchError, setFetchError] = useState('');

    const [fetchClosestEvents, isClosestEventsLoading, closestEventsError] = useFetching(async () => {
        const lastEventResponse = await GuestService.getLastEvent();
        const currentEventResponse = await GuestService.getCurrentEvent();
        const nextEventResponse = await GuestService.getNextEvent();
        setLastEvent(lastEventResponse.data);
        setCurrentEvent(currentEventResponse.data);
        setNextEvent(nextEventResponse.data);
    });

    useEffect(() => {
        fetchClosestEvents();
    }, []);

    useEffect(() => {
        setFetchError(closestEventsError);
    }, [closestEventsError]);

    return (
        isClosestEventsLoading ? (
            <Loader/>
        ) : (
            <div className="column">
                <div className="welcome_block">
                    <img src="/images/welcome.png" alt="Welcome"/>
                </div>
                {fetchError
                    ? <ErrorBlock errorMessage={fetchError}/>
                    :
                    <div>
                        <EventInfo status='upcoming' eventData={nextEvent}></EventInfo>
                        <EventInfo status='current' eventData={currentEvent}></EventInfo>
                        <EventInfo status='finished' eventData={lastEvent}></EventInfo>
                        <AllEventLink></AllEventLink>
                    </div>}
            </div>
        )
    );
};

export default Home;