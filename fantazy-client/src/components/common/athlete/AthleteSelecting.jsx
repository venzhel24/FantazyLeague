import React from 'react';
import classes from './AthleteSelecting.module.css';

const AthleteSelecting = ({athleteList, onAthleteHandle}) => {

    return (
        <ul className={classes.ul__block}>
            {athleteList.map((athlete) => (
                <li
                    key={athlete.id}
                    className={''}
                    onClick={() => onAthleteHandle(athlete)}
                >
                    {athlete.name}, {athlete.country}
                </li>
            ))}
        </ul>
    );
};

export default AthleteSelecting;