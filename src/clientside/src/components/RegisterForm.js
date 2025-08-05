
import React, { useState } from "react";
import TourAgencyReg from "./TourAgencyReg";
import TouristReg from "./TouristReg"; // <-- UNCOMMENTED/ADDED THIS LINE
import './RegisterForm.css'; // Assuming you have some styles for this component

// ADDED onSubmitTourist to the props destructuring
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
    // Both TouristReg and onSubmitTourist should now be defined
    return <TouristReg onSubmit={onSubmitTourist} />;
  }

  return null; // fallback
};



  // If you have a TouristReg component:
  // return <TouristReg onSubmit={...} />;
  //return <h3>Tourist registration form goes here</h3>;

  // Fallback for unexpected scenarios (e.g., role not set after submission)
 

export default RegisterForm;
