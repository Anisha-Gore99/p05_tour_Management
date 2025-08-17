import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function ViewAllTours() {
  const [tours, setTours] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/tours") // update to your actual endpoint
      .then((res) => {
        setTours(res.data);
      })
      .catch((err) => {
        console.error("Error fetching tours:", err);
      });
  }, []);

  const handleBookNow = (scheduleId) => {
    navigate(`/booking/${scheduleId}`);
  };

  return (
    <div className="view-tours-container">
      <h1>Available Tours</h1>
      <div className="tours-grid">
        {tours.map((tour) => (
          <div className="tour-card" key={tour.packageId}>
            <img
              src={tour.imageUrl || "/default-tour.jpg"}
              alt={tour.packageName}
              className="tour-image"
            />
            <h3>{tour.packageName}</h3>
            <p>{tour.description}</p>
            <p>
              <strong>Price:</strong> ₹{tour.price}
            </p>
            <p>
              <strong>Schedule ID:</strong> {tour.scheduleId}
            </p>
            <button
              onClick={() => handleBookNow(tour.scheduleId)}
              className="book-now-btn"
            >
              Book Now
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
