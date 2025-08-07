import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

// Inside your component:
const mystate = useSelector((state) => state.logged);
const navigate = useNavigate();

const handleBooking = () => {
  if (mystate.loggedIn) {
    alert("You are logged in. Proceeding to booking...");
    navigate("/booking");
  } else {
    const goTo = window.confirm("You need to login to book. Are you already registered?");
    if (goTo) {
      alert("Redirecting to login page...");
      setTimeout(() => navigate("/login"), 300);
    } else {
      alert("Redirecting to registration page...");
      setTimeout(() => navigate("/register"), 300);
    }
  }
};
