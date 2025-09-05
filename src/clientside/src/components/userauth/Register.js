import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../../api/auth';
import { getAllRoles } from '../../api/roles'; 
import '../../css/auth.css';

const Register = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        uname: '',
        email: '',
        password: '',
        phoneNo: '',
        rid: '', 
    });
    const [roles, setRoles] = useState([]); 
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const fetchedRoles = await getAllRoles();

                // Filter out roles with name "Admin"
                const nonAdminRoles = fetchedRoles.filter(role => role.rname.toLowerCase() !== 'admin');

                setRoles(nonAdminRoles);

                // Set a default role if available
                if (nonAdminRoles.length > 0) {
                    setFormData(prevData => ({ ...prevData, rid: nonAdminRoles[0].rid }));
                }
            } catch (error) {
                console.error("Failed to fetch roles:", error);
                setMessage('Failed to load roles. Please try refreshing.');
            }
        };
        fetchRoles();
    }, []);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');

        // Prepare the data to be sent, including the selected role ID
        const userData = {
            uname: formData.uname,
            email: formData.email,
            password: formData.password,
            phoneNo: formData.phoneNo,
            role: { rid: parseInt(formData.rid) }, 
        };

        try {
            await registerUser(userData);
            setMessage('Registration successful! You can now log in.');
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (err) {
            const errorMessage = err.message || 'Registration failed. Please try again.';
            setMessage(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Register</h2>
                <div className="form-group">
                    <label htmlFor="uname">Username</label>
                    <input type="text" id="uname" name="uname" value={formData.uname} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="phoneNo">Phone Number</label>
                    <input type="tel" id="phoneNo" name="phoneNo" value={formData.phoneNo} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="rid">Select Role</label>
                    <select id="rid" name="rid" value={formData.rid} onChange={handleChange} required>
                        {roles.length > 0 ? (
                            roles.map(role => (
                                <option key={role.rid} value={role.rid}>
                                    {role.rname}
                                </option>
                            ))
                        ) : (
                            <option value="">Loading roles...</option>
                        )}
                    </select>
                </div>
                <button type="submit" disabled={loading}>
                    {loading ? 'Registering...' : 'Register'}
                </button>
                {message && <p className={`message ${message.includes('successful') ? 'success' : 'error'}`}>{message}</p>}
                <p className="auth-link-text">
                    Already have an account? <a href="/login">Login here</a>
                </p>
            </form>
        </div>
    );
};

export default Register;
