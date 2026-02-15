import axios from 'axios';

// 1. Create a centralized instance
const apiClient = axios.create({
    baseURL: 'http://localhost:8080', // Adjust if your backend port changes
    headers: {
        'Content-Type': 'application/json'
    }
});

// 2. The Request Interceptor (The "Stamper")
apiClient.interceptors.request.use(
    (config) => {
        // Check if token exists in the "Bank Vault"
        const token = localStorage.getItem('jwt_token');
        if (token) {
            // Attach it to the header
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// 3. The Response Interceptor (Optional: Handle Expired Tokens)
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 403) {
            // If server says "Forbidden", maybe token expired?
            // Optional: localStorage.removeItem('jwt_token');
            // Optional: window.location.reload(); 
        }
        return Promise.reject(error);
    }
);

export default apiClient;