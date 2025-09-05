import React, { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchTourDetails } from '../../Reduxfeatures/tour/tourSlice';
import {
  selectIsAuthenticated,
  selectIsProfileComplete
} from '../../Reduxfeatures/user/userSlice';
import '../../css/tourdetails.css';

const TourDetails = () => {
  const { packageId } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { currentTour, loading, error } = useSelector((state) => state.tour);
  const isAuthenticated = useSelector(selectIsAuthenticated);
  const isProfileComplete = useSelector(selectIsProfileComplete);

  useEffect(() => {
    if (packageId) {
      dispatch(fetchTourDetails(packageId));
    }
  }, [dispatch, packageId]);

  const handleScheduleBooking = (scheduleId) => {
    if (!isAuthenticated) {
      alert("Please log in or register to book a tour.");
      navigate("/login");
      return;
    }

    if (!isProfileComplete) {
      alert("Please complete your profile information before booking.");
      navigate("/complete-profile");
      return;
    }

    navigate(`/bookings/create/${scheduleId}`);
  };

  if (loading) {
    return <div className="loading-message">Loading tour details...</div>;
  }

  if (error) {
    return <div className="error-message">Error: {error.message}</div>;
  }

  if (!currentTour) {
    return <div className="no-tour-found">Tour not found.</div>;
  }

  const sanitizeString = (input) => input.replace(/\s+/g, '');

  return (
    <div className="tour-details-container">
      <div className="tour-details-card">
        <img
          src={`/images/${sanitizeString(currentTour.pname)}.jpg`}
          alt={currentTour.pname}
          className="tour-image"
        />

        <div className="tour-info">
          <h2>{currentTour.pname}</h2>
          <p className="tour-destination">Destination: {currentTour.destination}</p>
          <p className="tour-description">Description: {currentTour.description}</p>

          <h3>Available Schedules:</h3>
          <ul className="schedule-list">
            {currentTour.schedules && currentTour.schedules.length > 0 ? (
              currentTour.schedules.map((schedule) => (
                <li key={schedule.scheduleId} className="schedule-item">
                  <p>Start Date: {schedule.start_date}</p>
                  <p>End Date: {schedule.end_date}</p>
                  <p>Duration: {schedule.duration} days</p>
                  <p className="schedule-cost">Price: Rs.{schedule.cost.toFixed(2)}</p>

                  <button
                    className="book-now-button"
                    onClick={() => handleScheduleBooking(schedule.scheduleId)}
                  >
                    Book This Schedule
                  </button>
                </li>
              ))
            ) : (
              <p>No schedules available for this tour.</p>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default TourDetails;
