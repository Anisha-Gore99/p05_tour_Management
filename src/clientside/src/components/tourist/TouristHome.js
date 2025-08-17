// src/components/tourist/TouristHome.js
import { useEffect, useState } from "react";
import { Link, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";

const API_BASE =
  (process.env.REACT_APP_API_BASE_URL &&
    process.env.REACT_APP_API_BASE_URL.replace(/\/+$/, "")) ||
  "http://localhost:8080";

export default function TouristHome() {
  const [tourist, setTourist] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Adjust this selector to match your slice structure if needed
  const loggedIn = useSelector((s) => s?.logged?.loggedIn);

  useEffect(() => {
    let abort = new AbortController();
    setError(null);
    setLoading(true);

    // If not logged in, reset view
    if (!loggedIn) {
      setTourist(null);
      setLoading(false);
      return () => abort.abort();
    }

    // Read the logged-in user from localStorage
    let loggedUser = null;
    try {
      loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
    } catch {
      setError("Corrupted login session. Please log in again.");
      setLoading(false);
      return () => abort.abort();
    }

    const uid = loggedUser?.uid ?? loggedUser?.id;
    if (!uid) {
      setError("User ID missing. Please log in again.");
      setLoading(false);
      return () => abort.abort();
    }

    (async () => {
      try {
        const resp = await fetch(
          `${API_BASE}/tourist/getTouristByUserId?uid=${encodeURIComponent(uid)}`,
          {
            method: "GET",
            headers: { Accept: "application/json" },
            mode: "cors",
            signal: abort.signal,
          }
        );

        // 404 -> no profile yet (not an error)
        if (resp.status === 404) {
          localStorage.removeItem("loggedTourist");
          setTourist(null);
          setLoading(false);
          return;
        }

        if (!resp.ok) {
          // Try to surface server message and requestId to help debugging
          const ct = resp.headers.get("content-type") || "";
          let msg = `Failed to load tourist data (HTTP ${resp.status})`;
          let reqId = null;

          try {
            if (ct.includes("application/json")) {
              const j = await resp.json();
              if (j?.message) msg += `: ${j.message}`;
              reqId = j?.requestId || j?.traceId || j?.request_id || null;
            } else {
              const t = await resp.text();
              if (t) msg += `: ${t}`;
            }
          } catch {
            /* ignore parse errors */
          }

          if (reqId) msg += ` (requestId: ${reqId})`;
          throw new Error(msg);
        }

        const obj = await resp.json();
        localStorage.setItem("loggedTourist", JSON.stringify(obj));
        setTourist(obj);
        setLoading(false);
      } catch (e) {
        if (abort.signal.aborted) return;
        console.error(e);
        setError(
          e?.message || "Failed to load tourist data."
        );
        setLoading(false);
      }
    })();

    return () => abort.abort();
  }, [loggedIn]);

  const displayName =
    tourist
      ? `${tourist.fname ?? ""} ${tourist.lname ?? ""}`.trim() || "Tourist"
      : "Guest";

  return (
    <div>
      <header><h2>Tourist Dashboard</h2></header>

      {error && (
        <p style={{ color: "crimson", whiteSpace: "pre-wrap" }}>{error}</p>
      )}

      <nav style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
        <Link to="/viewalltours">View Tours</Link>
        <Link to="/bookinghistory">Booking History</Link>
        <Link to="/logout">Logout</Link>
      </nav>

      <section className="hero">
        <h1>
          Welcome,&nbsp;{loading ? "Loading…" : displayName}
        </h1>
        <Outlet />
      </section>
    </div>
  );
}
