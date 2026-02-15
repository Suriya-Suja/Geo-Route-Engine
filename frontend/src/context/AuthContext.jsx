import { createContext, useState, useEffect, useContext } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('jwt_token'));
    const [isAuthenticated, setIsAuthenticated] = useState(!!token);

    // Sync state with LocalStorage
    useEffect(() => {
        if (token) {
            localStorage.setItem('jwt_token', token);
            setIsAuthenticated(true);
        } else {
            localStorage.removeItem('jwt_token');
            setIsAuthenticated(false);
        }
    }, [token]);

    const login = (newToken) => {
        setToken(newToken);
    };

    const logout = () => {
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ token, isAuthenticated, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);