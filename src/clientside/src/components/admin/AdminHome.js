import React from "react";
import { Link } from "react-router-dom";

const AdminHome = () => {
  return (
    <div style={{ padding: "2rem" }}>
      <h1>Welcome Admin</h1>
      <p>Select a management function:</p>

      <ul>
        <li>
          <Link to="/admin/account-management">Account Management</Link>
        </li>
        <li>
          <Link to="/admin/transactions">View Transactions</Link>
        </li>
        <li>
          <Link to="/admin/manage-categories">Manage Travel Categories</Link>
        </li>
        <li>
          <Link to="/admin/reports">Generate Reports</Link>
        </li>
      </ul>
    </div>
  );
};

export default AdminHome;
