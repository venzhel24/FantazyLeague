import React from 'react';
import classes from './Error.module.css';

const ErrorBlock = ({ errorMessage }) => {
    return (
        <div className={classes.error__block}>
            <p>{errorMessage}</p>
        </div>
    );
}

export default ErrorBlock;
