import React, { useEffect, useState } from "react";
import { fetchTourPackages } from "../../api/TourApi";
import { useNavigate } from "react-router-dom";

export default function ViewTours() {
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");
  const navigate = useNavigate();

  async function loadPackages() {
    setLoading(true);
    setErr("");
    try {
      const data = await fetchTourPackages();
      setPackages(Array.isArray(data) ? data : []);
    } catch (e) {
      // Compose a helpful message for the banner
      const status = e?.status ?? e?.response?.status ?? "?";
      const data = e?.data ?? e?.response?.data;
      const msg =
        (typeof data === "string" && data) ||
        data?.message ||
        e.message ||
        "Request failed";
      const reqId = data?.requestId || data?.traceId || data?.request_id || null;

      setErr(
        `Failed to load packages (HTTP ${status})` +
          (msg ? `: ${msg}` : "") +
          (reqId ? ` (requestId: ${reqId})` : "")
      );
      console.error("Error fetching tour packages:", e);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    let cancelled = false;
    (async () => {
      if (!cancelled) await loadPackages();
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  if (loading) return <p>Loading tour packages…</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Available Tour Packages</h2>

      {err && (
        <div style={{
          color:"#b00020",
          background:"#ffe6e9",
          border:"1px solid #ffc2ca",
          padding:"10px 12px",
          borderRadius:8,
          whiteSpace:"pre-wrap",
          margin:"10px 0"
        }}>
          {err}
          <div style={{ marginTop: 8 }}>
            <button onClick={loadPackages}>Retry</button>
          </div>
        </div>
      )}

      {!err && packages.length === 0 && <p>No tour packages found.</p>}

      {!err && packages.length > 0 && (
        <div className="package-list" style={{ display:"grid", gap:"12px" }}>
          {packages.map((pkg) => {
            const id = pkg.packageId ?? pkg.id;
            const name = pkg.packageName ?? pkg.pname ?? `Package #${id}`;
            const dest = pkg.destination ?? pkg.place ?? "—";
            const desc = pkg.description ?? pkg.details ?? "—";
            return (
              <div key={id} className="package-card" style={{
                border:"1px solid #ddd",
                borderRadius:10,
                padding:"12px 14px"
              }}>
                <h3 style={{ marginTop:0 }}>{name}</h3>
                <p><strong>Destination:</strong> {dest}</p>
                <p><strong>Description:</strong> {desc}</p>
                <button onClick={() => navigate(`/tours/${id}`)}>
                  View Details
                </button>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
