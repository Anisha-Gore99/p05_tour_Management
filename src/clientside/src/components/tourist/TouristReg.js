// clientside/src/components/TouristReg.js
import React, { useMemo, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080"; // via gateway

export default function TouristReg() {
  const navigate = useNavigate();

  const [tourist, setTourist] = useState({
    fname: "",
    lname: "",
    address: "",
    dob: "",
    uid: { uname: "", email: "", password: "", phoneNo: "" },
  });

  const [touched, setTouched] = useState({});
  const [submitting, setSubmitting] = useState(false);

  const markTouched = (name) => setTouched((t) => ({ ...t, [name]: true }));

  const handleChange = (e) => {
    const { name, value } = e.target;
    const userFields = ["uname", "email", "password", "phoneNo"];

    if (userFields.includes(name)) {
      setTourist((prev) => ({
        ...prev,
        uid: { ...prev.uid, [name]: value },
      }));
    } else {
      setTourist((prev) => ({ ...prev, [name]: value }));
    }
  };

  // ---------- validation ----------
  const errors = useMemo(() => {
    const e = {};
    const phoneDigits = (tourist.uid.phoneNo || "").replace(/\D/g, "");
    const email = (tourist.uid.email || "").trim();
    const uname = (tourist.uid.uname || "").trim();
    const pwd = tourist.uid.password || "";
    const today = new Date();
    const dob = tourist.dob ? new Date(tourist.dob) : null;

    if (!tourist.fname.trim()) e.fname = "First name is required.";
    if (!tourist.lname.trim()) e.lname = "Last name is required.";
    if (!tourist.address.trim()) e.address = "Address is required.";

    if (!tourist.dob) {
      e.dob = "Date of birth is required.";
    } else if (dob > today) {
      e.dob = "Date of birth cannot be in the future.";
    }

    if (!uname) e.uname = "Username is required.";
    else if (uname.length < 3) e.uname = "Username must be at least 3 characters.";

    if (!email) e.email = "Email is required.";
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email))
      e.email = "Enter a valid email address.";

    if (!pwd.trim()) e.password = "Password is required.";
    else if (pwd.length < 6) e.password = "Password must be at least 6 characters.";

    if (!tourist.uid.phoneNo.trim()) {
      e.phoneNo = "Phone number is required.";
    } else if (phoneDigits.length !== 10) {
      e.phoneNo = "Enter a valid 10-digit phone number.";
    }

    return e;
  }, [tourist]);

  const canSubmit = Object.keys(errors).length === 0;

  const sendData = async () => {
    setSubmitting(true);
    // show messages if submit pressed early
    setTouched({
      fname: true,
      lname: true,
      address: true,
      dob: true,
      uname: true,
      email: true,
      password: true,
      phoneNo: true,
    });

    if (!canSubmit) {
      setSubmitting(false);
      return;
    }

    const payload = {
      ...tourist,
      uid: {
        ...tourist.uid,
        phoneNo: (tourist.uid.phoneNo || "").replace(/\D/g, ""), // sanitize
      },
    };

    try {
      const { data } = await axios.post(
        `${API}/api/tourist/registertourist`,
        payload,
        { headers: { "Content-Type": "application/json" } }
      );

      // Persist returned entities for downstream pages if present
      try {
        if (data) {
          localStorage.setItem("loggedTourist", JSON.stringify(data));
          if (data.uid) {
            localStorage.setItem("loggedUser", JSON.stringify(data.uid));
          }
        }
      } catch {}

      alert("Registration successful!");
      setTourist({
        fname: "",
        lname: "",
        address: "",
        dob: "",
        uid: { uname: "", email: "", password: "", phoneNo: "" },
      });
      setTouched({});
      navigate("/");
    } catch (error) {
      const backend = error?.response?.data;
      const msg =
        typeof backend === "string"
          ? backend
          : backend?.message ||
            backend?.error ||
            "Registration failed. Please check your details and try again.";
      alert(msg);
      console.error("Error registering tourist:", error.response || error);
    } finally {
      setSubmitting(false);
    }
  };

  const errStyle = { color: "crimson", fontSize: 12, marginTop: 4 };

  return (
    <div style={{ maxWidth: 520, margin: "20px auto" }}>
      <h2>Tourist Registration</h2>

      {/* First Name */}
      <label>
        First Name <span style={{ color: "crimson" }}>*</span>
        <input
          type="text"
          name="fname"
          placeholder="First Name"
          value={tourist.fname}
          onChange={handleChange}
          onBlur={() => markTouched("fname")}
          aria-invalid={Boolean(touched.fname && errors.fname)}
          required
        />
      </label>
      {touched.fname && errors.fname && <div style={errStyle}>{errors.fname}</div>}
      <br />

      {/* Last Name */}
      <label>
        Last Name <span style={{ color: "crimson" }}>*</span>
        <input
          type="text"
          name="lname"
          placeholder="Last Name"
          value={tourist.lname}
          onChange={handleChange}
          onBlur={() => markTouched("lname")}
          aria-invalid={Boolean(touched.lname && errors.lname)}
          required
        />
      </label>
      {touched.lname && errors.lname && <div style={errStyle}>{errors.lname}</div>}
      <br />

      {/* Address */}
      <label>
        Address <span style={{ color: "crimson" }}>*</span>
        <input
          type="text"
          name="address"
          placeholder="Address"
          value={tourist.address}
          onChange={handleChange}
          onBlur={() => markTouched("address")}
          aria-invalid={Boolean(touched.address && errors.address)}
          required
        />
      </label>
      {touched.address && errors.address && <div style={errStyle}>{errors.address}</div>}
      <br />

      {/* DOB */}
      <label>
        Date of Birth <span style={{ color: "crimson" }}>*</span>
        <input
          type="date"
          name="dob"
          placeholder="Date of Birth"
          value={tourist.dob}
          onChange={handleChange}
          onBlur={() => markTouched("dob")}
          aria-invalid={Boolean(touched.dob && errors.dob)}
          required
        />
      </label>
      {touched.dob && errors.dob && <div style={errStyle}>{errors.dob}</div>}
      <br />

      {/* Username */}
      <label>
        Username <span style={{ color: "crimson" }}>*</span>
        <input
          type="text"
          name="uname"
          placeholder="Username"
          value={tourist.uid.uname}
          onChange={handleChange}
          onBlur={() => markTouched("uname")}
          aria-invalid={Boolean(touched.uname && errors.uname)}
          required
        />
      </label>
      {touched.uname && errors.uname && <div style={errStyle}>{errors.uname}</div>}
      <br />

      {/* Email */}
      <label>
        Email <span style={{ color: "crimson" }}>*</span>
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={tourist.uid.email}
          onChange={handleChange}
          onBlur={() => markTouched("email")}
          aria-invalid={Boolean(touched.email && errors.email)}
          required
        />
      </label>
      {touched.email && errors.email && <div style={errStyle}>{errors.email}</div>}
      <br />

      {/* Password */}
      <label>
        Password <span style={{ color: "crimson" }}>*</span>
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={tourist.uid.password}
          onChange={handleChange}
          onBlur={() => markTouched("password")}
          aria-invalid={Boolean(touched.password && errors.password)}
          required
        />
      </label>
      {touched.password && errors.password && <div style={errStyle}>{errors.password}</div>}
      <br />

      {/* Phone */}
      <label>
        Phone No <span style={{ color: "crimson" }}>*</span>
        <input
          type="text"
          name="phoneNo"
          placeholder="10-digit phone number"
          value={tourist.uid.phoneNo}
          onChange={handleChange}
          onBlur={() => markTouched("phoneNo")}
          inputMode="numeric"
          aria-invalid={Boolean(touched.phoneNo && errors.phoneNo)}
          required
        />
      </label>
      {touched.phoneNo && errors.phoneNo && <div style={errStyle}>{errors.phoneNo}</div>}
      <br />

      <button onClick={sendData} disabled={!canSubmit || submitting}>
        {submitting ? "Submitting…" : "Register"}
      </button>
    </div>
  );
}
