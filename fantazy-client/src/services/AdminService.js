import api from "./api";

const ADMIN_URL = '/api/v1/admin';

export default class AdminService {
    static async uploadRace(formData) {
        try {
            const response = await api.post(ADMIN_URL + '/upload', formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                },
            });
            return response;
        } catch (error) {
            console.log('Error fetching data');
            throw error;
        }
    }

    static async getAllUsers() {
        try {
            const response = await api.get(ADMIN_URL + '/users');
            return response;
        } catch (error) {
            console.log('Error fetching data');
            throw error;
        }
    }

    static async createEvent(event) {
        try {
            const response = await api.post(ADMIN_URL + '/add-event', event);
            return response;
        } catch (error) {
            console.log('Error fetching data');
            throw error;
        }
    }
}