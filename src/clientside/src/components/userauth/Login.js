import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../../api/auth';
import {
  loginStart,
  loginSuccess,
  loginFailure,
  fetchTouristProfile
} from '../../Reduxfeatures/user/userSlice';
import {
  setTourAgencyUser,
  tourAgencyLoginStart,
  tourAgencyLoginFailure
} from '../../Reduxfeatures/touragency/tourAgencySlice';
import '../../css/auth.css';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error } = useSelector(state => state.user);
  const [uname, setUname] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(loginStart());

    try {
      const userData = await loginUser({ uname, password });
      dispatch(loginSuccess(userData));

     const rawRole = userData.role?.rname || '';
     const roleName = rawRole.replace(/[^a-zA-Z]/g, '').toLowerCase(); // removes underscores, spaces etc.

      console.log(`Logged in as: ${roleName}`);

      localStorage.setItem('userRole', roleName);

      if (roleName === 'tourist') {
        dispatch(fetchTouristProfile(userData.uid));
        navigate('/touristdashboard');
      } else if (roleName === 'tour_agency' || roleName === 'touragency') {
        dispatch(tourAgencyLoginStart());
        dispatch(setTourAgencyUser(userData));
        navigate('/agencydashboard');
      } else if (roleName === 'admin') {
        navigate('/admindashboard');
      } else {
        navigate('/');
      }

    } catch (err) {
      console.error('Login error:', err);
      dispatch(loginFailure('Invalid username or password.'));
      dispatch(tourAgencyLoginFailure('Login failed for tour agency.'));
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Login to TourNest</h2>

        <div className="form-group">
          <label htmlFor="uname">Username</label>
          <input
            type="text"
            id="uname"
            value={uname}
            onChange={(e) => setUname(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>

        {error && <p className="error-message">{error}</p>}

        <p className="auth-link-text">
          Don't have an account? <a href="/register">Register here</a>
        </p>
      </form>
    </div>
  );
};

export default Login;
