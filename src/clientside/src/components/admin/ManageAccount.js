import React, { useEffect, useState } from "react";
import axios from "axios";

const ManageAccount = () => {
  const [users, setUsers] = useState([]);

  const fetchUsers = async () => {
    try {
      const res = await axios.get("http://localhost:8083/api/admin/users");
      setUsers(res.data);
    } catch (err) {
      console.error("Error fetching users:", err);
    }
  };

  const deleteUser = async (uid) => {
  try {
    await axios.delete(`http://localhost:8082/api/admin/users/${uid}`);
    alert("User deleted successfully");
    fetchUsers(); // refresh the user list
  } catch (err) {
    if (err.response && err.response.data) {
      // Show the actual backend message
      alert("Delete failed: " + err.response.data);
    } else {
      alert("Delete failed: Unknown error");
      console.error("Error deleting user:", err);
    }
  }
};


  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Account Management</h2>
      {users.length === 0 ? (
        <p>No users found.</p>
      ) : (
        <table border="1" cellPadding="8" cellSpacing="0">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.uid}>
                <td>{u.uid}</td>
                <td>{u.uname}</td>
                <td>{u.email}</td>
                <td>
                  <button onClick={() => deleteUser(u.uid)} style={{ color: "red" }}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ManageAccount;
