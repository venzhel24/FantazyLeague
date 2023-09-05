import React from 'react';
import {getPagesArray} from "../../../utils/Pages";
import classes from './Pagination.module.css';

const Pagination = ({totalPages, page, changePage}) => {
    let pagesArray = getPagesArray(totalPages);
    return (
        <div className={classes.page__wrapper}>
            {pagesArray.map(p =>
                <span
                    onClick={() => changePage(p)}
                    key={p}
                    className={page === p ? classes.page__current : classes.page}
                >
                    {p}
                </span>
            )}
        </div>
    );
};

export default Pagination;