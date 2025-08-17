// src/components/touragency/TourAgencyHome.jsx
import React, { useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { fetchAgencyByUserId } from "../../api/TourApi";

export default function TourAgencyHome() {
  const agencyUser = useMemo(() => {
    try { return JSON.parse(localStorage.getItem("loggedUser")); }
    catch { return null; }
  }, []);

  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [apiErr, setApiErr] = useState("");

  const name = agencyUser?.uname || "Tour Agency";
  const email = agencyUser?.email || "—";
  const phone = agencyUser?.phoneNo || "—";

  useEffect(() => {
    let active = true;
    (async () => {
      try {
        if (!agencyUser?.uid) throw new Error("No logged-in agency user.");
        const agency = await fetchAgencyByUserId(agencyUser.uid);

        // Normalize and cache id for later usage
        const tourAgencyId =
          Number(agency?.tourAgencyId ?? agency?.taid ?? agency?.id ?? NaN);
        const normalized = { ...agency, tourAgencyId };
        localStorage.setItem("loggedAgency", JSON.stringify(normalized));
        if (active) setApiErr("");
      } catch (e) {
        console.error(e);
        if (!active) return;
        if (e.code === "AGENCY_NOT_FOUND") {
          setApiErr("No agency profile found for this account.");
          localStorage.removeItem("loggedAgency");
        } else {
          setApiErr("Could not load your agency profile.");
        }
      } finally {
        if (active) setLoading(false);
      }
    })();
    return () => { active = false; };
  }, [agencyUser?.uid]);

  return (
    <div style={{ padding: 24, maxWidth: 1100, margin: "0 auto" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: 12 }}>
        <div>
          <h2 style={{ margin: 0 }}>Welcome, {name}!</h2>
          <div style={{ color: "#666", marginTop: 6 }}>
            <span style={{ marginRight: 16 }}>Email: {email}</span>
            <span>Phone: {phone}</span>
          </div>

          {apiErr && (
            <div style={{ color: "crimson", marginTop: 8 }}>
              {apiErr}{" "}
              {apiErr.includes("No agency profile") && (
                <>
                  Please{" "}
                  <button
                    onClick={() => navigate("/agency/register")}
                    style={{ marginLeft: 6, padding: "4px 8px" }}
                  >
                    register your agency
                  </button>{" "}
                  to continue.
                </>
              )}
            </div>
          )}
        </div>
        <button onClick={() => navigate("/agency/tour/new")} style={{ padding: "10px 14px" }}>
          + Create New Package
        </button>
      </div>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
          gap: 16,
          marginTop: 20,
        }}
      >
        <Card
          title="View & Manage Packages"
          desc="See all your packages, edit details, and manage pricing & duration."
          action={<LinkButton to="/agency/tours" label="Open Packages" />}
        />
        <Card
          title="Create Package"
          desc="Add a new tour package with destination, price, and duration."
          action={<button onClick={() => navigate("/agency/tour/new")}>+ New Package</button>}
        />
        <Card
          title="Create Schedules"
          desc="Plan dates and seats for your packages."
          action={<LinkButton to="/agency/schedule" label="Manage Schedules" />}
        />
        <Card
          title="Update Profile"
          desc="Keep your agency details up to date."
          action={<LinkButton to="/agency/profile" label="Edit Profile" />}
        />
      </div>

      <div style={{ marginTop: 28 }}>
        <h4 style={{ margin: "0 0 8px" }}>Tips</h4>
        <ul style={{ color: "#666", lineHeight: 1.8, margin: 0 }}>
          <li>Use <strong>Create Package</strong> to add a new tour.</li>
          <li>From <strong>View &amp; Manage Packages</strong>, click <em>Edit</em> to modify existing tours.</li>
          <li>Schedules can be tied to packages once your schedules API is ready.</li>
        </ul>
      </div>

      {loading && <div style={{ marginTop: 12 }}>Syncing your agency profile…</div>}
    </div>
  );
}

function Card({ title, desc, action }) {
  return (
    <div
      style={{
        border: "1px solid #e5e7eb",
        borderRadius: 12,
        padding: 16,
        boxShadow: "0 1px 3px rgba(0,0,0,0.06)",
        background: "#fff",
      }}
    >
      <h3 style={{ margin: "0 0 6px" }}>{title}</h3>
      <p style={{ margin: "0 0 12px", color: "#666" }}>{desc}</p>
      {action}
    </div>
  );
}

function LinkButton({ to, label }) {
  return (
    <Link
      to={to}
      style={{
        display: "inline-block",
        padding: "8px 12px",
        border: "1px solid #d1d5db",
        borderRadius: 8,
        textDecoration: "none",
      }}
    >
      {label}
    </Link>
  );
}
