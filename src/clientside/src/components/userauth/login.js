// src/components/userauth/login.js
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { login } from "../../Reduxfeatures/slice";
import { useNavigate, useLocation } from "react-router-dom";

// Use only the gateway (no CORS to 8081)
const API_BASE =
  (process.env.REACT_APP_API_BASE_URL &&
    process.env.REACT_APP_API_BASE_URL.replace(/\/+$/, "")) ||
  "http://localhost:8080";

// ---- Helpers ----
async function postJson(url, body) {
  const resp = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json", Accept: "application/json" },
    body: JSON.stringify(body),
    mode: "cors",
  });

  const contentType = resp.headers.get("content-type") || "";
  let payload = null;
  if (contentType.includes("application/json")) {
    try { payload = await resp.json(); } catch {}
  } else {
    try { payload = await resp.text(); } catch {}
  }

  if (!resp.ok) {
    // compose an actionable message
    let msg = `HTTP ${resp.status}`;
    if (payload) {
      if (typeof payload === "string") msg += `: ${payload}`;
      else if (payload.message) msg += `: ${payload.message}`;
      else msg += `: ${JSON.stringify(payload)}`;
    }
    // add requestId if present (useful to grep server logs)
    const reqId = payload && typeof payload === "object" ? (payload.requestId || payload.traceId || payload.request_id) : null;
    if (reqId) msg += ` (requestId: ${reqId})`;

    const err = new Error(msg);
    err.status = resp.status;
    err.requestId = reqId || null;
    throw err;
  }

  // success
  if (payload && typeof payload !== "string") return payload;
  try { return JSON.parse(payload); } catch { throw new Error("Unexpected non-JSON response"); }
}

function normalizeRole(user) {
  const raw =
    user?.rid?.rname ??
    user?.role ??
    user?.roleName ??
    user?.role?.name ??
    "";
  const s = String(raw).toLowerCase().replace(/[^a-z]/g, "");
  if (["agency", "touragency", "travelagency", "agencyowner"].includes(s)) return "agency";
  if (["admin", "administrator"].includes(s)) return "admin";
  if (["tourist", "user", "customer"].includes(s)) return "tourist";
  return s || "tourist";
}

const touristEndpoints = (uid) => [
  `${API_BASE}/tourist/getTouristByUserId?uid=${encodeURIComponent(uid)}`, // via gateway only
];

export default function Login() {
  const [uname, setUname] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState(null);
  const [loading, setLoading] = useState(false);

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();

  const rawFrom = location.state?.from;
  const from =
    typeof rawFrom === "string" && rawFrom !== "/" && rawFrom !== "/login"
      ? rawFrom
      : null;

  const doLogin = async (e) => {
    e.preventDefault();
    setErr(null);
    setLoading(true);

    try {
      const user = await postJson(`${API_BASE}/api/user/chkLogin`, {
        uname: uname.trim(),
        password,
      });

      // ensure no password is stored even if server returns it
      if (user && "password" in user) delete user.password;
      localStorage.setItem("loggedUser", JSON.stringify(user));

      const role = normalizeRole(user);

      // Try to fetch tourist profile (optional). If it fails, continue.
      if (role === "tourist") {
        const uid = user?.uid ?? user?.id;
        if (uid != null) {
          for (const url of touristEndpoints(uid)) {
            try {
              const tResp = await fetch(url, { mode: "cors" });
              if (tResp.status === 404) {
                localStorage.removeItem("loggedTourist");
                break;
              }
              if (tResp.ok) {
                const tourist = await tResp.json();
                localStorage.setItem("loggedTourist", JSON.stringify(tourist));
                break;
              }
            } catch {
              // ignore tourist fetch errors
            }
          }
        }
      }

      // Redux auth state (your slice)
      dispatch(login());

      // Navigate
      if (from) {
        navigate(from, { replace: true });
      } else if (role === "admin") {
        navigate("/adminhome", { replace: true });
      } else if (role === "agency") {
        navigate("/agencyhome", { replace: true });
      } else {
        navigate("/touristhome", { replace: true });
      }
    } catch (e2) {
      console.error(e2);
      setErr(e2.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: 20, maxWidth: 420, margin: "40px auto" }}>
      <h2 style={{ marginBottom: 16 }}>Login</h2>

      {err && (
        <div
          style={{
            color: "#b00020",
            background: "#ffe6e9",
            border: "1px solid #ffc2ca",
            padding: "8px 10px",
            borderRadius: 6,
            marginBottom: 12,
            fontSize: 14,
            whiteSpace: "pre-wrap",
          }}
        >
          {err}
          {err.includes("HTTP 500") && (
            <>
              {"\n"}This is a server error from the gateway/backend. Use the
              requestId (if shown) to find the exact stack trace in your
              user-service logs.
            </>
          )}
        </div>
      )}

      <form onSubmit={doLogin} style={{ display: "grid", gap: 12 }}>
        <label style={{ display: "grid", gap: 6 }}>
          <span>Username</span>
          <input
            type="text"
            value={uname}
            onChange={(e) => setUname(e.target.value)}
            required
            autoComplete="username"
            style={{
              padding: "10px 12px",
              borderRadius: 8,
              border: "1px solid #cfcfcf",
            }}
          />
        </label>

        <label style={{ display: "grid", gap: 6 }}>
          <span>Password</span>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            autoComplete="current-password"
            style={{
              padding: "10px 12px",
              borderRadius: 8,
              border: "1px solid #cfcfcf",
            }}
          />
        </label>

        <button
          type="submit"
          disabled={loading}
          style={{
            padding: "10px 14px",
            borderRadius: 10,
            border: "none",
            background: loading ? "#9aa0a6" : "#1a73e8",
            color: "white",
            cursor: loading ? "not-allowed" : "pointer",
            fontWeight: 600,
          }}
        >
          {loading ? "Signing in…" : "Login"}
        </button>
      </form>
    </div>
  );
}
