import React, {useEffect, useState} from 'react';
import {useFetching} from "../hooks/useFetching";
import GuestService from "../services/GuestService";
import {useParams} from "react-router-dom";
import Loader from "../components/UI/loader/Loader";
import ErrorBlock from "../components/UI/error/ErrorBlock";

const Results = () => {
    const params = useParams();
    const [results, setResults] = useState([]);
    const [race, setRace] = useState({});

    const [fetchResults, isResultsLoading, resultsError] = useFetching(async () => {
        const response = await GuestService.getResultsByRaceId(params.id);
        setResults(response.data.results);
        setRace(response.data.race);
    });

    useEffect(() => {
        fetchResults();
    }, []);

    if (isResultsLoading) {
        return (
            <Loader/>
        )
    }

    if (resultsError) {
        return (
            <ErrorBlock errorMessage={resultsError}/>
        )
    }

    if (!results.length) {
        return (
            <div className={"column"}>
                <div className={"heading__block"}>
                    <h3>Results not found</h3>
                </div>
            </div>
        )
    }

    return (
        <div className="column">
            <div className="heading__table">
                <h2>{race.raceType} {race.event.city} {race.date}</h2>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Rank</th>
                    <th>Bib</th>
                    <th>Sportsman</th>
                    <th>Country</th>
                    <th>Total misses</th>
                    <th>Behind</th>
                    <th>Time</th>
                    <th>Fantazy</th>
                </tr>
                </thead>
                <tbody>
                {results.map((result) => (
                    <tr key={result.id}>
                        <td>{result.rank}</td>
                        <td>{result.bib}</td>
                        <td>{result.athlete.name}</td>
                        <td>{result.athlete.country}</td>
                        <td>{result.totalMisses}</td>
                        <td>{result.behind}</td>
                        <td>{result.resultTime}</td>
                        <td>{result.points}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
};

export default Results;