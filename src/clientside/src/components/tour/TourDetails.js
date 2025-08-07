import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchTourById, fetchScheduleByPackageId } from "../../api/TourApi";
import { useSelector } from "react-redux";

const TourDetails = () => {
  const { packageId } = useParams();
  const [tour, setTour] = useState(null);
  const [schedules, setSchedules] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const mystate = useSelector((state) => state.logged);
  const navigate = useNavigate();

  const handleBooking = () => {
    if (mystate.loggedIn) {
      navigate("/booking");
    } else {
      setShowModal(true);
    }
  };

  const handleModalChoice = (choice) => {
    setShowModal(false);
    if (choice === "login") {
      navigate("/login");
    } else if (choice === "register") {
      navigate("/register");
    }
  };

  useEffect(() => {
    const loadDetails = async () => {
      try {
        const tourData = await fetchTourById(packageId);
        setTour(tourData);
        const scheduleData = await fetchScheduleByPackageId(packageId);
        setSchedules(scheduleData);
      } catch (err) {
        console.error("Failed to load tour details", err);
      }
    };

    if (packageId) {
      loadDetails();
    } else {
      console.error("No package ID found in URL");
    }
  }, [packageId]);

  if (!tour) return <p>Loading tour details... or tour not found.</p>;

  return (
    <div>
      <h2>{tour.pname}</h2>
      <p><strong>Destination:</strong> {tour.destination}</p>
      <p><strong>Description:</strong> {tour.description}</p>

      <h3>Available Schedules</h3>
      {schedules.length === 0 ? (
        <p>No schedules available.</p>
      ) : (
        <ul>
          {schedules.map((s) => (
            <li key={s.schedule_id}>
              <div>From: {s.start_date}</div>
              <div>To: {s.end_date}</div>
              <div>Seats Available: {s.available_bookings}</div>
              <div>Cost: ₹{s.cost}</div>
              <button
                disabled={s.available_bookings <= 0}
                onClick={handleBooking}
              >
                {s.available_bookings <= 0 ? "Sold Out" : "Book Now"}
              </button>
            </li>
          ))}
        </ul>
      )}

      {/* Simple Logic-Based Modal */}
      {showModal && (
        <div>
          <h4>Login or Register to Continue Booking</h4>
          <button onClick={() => handleModalChoice("login")}>
            I already have an account
          </button>
          <button onClick={() => handleModalChoice("register")}>
            I want to register
          </button>
          <button onClick={() => handleModalChoice("cancel")}>
            Cancel
          </button>
        </div>
      )}
    </div>
  );
};

export default TourDetails;
