import React, { useEffect, useState, useMemo } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";

// ==== All API calls go through the GATEWAY (8080) ====
const API = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

const MODES_ENDPOINT = `${API}/api/modes`;
const BOOKINGS_ENDPOINT = `${API}/api/bookings`;
const SCHEDULE_ENDPOINT = (id) => `${API}/api/schedules/${id}`;
const PAYMENT_CHECKOUT_ENDPOINT = `${API}/api/payments/checkout`; // via gateway

// ---- Helpers to normalize incoming shapes ----
function normalizeAvailable(src) {
  if (!src) return null;
  if (typeof src === "number") return Number.isFinite(src) ? src : null;
  const v =
    src.availableBookings ??
    src.available_bookings ??
    src.available ??
    src.seatsAvailable ??
    src.seats_available ??
    null;
  const n = Number(v);
  return Number.isFinite(n) ? n : null;
}
function normalizePrice(src) {
  if (!src) return null;
  if (typeof src === "number") return Number.isFinite(src) ? src : null;
  const v = src.cost ?? src.price ?? src.amount ?? null;
  const n = Number(v);
  return Number.isFinite(n) ? n : null;
}
function formatINR(amount) {
  if (amount == null) return "—";
  try {
    return amount.toLocaleString("en-IN", { style: "currency", currency: "INR", maximumFractionDigits: 0 });
  } catch {
    return `₹${amount}`;
  }
}

// PaymentService checkout (via gateway)
async function createCheckout(bookingId, amount) {
  const res = await fetch(PAYMENT_CHECKOUT_ENDPOINT, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ bookingId, amount: Number(amount || 0), currency: "INR" }),
  });
  if (!res.ok) throw new Error(`Payment init failed: ${await res.text()}`);
  const data = await res.json();
  return data.redirectUrl || data.paymentRedirectUrl || data.url || null;
}

// --- Robust localStorage readers ---
function readLoggedTouristId() {
  try {
    const t = JSON.parse(localStorage.getItem("loggedTourist"));
    const raw =
      t?.touristId ?? t?.tid ?? t?.id ?? t?.touristID ?? t?.tId ?? null;
    const n = Number(raw);
    return Number.isFinite(n) ? n : null;
  } catch {
    return null;
  }
}
function readLoggedUserUid() {
  try {
    const u = JSON.parse(localStorage.getItem("loggedUser"));
    const raw = u?.uid ?? u?.id ?? null;
    const n = Number(raw);
    return Number.isFinite(n) ? n : null;
  } catch {
    return null;
  }
}

export default function BookingPage() {
  const { scheduleId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  // Tourist identity (start from localStorage, then auto-hydrate)
  const [touristId, setTouristId] = useState(() => readLoggedTouristId());

  // Payment modes
  const [modes, setModes] = useState([]);
  const [modesLoading, setModesLoading] = useState(true);
  const [modesErr, setModesErr] = useState("");
  const [modeId, setModeId] = useState("");

  // Availability & pricing for this schedule
  const initialAvail = normalizeAvailable(location.state);
  const initialPrice = normalizePrice(location.state);
  const [available, setAvailable] = useState(initialAvail);
  const [pricePerPerson, setPricePerPerson] = useState(initialPrice);
  const [availErr, setAvailErr] = useState("");

  // Form state
  const [numTourists, setNumTourists] = useState(1);
  const [touristDetails, setTouristDetails] = useState([
    { fname: "", lname: "", age: "", gender: "", idProof: "" },
  ]);

  // Derived: total cost
  const totalCost = useMemo(() => {
    if (pricePerPerson == null) return null;
    return Number(pricePerPerson) * Number(numTourists || 1);
  }, [pricePerPerson, numTourists]);

  // Auto-hydrate tourist profile silently (no redirect)
  useEffect(() => {
    if (touristId != null) return; // already have it
    const uid = readLoggedUserUid();
    if (!uid) return; // user not logged -> keep page usable; submit will show friendly alert
    (async () => {
      try {
        const r = await fetch(`${API}/tourist/getTouristByUserId?uid=${uid}`);
        if (r.ok) {
          const t = await r.json();
          localStorage.setItem("loggedTourist", JSON.stringify(t));
          const id = readLoggedTouristId();
          if (id != null) setTouristId(id);
        }
      } catch {
        // ignore; we'll just ask to complete profile at submit time if still missing
      }
    })();
  }, [touristId]);

  // ---- Load payment modes ----
  useEffect(() => {
    let active = true;
    setModesLoading(true);
    setModesErr("");

    fetch(MODES_ENDPOINT)
      .then((r) => { if (!r.ok) throw new Error(`HTTP ${r.status}`); return r.json(); })
      .then((res) => {
        if (!active) return;
        const list = (Array.isArray(res) ? res : [])
          .map((m) => ({ id: m.modeId ?? m.id ?? m.mid, name: m.modeName ?? m.name ?? m.title }))
          .filter((m) => m.id != null && m.name);
        setModes(list);
        if (!modeId && list.length) setModeId(String(list[0].id));
      })
      .catch((e) => {
        if (!active) return;
        setModesErr(
          e.message?.includes("Failed to fetch")
            ? `Cannot reach ${MODES_ENDPOINT}. Is the gateway & booking-service UP?`
            : e.message || "Unable to load payment modes."
        );
      })
      .finally(() => active && setModesLoading(false));

    return () => { active = false; };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // ---- Load schedule info (availability + price) if not passed via state ----
  useEffect(() => {
    if (available != null && pricePerPerson != null) return;
    let active = true;
    const url = SCHEDULE_ENDPOINT(scheduleId);

    fetch(url)
      .then((r) => { if (!r.ok) throw new Error(`HTTP ${r.status}`); return r.json(); })
      .then((s) => {
        if (!active) return;
        const a = available != null ? available : normalizeAvailable(s);
        const p = pricePerPerson != null ? pricePerPerson : normalizePrice(s);
        if (a == null) throw new Error("availableBookings not found");
        setAvailable(a);
        if (p != null) setPricePerPerson(p);

        // Clamp form counts based on availability
        setNumTourists((prev) => Math.min(Math.max(prev, 1), a));
        setTouristDetails((prev) => {
          const next = [...prev];
          while (next.length < Math.max(1, Math.min(a, prev.length))) {
            next.push({ fname: "", lname: "", age: "", gender: "", idProof: "" });
          }
          if (next.length > a) next.length = a;
          if (next.length < 1) next.length = 1;
          return next;
        });
      })
      .catch((e) => {
        if (!active) return;
        setAvailErr(
          e.message?.includes("Failed to fetch")
            ? `Cannot reach ${url}. Is the gateway & tour-service UP?`
            : "Failed to load schedule details."
        );
      });

    return () => { active = false; };
  }, [scheduleId, available, pricePerPerson]);

  // ---- Handlers ----
  const handleNumTouristsChange = (e) => {
    const raw = parseInt(e.target.value, 10) || 1;
    const max = available ?? Infinity;
    const value = Math.max(1, Math.min(raw, max));
    setNumTourists(value);

    const updated = [...touristDetails];
    while (updated.length < value) updated.push({ fname: "", lname: "", age: "", gender: "", idProof: "" });
    while (updated.length > value) updated.pop();
    setTouristDetails(updated);
  };

  const handleTouristDetailChange = (index, field, value) => {
    const updated = [...touristDetails];
    updated[index][field] = value;
    setTouristDetails(updated);
  };

  const redirectTo = (url) => window.location.assign(url);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (available === 0) { alert("This schedule is sold out."); return; }
    if (available != null && numTourists > available) { alert(`Only ${available} seat(s) available.`); return; }
    if (!modeId) { alert("Please select a payment mode."); return; }

    // Ensure we have a touristId, but DO NOT redirect to login
    let tid = touristId;
    if (tid == null) {
      const uid = readLoggedUserUid();
      if (uid) {
        try {
          const r = await fetch(`${API}/tourist/getTouristByUserId?uid=${uid}`);
          if (r.ok) {
            const t = await r.json();
            localStorage.setItem("loggedTourist", JSON.stringify(t));
            const hydrated = readLoggedTouristId();
            if (hydrated != null) {
              setTouristId(hydrated);
              tid = hydrated;
            }
          }
        } catch { /* ignore */ }
      }
    }
    if (tid == null) {
      alert("We couldn't find your tourist profile. Please complete your profile before booking.");
      return;
    }

    const payload = {
      touristId: Number(tid),
      scheduleId: Number(scheduleId),
      modeId: Number(modeId),
      touristDetails: touristDetails.map(d => ({ ...d, age: Number(d.age) })),
      noOfTourist: numTourists,
      amount: totalCost ?? undefined,
    };

    try {
      const res = await fetch(BOOKINGS_ENDPOINT, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (!res.ok) throw new Error(`HTTP ${res.status} - ${await res.text()}`);

      const data = await res.json();

      // If backend already returned a payment redirect, follow it
      if (data?.paymentRequired && data?.paymentRedirectUrl) {
        redirectTo(data.paymentRedirectUrl);
        return;
      }

      // Otherwise, create checkout if mode is online
      const bookingId = data?.bookingId ?? data?.id;
      const selectedModeName =
        (modes.find(m => String(m.id) === String(modeId))?.name || "").toLowerCase();
      const needsOnline = !/cash|offline|cod/.test(selectedModeName);

      if (needsOnline && bookingId) {
        try {
          const redirectUrl = await createCheckout(bookingId, totalCost ?? 0);
          if (redirectUrl) {
            redirectTo(redirectUrl);
            return;
          }
        } catch (e2) {
          console.error(e2);
          alert("Payment could not be initialized. You can try again from your bookings.");
        }
      }

      // Local success flow
      alert("Booking successful!");
      if (typeof available === "number") setAvailable((prev) => Math.max(0, (prev ?? 0) - numTourists));
      setNumTourists(1);
      setTouristDetails([{ fname: "", lname: "", age: "", gender: "", idProof: "" }]);
      // navigate("/"); // optional

    } catch (err) {
      console.error("POST error:", err);
      const isFetch = String(err?.message || "").includes("Failed to fetch");
      alert(isFetch ? `Cannot reach ${BOOKINGS_ENDPOINT}. Check the gateway/service.` : err.message || "Booking failed.");
    }
  };

  // ---- Derived UI state ----
  const maxAttr = available ?? undefined;
  const disableForm = available === 0; // only disable if sold out
  const priceKnown = pricePerPerson != null;

  return (
    <div style={{ padding: 20 }}>
      <h2>Book Tour</h2>

      {availErr && <div style={{ color: "crimson", marginBottom: 8 }}>{availErr}</div>}
      {available != null && <div style={{ marginBottom: 4 }}><strong>Seats Available:</strong> {available}</div>}
      {priceKnown && <div style={{ marginBottom: 12 }}><strong>Price per Person:</strong> {formatINR(pricePerPerson)}</div>}
      {available === 0 && <div style={{ color: "crimson", marginBottom: 12 }}>This schedule is sold out.</div>}

      <form onSubmit={handleSubmit}>
        {/* Payment mode radios */}
        <div style={{ marginBottom: 16 }}>
          <label style={{ display: "block", fontWeight: 600, marginBottom: 6 }}>Select Payment Mode:</label>

          {modesLoading && <div>Loading modes…</div>}
          {modesErr && <div style={{ color: "crimson" }}>{modesErr}</div>}
          {!modesLoading && !modesErr && modes.length === 0 && <div>No modes available.</div>}

          {!modesLoading && !modesErr && modes.length > 0 && (
            <div style={{ display: "grid", gap: 6 }}>
              {modes.map((m) => (
                <label key={m.id} style={{ display: "flex", alignItems: "center", gap: 8 }}>
                  <input
                    type="radio"
                    name="mode"
                    value={m.id}
                    checked={String(modeId) === String(m.id)}
                    onChange={(e) => setModeId(e.target.value)}
                    required
                    disabled={disableForm}
                  />
                  <span>{m.name}</span>
                </label>
              ))}
            </div>
          )}
        </div>

        {/* Number of tourists (capped) */}
        <div style={{ marginBottom: 10 }}>
          <label>No. of Tourists:&nbsp;</label>
          <input
            type="number"
            min="1"
            max={maxAttr}
            value={numTourists}
            onChange={handleNumTouristsChange}
            disabled={disableForm}
          />
          {available != null && <small style={{ marginLeft: 8 }}>(max {available})</small>}
        </div>

        {/* Total Cost */}
        <div style={{ margin: "12px 0", fontWeight: 600 }}>
          Total Cost:&nbsp;{priceKnown ? formatINR(totalCost) : "—"}
        </div>

        <h3>Tourist Details</h3>
        {touristDetails.map((tourist, index) => (
          <div
            key={index}
            style={{
              border: "1px solid #ccc",
              padding: 10,
              marginBottom: 10,
              borderRadius: 8,
              opacity: disableForm ? 0.6 : 1,
            }}
          >
            <p style={{ marginTop: 0 }}>Tourist #{index + 1}</p>
            <input
              type="text"
              placeholder="First Name"
              value={tourist.fname}
              onChange={(e) => handleTouristDetailChange(index, "fname", e.target.value)}
              required
              disabled={disableForm}
            />
            <input
              type="text"
              placeholder="Last Name"
              value={tourist.lname}
              onChange={(e) => handleTouristDetailChange(index, "lname", e.target.value)}
              required
              disabled={disableForm}
            />
            <input
              type="number"
              placeholder="Age"
              value={tourist.age}
              onChange={(e) => handleTouristDetailChange(index, "age", e.target.value)}
              required
              disabled={disableForm}
            />
            <select
              value={tourist.gender}
              onChange={(e) => handleTouristDetailChange(index, "gender", e.target.value)}
              required
              disabled={disableForm}
            >
              <option value="">Select Gender</option>
              <option>Male</option>
              <option>Female</option>
              <option>Other</option>
            </select>
            <input
              type="text"
              placeholder="ID Proof"
              value={tourist.idProof}
              onChange={(e) => handleTouristDetailChange(index, "idProof", e.target.value)}
              required
              disabled={disableForm}
            />
          </div>
        ))}

        <button type="submit" disabled={disableForm}>
          Confirm Booking
        </button>
      </form>
    </div>
  );
}

