import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { logout } from "../../Reduxfeatures/slice";
import { useNavigate } from "react-router-dom";

export default function LogoutComp() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    try {
      // Clear persisted session first
      localStorage.removeItem("loggedUser");
      localStorage.removeItem("loggedTourist");
    } catch (_) {}

    // Update Redux
    dispatch(logout());

    // Go home
    navigate("/", { replace: true });
  }, [dispatch, navigate]);

  return <p style={{ padding: 16 }}>Logging out…</p>;
}
