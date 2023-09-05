import React, {useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import GuestService from "../services/GuestService";
import Loader from "../components/UI/loader/Loader";
import ErrorBlock from "../components/UI/error/ErrorBlock";
import RaceItem from "../components/common/race/RaceItem";
import {useParams} from "react-router-dom";

const EventRaces = () => {
    const params = useParams();
    const [races, setRaces] = useState([]);
    const [error, setError] = useState('');

    const [fetchEventRaces, isEventRacesLoading, eventRacesError] = useFetching(async () => {
        const responseRaces = await GuestService.getEventRacesByEventId(params.id);
        setRaces(responseRaces.data);
    });

    useEffect(() => {
        fetchEventRaces();
        setError(eventRacesError);
    }, []);

    return (
        isEventRacesLoading ? (
            <Loader/>
        ) : (
            <div className="column">
                {eventRacesError ? (
                    <ErrorBlock errorMessage={eventRacesError} />
                ) : (
                    races.length ? (
                        races.map((race) => (
                            <RaceItem race={race} key={race.id}></RaceItem>
                        ))
                    ) : (
                        <div className={"heading__block"}>
                            <h3>Races not found</h3>
                        </div>
                    )
                )}
            </div>
        )
    );
};

export default EventRaces;