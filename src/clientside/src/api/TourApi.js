// clientside/src/api/TourApi.js
import axios from "axios";

// ---- Base (gateway only) ----
const API_BASE =
  (process.env.REACT_APP_API_BASE_URL &&
    process.env.REACT_APP_API_BASE_URL.replace(/\/+$/, "")) ||
  "http://localhost:8080";

const http = axios.create({
  baseURL: API_BASE,
  withCredentials: true,                 // ok if you later add cookies; harmless otherwise
  timeout: 10000,
  headers: { Accept: "application/json" }
});

// ---- Error shaping: always bubble up status + requestId for easy debugging ----
http.interceptors.response.use(
  (res) => res,
  (err) => {
    const status = err?.response?.status;
    const data = err?.response?.data;
    const headers = err?.response?.headers || {};
    const reqId =
      (data && (data.requestId || data.traceId || data.request_id)) ||
      headers["x-request-id"] ||
      null;

    const msg =
      (typeof data === "string" && data) ||
      data?.message ||
      err.message ||
      "Request failed";

    const e = new Error(
      `HTTP ${status ?? "?"}${msg ? `: ${msg}` : ""}${
        reqId ? ` (requestId: ${reqId})` : ""
      }`
    );
    e.status = status;
    e.data = data;
    e.requestId = reqId;
    return Promise.reject(e);
  }
);

// ---------------- TOURS (tour-service via gateway) ----------------

export async function fetchTourPackages() {
  const res = await http.get(`/api/packages`, {
    // In case the service returns something non-JSON by mistake, we still fail loud via interceptor
    validateStatus: (s) => s >= 200 && s < 300
  });
  return res.data;
}

export async function fetchTourPackageById(packageId) {
  const res = await http.get(`/api/packages/${packageId}`, {
    validateStatus: (s) => s >= 200 && s < 500
  });
  if (res.status === 404) return null;
  if (res.status >= 400) {
    const d = res.data;
    const reqId =
      d?.requestId || d?.traceId || d?.request_id || res.headers?.["x-request-id"];
    const msg =
      (typeof d === "string" && d) || d?.message || `Server error (${res.status})`;
    const e = new Error(
      `HTTP ${res.status}: ${msg}${reqId ? ` (requestId: ${reqId})` : ""}`
    );
    e.status = res.status;
    e.data = d;
    e.requestId = reqId || null;
    throw e;
  }
  return res.data;
}

export async function fetchTourPackagesByAgency(agencyUserId) {
  const all = await fetchTourPackages();
  return (all || []).filter((p) => {
    const aid = p.tourAgencyId ?? p.agencyId ?? p.uid ?? p.userId;
    return String(aid) === String(agencyUserId);
  });
}

export async function createTourPackage(body) {
  const res = await http.post(`/api/packages`, body, {
    headers: { "Content-Type": "application/json" }
  });
  return res.data;
}

export async function updateTourPackage(packageId, payload) {
  const res = await http.put(`/api/packages/${packageId}`, payload, {
    headers: { "Content-Type": "application/json" }
  });
  return res.data;
}

// ---------------- SCHEDULES (tour-service via gateway) ----------------
// Tries common variants if your controller mapping differs.
export async function fetchScheduleByPackageId(packageId) {
  const candidates = [
    `/api/schedules/by-package/${packageId}`,
    `/api/schedules/package/${packageId}`,
    `/api/schedules?packageId=${encodeURIComponent(packageId)}`,
    `/api/schedules/byPackage/${packageId}`
  ];

  for (const path of candidates) {
    try {
      const res = await http.get(path, { validateStatus: (s) => s >= 200 && s < 500 });
      if (res.status === 404) continue; // try next variant
      if (res.status >= 400) {
        // Let interceptor shape a helpful error on next throw
        const e = new Error(`HTTP ${res.status}`);
        e.status = res.status;
        e.data = res.data;
        throw e;
      }
      const data = res.data;
      return Array.isArray(data) ? data : data?.schedules ?? [];
    } catch (e) {
      // try next candidate; if last one fails, let it bubble up
      if (path === candidates[candidates.length - 1]) throw e;
    }
  }
  return [];
}

// ---------------- AGENCY (user-service via gateway) ----------------
// Your controller base is `/touragency` (no /api). Try both, just in case.
export async function fetchAgencyByUserId(uid) {
  const paths = [
    `/touragency/getByUserId`,
    `/api/touragency/getByUserId`
  ];

  for (const p of paths) {
    try {
      const res = await http.get(p, { params: { uid }, validateStatus: (s) => s >= 200 && s < 500 });
      if (res.status === 404) {
        const e = new Error("AGENCY_NOT_FOUND");
        e.code = "AGENCY_NOT_FOUND";
        throw e;
      }
      if (res.status >= 400) {
        const d = res.data;
        const reqId =
          d?.requestId || d?.traceId || d?.request_id || res.headers?.["x-request-id"];
        const msg =
          (typeof d === "string" && d) || d?.message || `Server error (${res.status})`;
        const e = new Error(
          `HTTP ${res.status}: ${msg}${reqId ? ` (requestId: ${reqId})` : ""}`
        );
        e.status = res.status;
        e.data = d;
        e.requestId = reqId || null;
        throw e;
      }
      return res.data;
    } catch (e) {
      if (p === paths[paths.length - 1]) throw e; // bubble on last
    }
  }
}
