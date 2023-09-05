import React, {useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import Loader from "../components/UI/loader/Loader";
import AthleteList from "../components/common/athlete/AthleteList";
import GuestService from "../services/GuestService";

const AthletesNew = () => {
    const [athletes, setAthletes] = useState([]);

    const [fetchAthletes, isLoading, error] = useFetching(async () => {
        const response = await GuestService.getAllAthletesNew();
        setAthletes(response.data);
    });

    useEffect(() => {
        fetchAthletes();
    }, []);

    return (
        isLoading ? (
            <Loader/>
        ) : (
            <AthleteList athletes={athletes}>

            </AthleteList>
        )
    );
};

export default AthletesNew;