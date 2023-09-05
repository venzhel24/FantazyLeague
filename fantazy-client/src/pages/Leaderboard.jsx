import React, {useContext, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {useFetching} from "../hooks/useFetching";
import GuestService from "../services/GuestService";
import Loader from "../components/UI/loader/Loader";
import ErrorBlock from "../components/UI/error/ErrorBlock";
import DefaultButton from "../components/UI/button/DefaultButton";
import {AuthContext} from "../context";

const Leaderboard = () => {
    const {currentUser, setCurrentUser, isLoading} = useContext(AuthContext);

    const params = useParams();
    const [leaderboard, setLeaderboard] = useState([]);
    const navigate = useNavigate();

    const [fetchLeaderboard, isLeaderboardLoading, leaderboardError] = useFetching(async () => {
        const response = await GuestService.getLeaderboardByEventId(params.id);
        response.data.teamAndPointsDtoList !== null && setLeaderboard(response.data.teamAndPointsDtoList);
    });

    useEffect(() => {
        fetchLeaderboard();
    }, []);

    const handleCreateTeam = () => {
        navigate('/create-team/' + params.id);
    };

    const handleGetMyTeam = () => {
        navigate('/team/' + params.id);
    };

    if (isLeaderboardLoading || isLoading) {
        return (
            <Loader/>
        )
    }

    if (leaderboardError) {
        return (
            <ErrorBlock errorMessage={leaderboardError}/>
        )
    }

    if (!leaderboard.length) {
        return (
            <div className={"column"}>
                <div className={"heading__block"}>
                    <h3>Teams not found</h3>
                </div>
                {currentUser && currentUser.role.includes('ROLE_USER') &&
                    <div style={{position: 'absolute', left: 0}}>
                        <DefaultButton onClick={handleCreateTeam}>Create team</DefaultButton>
                    </div>
                }
            </div>
        )
    }

    function handleOverviewTeam(teamId) {
        navigate('/team/' + teamId);
    }

    return (
        <div className="column">
            <div className={"row"}>
                {currentUser && currentUser.role.includes('ROLE_USER') &&
                    <div style={{position: 'absolute', left: 0}}>
                        {leaderboard.some(obj => obj.team.userId === currentUser.id)
                            ? <DefaultButton onClick={handleCreateTeam}>Create team</DefaultButton>
                            : <DefaultButton onClick={handleGetMyTeam}>My team</DefaultButton>
                        }
                    </div>
                }
                <div className="heading__table">
                    <h2>Leaderboard</h2>
                </div>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Rank</th>
                    <th>Username</th>
                    <th>Team name</th>
                    <th>Points</th>
                    <th>Overview</th>
                </tr>
                </thead>
                <tbody>
                {leaderboard.map((teamAndPoints, index) => (
                    <tr key={teamAndPoints.team.id}>
                        <td>{index + 1}</td>
                        <td>{teamAndPoints.team.user.username}</td>
                        <td>{teamAndPoints.team.name}</td>
                        <td>{teamAndPoints.totalPoints}</td>
                        <td className={"td__link"} onClick={() => handleOverviewTeam(teamAndPoints.team.id)}>click</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Leaderboard;