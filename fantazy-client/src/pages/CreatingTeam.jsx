import React, {useContext, useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import GuestService from "../services/GuestService";
import {useNavigate, useParams} from "react-router-dom";
import UserService from "../services/UserService";
import {AuthContext} from "../context";
import Loader from "../components/UI/loader/Loader";
import ErrorBlock from "../components/UI/error/ErrorBlock";
import AthleteSelecting from "../components/common/athlete/AthleteSelecting";
import DefaultButton from "../components/UI/button/DefaultButton";

const CreatingTeam = () => {
    const {currentUser, setCurrentUser, isLoading} = useContext(AuthContext);
    const params = useParams();
    const navigate = useNavigate();

    const [athletes, setAthletes] = useState([]);
    const [selectedAthletes, setSelectedAthletes] = useState([]);
    const [teamName, setTeamName] = useState('');

    const [fetchAthletes, isAthletesLoading, athletesError] = useFetching(async () => {
        const response = await GuestService.getAllAthletes();
        setAthletes(response.data);
    });

    useEffect(() => {
        fetchAthletes();
        setSelectedAthletes([]);
    }, []);

    const handleTeamNameChange = (e) => {
        setTeamName(e.target.value);
    };

    const handleOnAthlete = (athlete) => {
        if(selectedAthletes.includes(athlete)) {
            setAthletes([...athletes, athlete]);
            const updatedSelectedAthletes = selectedAthletes.filter(item => item !== athlete);
            setSelectedAthletes(updatedSelectedAthletes);
            return;
        }

        if(athletes.includes(athlete) && selectedAthletes.length < 3) {
            setSelectedAthletes([...selectedAthletes, athlete]);
            const updatedAthletes = athletes.filter(item => item !== athlete);
            setAthletes(updatedAthletes);
            return;
        }

        if(selectedAthletes >= 2) {
            alert('Only 3 athletes');
        }
    };

    const handleTeamCreate = async () => {
        if (selectedAthletes.length !== 3 || teamName.trim() === '') {
            alert('Please select three athletes and enter a team name.');
        } else {
            const createTeamRequest = {
                teamName,
                eventId: params.id,
                selectedAthleteIds: selectedAthletes.map(o => o.id),
            };
            console.log(createTeamRequest);

            const createTeamResponse = await UserService.createTeam(createTeamRequest);
            if (createTeamResponse?.ok) {
                navigate('/team/' + createTeamResponse.data.team.id);
            }
        }
    };

    if (isAthletesLoading || isLoading) {
        return (
            <Loader/>
        )
    }

    if (!currentUser) {
        return (
            <ErrorBlock errorMessage={'Login to create your team'}></ErrorBlock>
        )
    }

    return (
        <div className={"column"}>
            <h2>Create Your Biathlon Team</h2>
            <div>
                <label htmlFor="teamName">Team Name:</label>
                <input
                    type="text"
                    id="teamName"
                    value={teamName}
                    onChange={handleTeamNameChange}
                />
            </div>
            <div className={"row"} style={{alignItems: 'normal', justifyContent: 'space-between'}}>
                <div className={"only__column"}>
                    <h3>Select 3 Athletes:</h3>
                    <AthleteSelecting athleteList={athletes} onAthleteHandle={handleOnAthlete}></AthleteSelecting>
                </div>
                <div className={"only__column"}>
                    <h3>Selected athletes:</h3>
                    <AthleteSelecting athleteList={selectedAthletes}
                                      onAthleteHandle={handleOnAthlete}></AthleteSelecting>
                    <DefaultButton onClick={handleTeamCreate}>Create Team</DefaultButton>
                </div>
            </div>
        </div>
    );
};

export default CreatingTeam;
