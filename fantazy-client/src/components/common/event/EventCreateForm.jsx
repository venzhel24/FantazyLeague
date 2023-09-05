import {useFetching} from "../../../hooks/useFetching";
import {useState} from "react";
import AdminService from "../../../services/AdminService";

const EventCreateForm = () => {
    const [cityName, setCityName] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [addingMessage, setAddingMessage] = useState('');
    const [responseStatus, setResponseStatus] = useState('');

    const [sendEvent, isEventSending, eventSendError] = useFetching(async (event) => {
        const response = AdminService.createEvent(event);
        setAddingMessage(response.data);
        setResponseStatus(response.status);
    });

    const handleSubmit = (e) => {
        e.preventDefault();

        const event = {
            cityName,
            startDate,
            endDate,
        };

        const response = sendEvent(event);
        if(responseStatus === 'ok') {
            setCityName('');
            setStartDate('');
            setEndDate('');
        }
    };

    return (
        <form onSubmit={handleSubmit} className={"form-group"}>
            <div>
                <label htmlFor="city">City:</label>
                <input
                    type="text"
                    id="city"
                    value={cityName}
                    onChange={(e) => setCityName(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="startDate">Start Date:</label>
                <input
                    type="date"
                    id="startDate"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="endDate">End Date:</label>
                <input
                    type="date"
                    id="endDate"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    required
                />
            </div>
            <div>
                <button type="submit">Add Event</button>
            </div>
            <p>{addingMessage}</p>
        </form>
    );
};

export default EventCreateForm;