import axios from 'axios';
import authHeader from './auth-header';

const API_URL = "http://localhost:8000";

class AuthService {
    login(user) {
        return axios
            .post(API_URL + '/authenticate', {
                email: user.email,
                password: user.password
            })
            .then(response => {
                if (response.data.success) {
                    if (response.data.data.token) {
                        localStorage.setItem('user', JSON.stringify(response.data.data));
                    }
                }

                return response.data;
            });
    }

    logout(data) {
        return axios
            .post(API_URL + '/invalidate', data, { headers: authHeader() })
            .then(response => {
                if (response.data.success) {
                    localStorage.removeItem('user');
                }

                return response.data;
            });

    }

    register(user) {
        return axios.post(API_URL + '/register', {
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthService();