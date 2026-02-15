import { useState } from 'react';
import apiClient from '../api/axiosConfig';
import { useAuth } from '../context/AuthContext';
import './AuthOverlay.css';

const AuthOverlay = () => {
    const { login, logout, isAuthenticated } = useAuth();
    const [isOpen, setIsOpen] = useState(false);
    const [isLoginView, setIsLoginView] = useState(true); // Toggle Login vs Signup
    
    // Form State
    const [formData, setFormData] = useState({ username: '', email: '', password: '' });
    const [error, setError] = useState('');

    const toggleOverlay = () => setIsOpen(!isOpen);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        
        try {
            if (isLoginView) {
                // LOGIN LOGIC
                // Note: backend returns a raw String for login, not JSON
                const response = await apiClient.post('/auth/login', {
                    email: formData.email,
                    password: formData.password
                });
                login(response.data); // Store the token
                setIsOpen(false); // Close modal
            } else {
                // SIGNUP LOGIC
                await apiClient.post('/auth/sign-up', formData);
                alert("Account created! Please log in.");
                setIsLoginView(true); // Switch to login screen
            }
        } catch (err) {
            setError(err.response?.data || "Authentication failed");
        }
    };

    // If logged in, just show a small Logout button
    if (isAuthenticated) {
        return (
            <div className="auth-widget logged-in">
                <button onClick={logout} className="logout-btn">Log Out</button>
            </div>
        );
    }

    return (
        <div className="auth-widget">
            {!isOpen ? (
                <button onClick={toggleOverlay} className="login-trigger-btn">
                    Log In / Sign Up
                </button>
            ) : (
                <div className="auth-card">
                    <button className="close-btn" onClick={toggleOverlay}>×</button>
                    <h3>{isLoginView ? "Welcome Back" : "Create Account"}</h3>
                    
                    {error && <p className="error-msg">{error}</p>}

                    <form onSubmit={handleSubmit}>
                        {!isLoginView && (
                            <input 
                                type="text" placeholder="Username" required
                                value={formData.username}
                                onChange={e => setFormData({...formData, username: e.target.value})}
                            />
                        )}
                        <input 
                            type="email" placeholder="Email" required
                            value={formData.email}
                            onChange={e => setFormData({...formData, email: e.target.value})}
                        />
                        <input 
                            type="password" placeholder="Password" required
                            value={formData.password}
                            onChange={e => setFormData({...formData, password: e.target.value})}
                        />
                        <button type="submit" className="submit-btn">
                            {isLoginView ? "Log In" : "Sign Up"}
                        </button>
                    </form>

                    <p className="toggle-text" onClick={() => setIsLoginView(!isLoginView)}>
                        {isLoginView ? "Need an account? Sign Up" : "Have an account? Log In"}
                    </p>
                </div>
            )}
        </div>
    );
};

export default AuthOverlay;