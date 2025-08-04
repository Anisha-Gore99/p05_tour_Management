
import React, { useState } from "react";
import TourAgencyReg from "./TourAgencyReg";
import TouristReg from "./TouristReg";
import './RegisterForm.css';





const RegisterForm = ({ onSubmitAgency,onSubmitTourist }) => {
  const [role, setRole] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = e => {
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
            onChange={e => setRole(e.target.value)}
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

  if (role === "agency") {
    return <TourAgencyReg onSubmit={onSubmitAgency} />;
  }


  if (role === "tourist") {
    return <TouristReg onSubmit={onSubmitTourist} />;
  }

  return null; // fallback
};



  // If you have a TouristReg component:
  // return <TouristReg onSubmit={...} />;
  //return <h3>Tourist registration form goes here</h3>;


export default RegisterForm;

