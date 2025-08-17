// clientside/src/components/TourAgencyReg.js
import React, { useMemo, useReducer, useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080"; // gateway

// ---------- helpers ----------
const digitsOnly = (s) => (s || "").replace(/\D/g, "");
const isTenDigits = (s) => /^\d{10}$/.test(digitsOnly(s));
const isEmail = (e) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(e || "").trim());

// ---------- state ----------
const initialState = {
  user: { uname: "", password: "", email: "", phoneNo: "" },
  tour_agency_name: "",
  phone_no: "",
  agency_email: "",
  address: "",
  license_number: "",
};

function reducer(state, action) {
  switch (action.type) {
    case "updateNested":
      return {
        ...state,
        [action.parent]: {
          ...(state[action.parent] || {}),
          [action.field]: action.value,
        },
      };
    case "update":
      return { ...state, [action.field]: action.value };
    case "reset":
      return initialState;
    default:
      return state;
  }
}

export default function TourAgencyReg({ onSubmit }) {
  const navigate = useNavigate();
  const [info, dispatch] = useReducer(reducer, initialState);
  const [touched, setTouched] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const markTouched = (name) => setTouched((t) => ({ ...t, [name]: true }));

  // ---------- validation ----------
  const errors = useMemo(() => {
    const e = {};
    // Agency
    if (!info.tour_agency_name.trim()) e.tour_agency_name = "Agency name is required.";
    if (!info.phone_no.trim()) e.phone_no = "Agency phone is required.";
    else if (!isTenDigits(info.phone_no)) e.phone_no = "Enter a valid 10-digit agency phone.";

    if (!info.agency_email.trim()) e.agency_email = "Agency email is required.";
    else if (!isEmail(info.agency_email)) e.agency_email = "Enter a valid agency email.";

    if (!info.address.trim()) e.address = "Agency address is required.";
    if (!info.license_number.trim()) e.license_number = "License number is required.";

    // User
    if (!info.user.uname.trim()) e.uname = "Username is required.";
    else if (info.user.uname.trim().length < 3) e.uname = "Username must be at least 3 characters.";

    if (!info.user.password.trim()) e.password = "Password is required.";
    else if (info.user.password.length < 6) e.password = "Password must be at least 6 characters.";

    if (!info.user.email.trim()) e.email = "Account email is required.";
    else if (!isEmail(info.user.email)) e.email = "Enter a valid account email.";

    if (!info.user.phoneNo.trim()) e.phoneNo = "Account phone is required.";
    else if (!isTenDigits(info.user.phoneNo)) e.phoneNo = "Enter a valid 10-digit account phone.";

    return e;
  }, [info]);

  const canSubmit = Object.keys(errors).length === 0;

  // Fetch the AGENCY role and return a JSON snippet you can assign to user.rid
  async function getAgencyRoleRef() {
    const { data } = await axios.get(`${API}/api/roles/getallroles`);
    const roles = Array.isArray(data) ? data : [];
    const agency = roles.find(
      (r) => String(r.rname || "").toLowerCase() === "agency"
    );
    if (!agency) {
      throw new Error(
        "Role 'AGENCY' not found. Seed roles in DB or create via API before registering."
      );
    }
    // Build the nested object to satisfy Jackson for ManyToOne Role
    // We don't know if Role's PK is 'rid' or 'id' — support both:
    if (agency.rid != null) return { rid: agency.rid };
    if (agency.id != null) return { id: agency.id };
    // last resort: try common variants
    const key = Object.keys(agency).find((k) => /id$/i.test(k));
    if (key) return { [key]: agency[key] };
    throw new Error("Could not determine Role id field name for 'AGENCY'.");
  }

  // ---------- submit ----------
  const sendData = async (e) => {
    e.preventDefault();

    setTouched({
      tour_agency_name: true,
      phone_no: true,
      agency_email: true,
      address: true,
      license_number: true,
      uname: true,
      password: true,
      email: true,
      phoneNo: true,
    });

    if (!canSubmit) return;

    try {
      setSubmitting(true);

      // 1) Get the AGENCY role reference (no backend change needed)
      const roleRef = await getAgencyRoleRef();

      // 2) Build payload with user.rid = roleRef
      const payload = {
        tour_agency_name: info.tour_agency_name.trim(),
        phone_no: digitsOnly(info.phone_no),
        agency_email: info.agency_email.trim(),
        address: info.address.trim(),
        license_number: info.license_number.trim(),
        user: {
          uname: info.user.uname.trim(),
          password: info.user.password, // don't trim passwords
          email: info.user.email.trim(),
          phoneNo: digitsOnly(info.user.phoneNo),
          rid: roleRef, // <-- the key piece to avoid rid=NULL on insert
        },
      };

      const res = await axios.post(`${API}/api/touragency/register`, payload, {
        headers: { "Content-Type": "application/json" },
      });

      if (onSubmit) onSubmit(res.data);
      alert("Agency registered successfully!");

      try {
        if (res.data?.user) localStorage.setItem("loggedUser", JSON.stringify(res.data.user));
        if (res.data) localStorage.setItem("tourAgency", JSON.stringify(res.data));
      } catch {}

      dispatch({ type: "reset" });
      setTouched({});
      navigate("/");
    } catch (error) {
      const body = error?.response?.data;
      const msg =
        typeof body === "string"
          ? body
          : body?.message || body?.error || error.message || "Registration failed.";
      alert(msg);
      console.error("Agency registration error:", error.response || error);
    } finally {
      setSubmitting(false);
    }
  };

  const err = (key) => touched[key] && errors[key];
  const errStyle = { color: "crimson", fontSize: 12, marginTop: 4 };

  return (
    <div style={{ maxWidth: 600, margin: "24px auto" }}>
      <h1>Tour Agency Registration</h1>

      <form onSubmit={sendData} onReset={() => dispatch({ type: "reset" })} noValidate>
        {/* Account (User) details */}
        <fieldset style={{ border: "1px solid #ddd", padding: 16, marginBottom: 16 }}>
          <legend>
            Account <span style={{ color: "crimson" }}>*</span>
          </legend>

          <label>
            Username <span style={{ color: "crimson" }}>*</span>
            <input
              type="text"
              name="uname"
              placeholder="Username"
              value={info.user.uname}
              onChange={(e) =>
                dispatch({ type: "updateNested", parent: "user", field: "uname", value: e.target.value })
              }
              onBlur={() => markTouched("uname")}
              aria-invalid={Boolean(err("uname"))}
              autoComplete="username"
              required
            />
          </label>
          {err("uname") && <div style={errStyle}>{errors.uname}</div>}
          <br />

          <label>
            Password <span style={{ color: "crimson" }}>*</span>
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={info.user.password}
              onChange={(e) =>
                dispatch({ type: "updateNested", parent: "user", field: "password", value: e.target.value })
              }
              onBlur={() => markTouched("password")}
              aria-invalid={Boolean(err("password"))}
              autoComplete="new-password"
              required
            />
          </label>
          {err("password") && <div style={errStyle}>{errors.password}</div>}
          <br />

          <label>
            Account Email <span style={{ color: "crimson" }}>*</span>
            <input
              type="email"
              name="email"
              placeholder="Account Email"
              value={info.user.email}
              onChange={(e) =>
                dispatch({ type: "updateNested", parent: "user", field: "email", value: e.target.value })
              }
              onBlur={() => markTouched("email")}
              aria-invalid={Boolean(err("email"))}
              autoComplete="email"
              required
            />
          </label>
          {err("email") && <div style={errStyle}>{errors.email}</div>}
          <br />

          <label>
            Account Phone <span style={{ color: "crimson" }}>*</span>
            <input
              type="tel"
              name="phoneNo"
              placeholder="10-digit account phone"
              value={info.user.phoneNo}
              onChange={(e) =>
                dispatch({ type: "updateNested", parent: "user", field: "phoneNo", value: e.target.value })
              }
              onBlur={() => markTouched("phoneNo")}
              inputMode="numeric"
              maxLength={14}
              aria-invalid={Boolean(err("phoneNo"))}
              autoComplete="tel"
              required
            />
          </label>
          {err("phoneNo") && <div style={errStyle}>{errors.phoneNo}</div>}
        </fieldset>

        {/* Agency details */}
        <fieldset style={{ border: "1px solid #ddd", padding: 16 }}>
          <legend>
            Agency <span style={{ color: "crimson" }}>*</span>
          </legend>

          <label>
            Agency Name <span style={{ color: "crimson" }}>*</span>
            <input
              type="text"
              name="tour_agency_name"
              placeholder="Agency Name"
              value={info.tour_agency_name}
              onChange={(e) => dispatch({ type: "update", field: "tour_agency_name", value: e.target.value })}
              onBlur={() => markTouched("tour_agency_name")}
              aria-invalid={Boolean(err("tour_agency_name"))}
              autoComplete="organization"
              required
            />
          </label>
          {err("tour_agency_name") && <div style={errStyle}>{errors.tour_agency_name}</div>}
          <br />

          <label>
            Agency Phone <span style={{ color: "crimson" }}>*</span>
            <input
              type="tel"
              name="phone_no"
              placeholder="10-digit agency phone"
              value={info.phone_no}
              onChange={(e) => dispatch({ type: "update", field: "phone_no", value: e.target.value })}
              onBlur={() => markTouched("phone_no")}
              inputMode="numeric"
              maxLength={14}
              aria-invalid={Boolean(err("phone_no"))}
              autoComplete="tel"
              required
            />
          </label>
          {err("phone_no") && <div style={errStyle}>{errors.phone_no}</div>}
          <br />

          <label>
            Agency Email <span style={{ color: "crimson" }}>*</span>
            <input
              type="email"
              name="agency_email"
              placeholder="Agency Email"
              value={info.agency_email}
              onChange={(e) => dispatch({ type: "update", field: "agency_email", value: e.target.value })}
              onBlur={() => markTouched("agency_email")}
              aria-invalid={Boolean(err("agency_email"))}
              autoComplete="email"
              required
            />
          </label>
          {err("agency_email") && <div style={errStyle}>{errors.agency_email}</div>}
          <br />

          <label>
            Address <span style={{ color: "crimson" }}>*</span>
            <textarea
              name="address"
              placeholder="Address"
              value={info.address}
              onChange={(e) => dispatch({ type: "update", field: "address", value: e.target.value })}
              onBlur={() => markTouched("address")}
              aria-invalid={Boolean(err("address"))}
              autoComplete="street-address"
              rows={3}
              required
            />
          </label>
          {err("address") && <div style={errStyle}>{errors.address}</div>}
          <br />

          <label>
            License Number <span style={{ color: "crimson" }}>*</span>
            <input
              type="text"
              name="license_number"
              placeholder="License Number"
              value={info.license_number}
              onChange={(e) => dispatch({ type: "update", field: "license_number", value: e.target.value })}
              onBlur={() => markTouched("license_number")}
              aria-invalid={Boolean(err("license_number"))}
              autoComplete="off"
              required
            />
          </label>
          {err("license_number") && <div style={errStyle}>{errors.license_number}</div>}
        </fieldset>

        <div style={{ marginTop: 16 }}>
          <button type="submit" disabled={!canSubmit || submitting}>
            {submitting ? "Submitting…" : "Register Agency"}
          </button>
          &nbsp;
          <button type="reset" disabled={submitting} onClick={() => setTouched({})}>
            Clear
          </button>
        </div>
      </form>
    </div>
  );
}

TourAgencyReg.propTypes = {
  onSubmit: PropTypes.func,
};
