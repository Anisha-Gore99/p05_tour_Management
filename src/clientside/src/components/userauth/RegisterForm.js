import React, { useState } from "react";
import TourAgencyReg from "../touragency/TourAgencyReg";
import TouristReg from "../tourist/TouristReg";

export default function RegisterForm({ onSubmitAgency, onSubmitTourist }) {
  const [role, setRole] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!role) return;
    setSubmitted(true);
  };

  if (!submitted) {
    return (
      <form onSubmit={handleSubmit} style={{ maxWidth: 480, margin: "24px auto" }}>
        <h2>Register As</h2>

        <label style={{ display: "block", margin: "8px 0" }}>
          <input
            type="radio"
            name="role"
            value="tourist"
            checked={role === "tourist"}
            onChange={(e) => setRole(e.target.value)}
          />
          &nbsp;Tourist
        </label>

        <label style={{ display: "block", margin: "8px 0" }}>
          <input
            type="radio"
            name="role"
            value="agency"
            checked={role === "agency"}
            onChange={(e) => setRole(e.target.value)}
          />
          &nbsp;Tour Agency
        </label>

        <button type="submit" disabled={!role}>Continue</button>
      </form>
    );
  }

  // render the selected registration form
  if (role === "agency") return <TourAgencyReg onSubmit={onSubmitAgency} />;
  if (role === "tourist") return <TouristReg onSubmit={onSubmitTourist} />;
  return null;
}
