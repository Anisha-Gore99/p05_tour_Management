// src/pages/AgencyHome.js
import React from 'react';

function AgencyHome() {
  const agency = JSON.parse(localStorage.getItem("loggedUser"));

  return (
    <div style={{ padding: '20px' }}>
      <h2>Welcome, {agency?.uname || 'Tour Agency'}!</h2>
      <p>This is your Tour Agency dashboard.</p>

      <div style={{ marginTop: '20px' }}>
        <h3>Quick Actions:</h3>
        <ul>
          <li><a href="/agency/tours">View & Manage Tour Packages</a></li>
          <li><a href="/agency/bookings">View Bookings</a></li>
          <li><a href="/agency/schedule">Create Tour Schedules</a></li>
          <li><a href="/agency/profile">Update Agency Profile</a></li>
        </ul>
      </div>
    </div>
  );
}

export default AgencyHome;
