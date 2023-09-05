import React, {useEffect, useState} from 'react';
import '../styles/TeamOverview.css';
import {useParams} from "react-router-dom";
import {useFetching} from "../hooks/useFetching";
import GuestService from "../services/GuestService";
import Loader from "../components/UI/loader/Loader";
import TeamInfo from "../components/common/team/TeamInfo";
import TeamAthletes from "../components/common/team/TeamAthletes";
import ErrorBlock from "../components/UI/error/ErrorBlock";

const TeamOverview = () => {
    const params = useParams();

    const [team, setTeam] = useState(null);
    const [athleteAndPoints, setAthleteAndPoints] = useState([]);

    const [fetchTeam, isTeamLoading, teamError] = useFetching(async () => {
        const response = await GuestService.getTeamById(params.id);
        setTeam(response.data.overviewTeamDto.team);
        setAthleteAndPoints(response.data.overviewTeamDto.athleteAndPointsList);
    });

    useEffect(() => {
        fetchTeam();
    }, []);

    if (isTeamLoading) {
        return (
            <Loader/>
        )
    }

    if (teamError) {
        return (
            <div className={"column"}>
            <ErrorBlock errorMessage={teamError}/>
            </div>
        )
    }

    return (
        <div className={"column"}>
            <div className={"team-overview"}>
                <TeamInfo teamName={team.name} username={team.user.username}/>
                <TeamAthletes athletesAndPointsList={athleteAndPoints}/>
                <p className={"total-points"}>{athleteAndPoints.reduce((accumulator, currentObject) => {
                    return accumulator + currentObject.points;
                }, 0)
                }</p>
            </div>
        </div>
    );
};

export default TeamOverview;