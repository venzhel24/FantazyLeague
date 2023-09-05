import React from 'react';

const TeamAthletes = ({athletesAndPointsList}) => {
    return (
        <div className={"athletes_block"}>
            {athletesAndPointsList.map((athleteAndPoints) => (
                <div className={"athlete_block"} key={athleteAndPoints.athlete.id}>
                    <img src="/images/biathlete_unknown.png" alt="biathlete"/>
                    <h4>{athleteAndPoints.athlete.name}</h4>
                    <p className={"points"}>{athleteAndPoints.points}</p>
                </div>
            ))}
        </div>
    );
};

export default TeamAthletes;