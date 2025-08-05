// src/client/src/components/UserList.js

import React, { useState, useEffect } from 'react';

function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/users'); // Assumes backend runs on 8080
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setUsers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []); // Empty dependency array means this effect runs once on mount

  if (loading) {
    return (
      <div className="d-flex justify-content-center mt-5">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
        <p className="ms-2">Loading users...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="alert alert-danger mt-4" role="alert">
        <strong>Error:</strong> {error}
      </div>
    );
  }

  if (users.length === 0) {
    return (
      <div className="alert alert-info mt-4" role="alert">
        No users found.
      </div>
    );
  }

  return (
    <div className="card shadow-sm mt-4">
      <div className="card-header bg-primary text-white">
        <h2 className="card-title mb-0">User Details</h2>
      </div>
      <div className="card-body">
        <div className="table-responsive">
          <table className="table table-striped table-hover">
            <thead>
              <tr>
                <th>UID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Phone No</th>
                <th>RID</th>
              </tr>
            </thead>
            <tbody>
              {users.map(user => (
                <tr key={user.uid}>
                  <td>{user.uid}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>{user.phoneNo || 'N/A'}</td>
                  <td>{user.rid || 'N/A'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default UserList;