import React from "react";
import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

export default function RequireAuth({ children }) {
  const loggedIn = useSelector((state) => state?.logged?.loggedIn);
  const location = useLocation();

  if (!loggedIn) {
    // Redirect to /login and remember where we were trying to go
   // In RequireAuth.js
return <Navigate to="/login" state={{ from: location.pathname + location.search }} replace />;

  }

  return children;
}
