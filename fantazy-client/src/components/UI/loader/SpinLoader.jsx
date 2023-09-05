import React from 'react';
import classes from './SpinLoader.module.css';

const SpinLoader = () => {
    return (
        <div className={classes.loader_container}>
            <div className={classes.loader}></div>
        </div>
    );
};

export default SpinLoader;