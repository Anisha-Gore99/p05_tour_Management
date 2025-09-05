import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import '../../css/agencyDashboard.css';

const AgencyDashboard = () => {
  const tourAgencyUser = useSelector(state => state.touragency.tourAgencyUser);

  // Redirect if not logged in as agency
  if (!tourAgencyUser) {
    return <Navigate to="/" />;
  }

  return (
    <div className="dashboard-container">
      <h2>Welcome, {tourAgencyUser.uname}</h2>

      <div className="dashboard-sections">
        <section className="dashboard-section">
          <div className="section-header">
            <h3>My Tour Packages</h3>
            <button className="action-btn">Manage</button>
          </div>
          {/* TODO: Add component to list packages */}
        </section>

        <section className="dashboard-section">
          <div className="section-header">
            <h3>Create New Package</h3>
            <button className="action-btn">Add Package</button>
          </div>
          {/* TODO: Add form or modal to create package */}
        </section>

        <section className="dashboard-section">
          <div className="section-header">
            <h3>View Bookings for My Tours</h3>
            <button className="action-btn">View Bookings</button>
          </div>
          {/* TODO: Add table or list of bookings */}
        </section>

        <section className="dashboard-section">
          <div className="section-header">
            <h3>Agency Profile Management</h3>
            <button className="action-btn">Edit Profile</button>
          </div>
          {/* TODO: Add editable profile form */}
        </section>
      </div>
    </div>
  );
};

export default AgencyDashboard;
