import React from 'react';
import {Link} from "react-router-dom";
import classes from './AllEventLink.module.css';

const AllEventLink = () => {
    return (
        <div className={classes.all__events__block}>
            <Link to="/events">
                <h3>All Events</h3>
            </Link>
        </div>
    );
};

export default AllEventLink;