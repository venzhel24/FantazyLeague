import axios from "axios";
import {BACKEND_URL} from "./Path";
import api from "./api";

const GUEST_URL = BACKEND_URL + '/api/v1/guest';

export default class GuestService {
    static async getAllAthletes() {
        try {
            const response = await axios.get(GUEST_URL + '/athletes');
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }


    static async getAllEvents() {
        try {
            const response = await axios.get(GUEST_URL + '/events');
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    static async getLastEvent() {
        try {
            const response = await axios.get(GUEST_URL + '/last-event');
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    static async getCurrentEvent() {
        try {
            const response = await axios.get(GUEST_URL + '/current-event');
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    static async getNextEvent() {
        try {
            const response = await axios.get(GUEST_URL + '/next-event');
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    static async getEventRacesByEventId(eventId) {
        try {
            const response = await axios.get(GUEST_URL + '/event-races/' + eventId);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    static async getResultsByRaceId(raceId) {
        try {
            const response = await axios.get(GUEST_URL + '/results/' + raceId);
            return response;
        } catch(error) {
            console.log('Error fetching data', error);
            throw error;
        }
    }

    static async getLeaderboardByEventId(eventId) {
        try {
            const response = await axios.get(GUEST_URL + '/leaderboard/' + eventId);
            return response;
        } catch(error) {
            console.log('Error fetching data', error);
            throw error;
        }
    }

    static async getTeamById(teamId) {
        try {
            const response = await axios.get(GUEST_URL + '/team-overview/' + teamId);
            return response;
        } catch(error) {
            console.log('Error fetching data', error);
            throw error;
        }
    }
}