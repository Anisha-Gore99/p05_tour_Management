import React, { useState } from "react";
import TourAgencyReg from "../touragency/TourAgencyReg";
import TouristReg from "../tourist/TouristReg";
import '../../styles/RegisterForm.css';

const RegisterForm = ({ onSubmitAgency, onSubmitTourist }) => {
  const [role, setRole] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitted(true);
  };

  if (!submitted) {
    return (
      <form onSubmit={handleSubmit}>
        <label>
          Select Role:
          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          >
            <option value="">-- choose one --</option>
            <option value="tourist">Tourist</option>
            <option value="agency">Tour Agency</option>
          </select>
        </label>
        <button type="submit" disabled={!role} style={{ marginLeft: 8 }}>
          Register
        </button>
      </form>
    );
  }

  // After submission, render the appropriate registration form
  if (role === "agency") {
    return <TourAgencyReg onSubmit={onSubmitAgency} />;
  }

  if (role === "tourist") {
    return <TouristReg onSubmit={onSubmitTourist} />;
  }

  return null; // fallback
};

export default RegisterForm;