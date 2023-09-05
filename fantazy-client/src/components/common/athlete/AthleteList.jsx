import React from 'react';
import AthleteItem from "./AthleteItem";
import './Athlete.css';

const AthleteList = ({athletes}) => {
        if(!athletes.length) {
            return (
                <div className="athlete__list">
                    <h3>Athletes not found</h3>
                </div>
            )
        }

        return (
        <div className="athlete__list">
            <h3>Athletes</h3>
            {athletes.map((athlete) => (
                <AthleteItem athlete={athlete} key={athlete.id}></AthleteItem>
            ))}
        </div>
    );
};

export default AthleteList;