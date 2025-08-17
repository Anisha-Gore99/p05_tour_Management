// src/components/touragency/TourAgencyHome.js
import React from "react";
import { Link, useNavigate } from "react-router-dom";

export default function TourAgencyHome() {
  const agency = JSON.parse(localStorage.getItem("loggedUser"));
  const navigate = useNavigate();

  return (
    <div style={{ padding: 24, maxWidth: 960, margin: "0 auto" }}>
      <h2 style={{ marginBottom: 8 }}>
        Welcome, {agency?.uname || "Tour Agency"}!
      </h2>
      <p style={{ color: "#555", marginBottom: 24 }}>
        Manage your tour packages and schedules from one place.
      </p>

      {/* Quick Actions */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
          gap: 16,
        }}
      >
        <div
          style={{
            border: "1px solid #e5e7eb",
            borderRadius: 12,
            padding: 16,
            boxShadow: "0 1px 3px rgba(0,0,0,0.06)",
          }}
        >
          <h3 style={{ marginTop: 0, marginBottom: 8 }}>View & Manage Packages</h3>
          <p style={{ marginTop: 0, color: "#666" }}>
            See all your packages, edit details, and manage visibility.
          </p>
          <Link to="/agency/tours">
            <button>Open Packages</button>
          </Link>
        </div>

        <div
          style={{
            border: "1px solid #e5e7eb",
            borderRadius: 12,
            padding: 16,
            boxShadow: "0 1px 3px rgba(0,0,0,0.06)",
          }}
        >
          <h3 style={{ marginTop: 0, marginBottom: 8 }}>Create Package</h3>
          <p style={{ marginTop: 0, color: "#666" }}>
            Add a new tour package with price, duration and destination.
          </p>
          <button onClick={() => navigate("/agency/tour/new")}>
            + New Package
          </button>
        </div>

        <div
          style={{
            border: "1px solid #e5e7eb",
            borderRadius: 12,
            padding: 16,
            boxShadow: "0 1px 3px rgba(0,0,0,0.06)",
          }}
        >
          <h3 style={{ marginTop: 0, marginBottom: 8 }}>Create Schedules</h3>
          <p style={{ marginTop: 0, color: "#666" }}>
            Plan dates and seats for your packages. (Hook up when ready)
          </p>
          <Link to="/agency/schedule">
            <button>Manage Schedules</button>
          </Link>
        </div>

        <div
          style={{
            border: "1px solid #e5e7eb",
            borderRadius: 12,
            padding: 16,
            boxShadow: "0 1px 3px rgba(0,0,0,0.06)",
          }}
        >
          <h3 style={{ marginTop: 0, marginBottom: 8 }}>Update Profile</h3>
          <p style={{ marginTop: 0, color: "#666" }}>
            Keep your agency info up to date for customers.
          </p>
          <Link to="/agency/profile">
            <button>Edit Profile</button>
          </Link>
        </div>
      </div>

      {/* Helpful links */}
      <div style={{ marginTop: 24 }}>
        <h4 style={{ marginBottom: 8 }}>Tips</h4>
        <ul style={{ color: "#666", lineHeight: 1.8, marginTop: 0 }}>
          <li>Use <strong>Create Package</strong> to add a new tour.</li>
          <li>From <strong>View & Manage Packages</strong>, click <em>Edit</em> to modify an existing tour.</li>
          <li>Schedules can be tied to a package once your schedules API is ready.</li>
        </ul>
      </div>
    </div>
  );
}

