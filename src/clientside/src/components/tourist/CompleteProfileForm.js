import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useLocation } from 'react-router-dom';
import { registerTourist } from '../../Reduxfeatures/tourist/touristSlice';
import { setTouristProfile } from '../../Reduxfeatures/user/userSlice';
import '../../css/completeProfileForm.css';

const PLACEHOLDER_PASSWORD = 'Temp@123';

const CompleteProfileForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const user = useSelector((state) => state.user.user);

  const [fname, setFname] = useState(user?.fname || '');
  const [lname, setLname] = useState(user?.lname || '');
  const [dob, setDob] = useState('');
  const [address, setAddress] = useState('');
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const redirectMessage = location.state?.message;

  if (!user) {
    return (
      <div className="form-container">
        <p>User information is missing. Please log in again.</p>
        <button onClick={() => navigate('/login')} className="submit-btn">
          Go to Login
        </button>
      </div>
    );
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!dob || !address || !fname || !lname) {
      setError('Please fill in all required fields.');
      return;
    }

    const payload = {
      fname,
      lname,
      dob,
      address,
      uid: {
        uid: user.uid,
        uname: user.uname,
        email: user.email,
        phoneNo: user.phoneNo,
        password: PLACEHOLDER_PASSWORD,
        role: {
          rid: user?.rid?.rid || 2
        }
      }
    };

    try {
      setSubmitting(true);
      const response = await dispatch(registerTourist(payload)).unwrap();
      const touristId = response.tid; // Use tid from backend
      console.log('Received touristId:', touristId); // Debug log

      const completeProfile = {
        ...payload,
        tid: touristId // Use tid for Redux and localStorage
      };

      dispatch(setTouristProfile(completeProfile));
      localStorage.setItem('touristId', touristId);

      navigate('/touristdashboard', {
        state: {
          profileUpdated: true,
          message: 'Your profile has been saved successfully! You can now book tour packages.'
        }
      });
    } catch (err) {
      console.error('Profile completion failed:', err);
      setError('Something went wrong. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="form-container">
      <h2>Complete Your Profile</h2>

      {redirectMessage && (
        <div className="info-message">{redirectMessage}</div>
      )}

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Username</label>
          <input type="text" value={user.uname || ''} disabled />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input type="email" value={user.email || ''} disabled />
        </div>

        <div className="form-group">
          <label>Mobile Number</label>
          <input type="text" value={user.phoneNo || ''} disabled />
        </div>

        <div className="form-group">
          <label>First Name</label>
          <input
            type="text"
            value={fname}
            onChange={(e) => setFname(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>Last Name</label>
          <input
            type="text"
            value={lname}
            onChange={(e) => setLname(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>Date of Birth</label>
          <input
            type="date"
            value={dob}
            onChange={(e) => setDob(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>Address</label>
          <textarea
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            required
          />
        </div>

        <button type="submit" className="submit-btn" disabled={submitting}>
          {submitting ? 'Saving...' : 'Save Profile'}
        </button>
      </form>
    </div>
  );
};

export default CompleteProfileForm;
