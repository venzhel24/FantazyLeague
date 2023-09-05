import React from 'react';
import DefaultButton from "../../UI/button/DefaultButton";

const AthleteItem = ({athlete}) => {
    return (
        <div className="athlete">
            <div className="athlete__content">
                <strong>{athlete.id}. {athlete.name} : {athlete.country}</strong>
                <p></p>
            </div>
            <div className="buttons">
                <DefaultButton style={{fontSize: 12, padding: 3, width: 80}}>
                    More
                </DefaultButton>
            </div>
        </div>
    );
};

export default AthleteItem;