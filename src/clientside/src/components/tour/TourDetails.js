import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const API_BASE = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

export default function TourDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const loggedIn = useSelector(
    (state) =>
      Boolean(state?.auth?.isAuthenticated) || Boolean(state?.logged?.loggedIn)
  );

  const [schedules, setSchedules] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");

  const [showAuthDialog, setShowAuthDialog] = useState(false);
  const [pendingScheduleId, setPendingScheduleId] = useState(null);

  useEffect(() => {
    let active = true;
    setLoading(true);
    setErr("");

    fetch(`${API_BASE}/api/schedules/by-package/${id}`)
      .then((r) => {
        if (!r.ok) throw new Error(`HTTP ${r.status}`);
        return r.json();
      })
      .then((res) => {
        if (!active) return;
        setSchedules(Array.isArray(res) ? res : []);
      })
      .catch((e) => {
        if (!active) return;
        console.error(e);
        setErr("Failed to load schedules.");
      })
      .finally(() => active && setLoading(false));

    return () => {
      active = false;
    };
  }, [id]);

  const handleBookClick = (scheduleId) => {
    if (!scheduleId) return;
    if (loggedIn) {
      navigate(`/booking/${scheduleId}`);
    } else {
      setPendingScheduleId(scheduleId);
      setShowAuthDialog(true);
    }
  };

  const handleAuthChoice = (choice) => {
    setShowAuthDialog(false);
    if (!choice || choice === "cancel") {
      setPendingScheduleId(null);
      return;
    }
    const redirectTo = pendingScheduleId ? `/booking/${pendingScheduleId}` : "/";
    if (choice === "login") {
      navigate("/login", { state: { from: redirectTo }, replace: true });
    } else if (choice === "register") {
      navigate("/register", { state: { from: redirectTo }, replace: true });
    }
  };

  return (
    <div className="tour-details-container" style={{ padding: 16 }}>
      <h3>Available Schedules</h3>

      {loading && <p>Loading…</p>}
      {!loading && err && <p style={{ color: "crimson" }}>{err}</p>}
      {!loading && !err && schedules.length === 0 && <p>No schedules available.</p>}

      {!loading &&
        !err &&
        schedules.map((s, i) => {
          const scheduleId =
            s.scheduleId ?? s.schedule_id ?? s.id ?? s.sid ?? s.scheduleID;
          const startDate = s.start_date ?? s.startDate ?? s.fromDate;
          const endDate = s.end_date ?? s.endDate ?? s.toDate;
          const seats =
            s.availableBookings ??
            s.available ??
            s.available_seats ??
            s.seatsAvailable ??
            s.seats_available ??
            null;
          const soldOut = seats === 0;
          const cost = s.cost ?? s.price ?? s.amount;

          return (
            <div
              key={scheduleId ?? i}
              style={{
                border: "1px solid #eee",
                borderRadius: 10,
                padding: 12,
                marginBottom: 12,
              }}
            >
              <div>From: {startDate ?? "—"}</div>
              <div>To: {endDate ?? "—"}</div>
              <div>Seats Available: {seats ?? "—"}</div>
              {cost !== undefined && <div>Cost: ₹{cost}</div>}
              <button
                disabled={soldOut || !scheduleId}
                onClick={() => handleBookClick(scheduleId)}
                style={{ marginTop: 8 }}
              >
                {soldOut ? "Sold Out" : "Book Now"}
              </button>
            </div>
          );
        })}

      {showAuthDialog && (
        <div
          role="dialog"
          aria-modal="true"
          aria-labelledby="authDialogTitle"
          onClick={() => handleAuthChoice("cancel")}
          style={{
            position: "fixed",
            inset: 0,
            background: "rgba(0,0,0,.35)",
            display: "grid",
            placeItems: "center",
            zIndex: 9999,
          }}
        >
          <div
            onClick={(e) => e.stopPropagation()}
            style={{
              background: "#fff",
              padding: 20,
              borderRadius: 12,
              width: 360,
              boxShadow: "0 10px 30px rgba(0,0,0,.2)",
            }}
          >
            <h4 id="authDialogTitle" style={{ marginTop: 0 }}>
              Login or Register to continue
            </h4>
            <p style={{ marginTop: 0 }}>
              You need an account to book this tour.
            </p>
            <div style={{ display: "flex", gap: 8, justifyContent: "flex-end" }}>
              <button onClick={() => handleAuthChoice("login")}>Login</button>
              <button onClick={() => handleAuthChoice("register")}>
                Register
              </button>
              <button onClick={() => handleAuthChoice("cancel")}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
