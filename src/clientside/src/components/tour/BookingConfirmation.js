import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../../css/bookingConfirmation.css';

const BookingConfirmation = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const bookingInfo = location.state?.bookingInfo;

  if (!bookingInfo) {
    return (
      <div className="confirmation-container">
        <h2>No booking information available.</h2>
        <button onClick={() => navigate('/touristdashboard')} className="back-btn">
          Go to Dashboard
        </button>
      </div>
    );
  }

  const {
    bookingId,
    status,
    bookingDate,
    tourSchedule,
    companions
  } = bookingInfo;

  const tourPackage = tourSchedule?.tourPackage;
  const agency = tourPackage?.tourAgency;

  return (
    <div className="confirmation-container">
      <h2>🎉 Successful Booking! Make Payment to Confirm Your Tour</h2>

      <div className="booking-summary">
        <p><strong>Booking ID:</strong> {bookingId}</p>
        <p><strong>Status:</strong> {status}</p>
        <p><strong>Booking Date:</strong> {typeof bookingDate === 'string' ? new Date(bookingDate).toLocaleDateString() : bookingDate.toLocaleDateString()}</p>

        <h3>Tour Package</h3>
        <p><strong>Name:</strong> {tourPackage?.pname || 'N/A'}</p>
        <p><strong>Destination:</strong> {tourPackage?.destination || 'N/A'}</p>

        <h3>Travel Dates</h3>
        <p>
          <strong>Start:</strong> {tourSchedule?.startDate ? new Date(tourSchedule.startDate).toLocaleDateString() : 'N/A'} &nbsp;
          <strong>End:</strong> {tourSchedule?.endDate ? new Date(tourSchedule.endDate).toLocaleDateString() : 'N/A'}
        </p>

        <h3>Tour Agency</h3>
        <p><strong>Name:</strong> {agency?.tourAgencyName || 'N/A'}</p>
        <p><strong>Phone:</strong> {agency?.phoneNo || 'N/A'}</p>
        <p><strong>Email:</strong> {agency?.agencyEmail || 'N/A'}</p>

        <h3>Companions</h3>
        <ul className="companion-list">
          {companions?.length > 0 ? companions.map((c, index) => (
            <li key={index}>
              {c.fname} {c.lname}, Age: {c.age}, Gender: {c.gender}
            </li>
          )) : <li>No companions listed.</li>}
        </ul>
      </div>

      {/* Payment Button (non-functional for now) */}
      <button className="payment-btn" disabled>
        Make Payment
      </button>

      <button onClick={() => navigate('/touristdashboard')} className="back-btn">
        Go to Dashboard
      </button>
    </div>
  );
};

export default BookingConfirmation;
