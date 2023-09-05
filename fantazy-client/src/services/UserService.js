import {BACKEND_URL} from "./Path";
import axios from "axios";
import api from "./api";

const USER_URL = '/api/v1/user';

export default class UserService {
    static async createTeam(createTeamRequest) {
        try {
            const response = await api.post(USER_URL + '/create-team', {
                eventId: createTeamRequest.eventId,
                teamName: createTeamRequest.teamName,
                selectedAthleteIds: createTeamRequest.selectedAthleteIds
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }
}