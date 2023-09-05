import React from 'react';

const TeamInfo = ({teamName, username}) => {
    return (
        <div className="team-info">
            <h2 className="team-name">{teamName}</h2>
            <p className="team-name-sub">Team Overview</p>
            <p className="user-info">User: <span className="user-name">{username}</span></p>
        </div>
    );
};

export default TeamInfo;