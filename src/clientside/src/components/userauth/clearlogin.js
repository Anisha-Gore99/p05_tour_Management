import React from "react";
import { useDispatch } from "react-redux";
import { logout } from "../../Reduxfeatures/slice";
import { useNavigate } from "react-router-dom";

export default function ClearLogin() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleClear = () => {
    localStorage.removeItem("loggedUser");
    localStorage.removeItem("loggedTourist");
    dispatch(logout());
    alert("Login data cleared!");
    navigate("/", { replace: true });
  };

  return (
    <button
      style={{
        background: "crimson",
        color: "white",
        padding: "6px 12px",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
      }}
      onClick={handleClear}
    >
      Clear Login
    </button>
  );
}
