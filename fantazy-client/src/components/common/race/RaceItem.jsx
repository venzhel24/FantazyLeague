import React from 'react';
import DefaultButton from "../../UI/button/DefaultButton";
import classes from './RaceItem.module.css';
import {useNavigate} from "react-router-dom";


const RaceItem = ({race}) => {
    const navigate = useNavigate();
    const handleResults = () => {
        navigate('/results/' + race.id);
    };
    return (
        <div className={classes.race__block}>
            <div className={classes.race__content}>
                <strong>{race.id}. {race.raceType}  {race.distance}</strong>
                <p>date: {race.date}</p>
            </div>
            <div className={classes.btns__block}>
                <DefaultButton onClick={handleResults} style={{margin: 5, fontSize: 14, padding: 10}}>
                    Results
                </DefaultButton>
            </div>
        </div>
    );
};

export default RaceItem;