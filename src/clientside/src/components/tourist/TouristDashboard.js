import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import {
  fetchTouristProfile,
  selectTouristProfile,
  selectIsProfileComplete,
  selectUser,
  selectTouristId
} from '../../Reduxfeatures/user/userSlice';
import {
  fetchTouristBookings,
  cancelBooking
} from '../../Reduxfeatures/booking/bookingSlice';
import '../../css/touristDashboard.css';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const TouristDashboard = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();

  const user = useSelector(selectUser);
  const touristProfile = useSelector(selectTouristProfile);
  const isProfileComplete = useSelector(selectIsProfileComplete);
  // const touristIdFromRedux = useSelector(selectTouristId);
  // const touristId = touristIdFromRedux || parseInt(localStorage.getItem('touristId'), 10);
  const touristIdFromRedux = useSelector(selectTouristId);
const touristIdFromStorage = localStorage.getItem('touristId');
const touristId = touristIdFromRedux || (touristIdFromStorage && !isNaN(touristIdFromStorage) ? parseInt(touristIdFromStorage, 10) : null);

  console.log('Tourist ID:', touristId); // Debug log
  

  const rawBookings = useSelector((state) => state.booking.bookings);
  const bookings = Array.isArray(rawBookings) ? rawBookings : [];

  const userStatus = useSelector((state) => state.user.status);
  const bookingStatus = useSelector((state) => state.booking.status);
  const userError = useSelector((state) => state.user.error);
  const bookingError = useSelector((state) => state.booking.error);
  console.log('User error:', userError);
  console.log('Booking error:', bookingError);
 
  const profileUpdated = location.state?.profileUpdated;
  const successMessage = location.state?.message;

  useEffect(() => {
    if (touristId && typeof touristId === 'number') {
      dispatch(fetchTouristBookings(touristId));
    }
  }, [dispatch, touristId]);

  useEffect(() => {
    const role = user?.role?.toLowerCase();
    if (role === 'tourist' && user?.uid) {
      dispatch(fetchTouristProfile(user.uid));
    }
  }, [dispatch, user?.uid, user?.role]);

  useEffect(() => {
    if (user?.uid && touristProfile === null && userStatus === 'succeeded') {
      navigate('/complete-profile', {
        state: { fromDashboard: true, message: 'Please complete your profile to continue.' }
      });
    }
  }, [touristProfile, userStatus, navigate, user?.uid]);

  const handleCancelBooking = (bookingId) => {
    if (window.confirm('Are you sure you want to cancel this booking?')) {
      dispatch(cancelBooking(bookingId));
      toast.success("Booking successfully cancelled!");
    }
  };

  const renderBookings = (bookingsList) => {
    if (!bookingsList || bookingsList.length === 0) {
      return <p>No bookings to display.</p>;
    }

    return (
      <ul className="bookings-list">
        {bookingsList.map((booking) => {
          const companions = booking.companions || [];
          const tourSchedule = booking.tourSchedule;
          const tourPackage = tourSchedule?.tourPackage;
          const tourAgency = tourPackage?.tourAgency;
          const bookingId = booking.bookingId || booking.id;

          if (!tourSchedule || !tourPackage || !tourAgency) {
            console.warn('Incomplete booking:', booking); // Debug log
            return (
              <li key={bookingId} className="booking-card">
                <p>Incomplete booking data for Booking ID: {bookingId}</p>
              </li>
            );
          }

          return (
            <li key={bookingId} className="booking-card">
              <div className="booking-header">
                <h3>Booking ID: {bookingId}</h3>
                <span className={`booking-status status-${booking.status.toLowerCase()}`}>
                  {booking.status}
                </span>
              </div>

              <p><strong>Booking Date:</strong> {new Date(booking.bookingDate).toLocaleDateString()}</p>
              <p><strong>Number of Tourists:</strong> {companions.length}</p>
              <p><strong>Tour Package:</strong> {tourPackage.pname}</p>
              <p><strong>Destination:</strong> {tourPackage.destination}</p>
              <p><strong>Travel Dates:</strong> {new Date(tourSchedule.startDate).toLocaleDateString()} - {new Date(tourSchedule.endDate).toLocaleDateString()}</p>
              <p><strong>Cost:</strong> ₹{tourSchedule.cost}</p>

              <h4>Companions:</h4>
              <ul className="tourist-list">
                {companions.map((td, index) => (
                  <li key={index}>
                    {td.fname} {td.lname}, Age: {td.age}, Gender: {td.gender}
                  </li>
                ))}
              </ul>

              <h4>Tour Agency Info:</h4>
              <p><strong>Name:</strong> {tourAgency.tourAgencyName}</p>
              <p><strong>Phone:</strong> {tourAgency.phoneNo}</p>
              <p><strong>Email:</strong> {tourAgency.agencyEmail}</p>

              {booking.status.toLowerCase() === 'pending' && (
                <>
                  <p className="pending-message">
                    Your booking is <strong>pending</strong>. Please complete payment to confirm.
                  </p>
                  <button
                    className="payment-btn"
                    onClick={() => toast.info(`Payment initiated for Booking ID ${bookingId}`)}
                    disabled
                  >
                    Make Payment
                  </button>
                </>
              )}

              {booking.status.toLowerCase() !== 'cancelled' && (
                <button
                  className="cancel-booking-btn"
                  onClick={() => handleCancelBooking(bookingId)}
                >
                  Cancel Booking
                </button>
              )}
            </li>
          );
        })}
      </ul>
    );
  };

  //const myBookings = bookings.filter((b) => b.status?.toLowerCase() !== 'cancelled');
  const myBookings = bookings; //  Show all bookings, even cancelled


  return (
    <div className="dashboard-container">
      {userStatus === 'loading' || bookingStatus === 'loading' ? (
        <div className="loading">Loading dashboard...</div>
      ) : userError || bookingError ? (
        <div className="error">
          Error:{' '}
          {typeof userError === 'string'
            ? userError
            : userError?.message || bookingError?.message || 'Something went wrong.'}
        </div>
      ) : !isProfileComplete ? (
        <div className="form-container">
          <p>Please complete your profile to access the dashboard.</p>
          <button onClick={() => navigate('/complete-profile')} className="submit-btn">
            Go to Profile Form
          </button>
        </div>
      ) : (
        <>
          <header className="dashboard-header">
            <h1>Welcome, {touristProfile ? touristProfile.fname : 'Tourist'}!</h1>
            <p>Manage your tours and personal information here.</p>

            {profileUpdated && (
              <div className="success-message">
                Your profile has been updated successfully!
              </div>
            )}

            {successMessage && (
              <div className="success-message">
                {successMessage}
              </div>
            )}
          </header>

          <nav className="dashboard-nav">
            <ul>
              <li><a href="#my-bookings">My Bookings</a></li>
              <li><a href="#profile-management">Profile Management</a></li>
            </ul>
          </nav>

          <section id="my-bookings" className="dashboard-section">
            <h2>My Bookings</h2>
            {/*renderBookings(myBookings)*/}
            {!touristId || isNaN(touristId)
    ? <p className="error">Unable to load bookings: Tourist ID is missing.</p>
    : renderBookings(myBookings)}
          </section>

          <section id="profile-management" className="dashboard-section">
            <h2>Profile Management</h2>
            {touristProfile ? (
              <div className="profile-details">
                <p><strong>First Name:</strong> {touristProfile.fname}</p>
                <p><strong>Last Name:</strong> {touristProfile.lname}</p>
                <p><strong>Date of Birth:</strong> {new Date(touristProfile.dob).toLocaleDateString()}</p>
                <p><strong>Address:</strong> {touristProfile.address}</p>
                <p><strong>Email:</strong> {touristProfile.uid?.email || 'N/A'}</p>
                <p><strong>Phone:</strong> {touristProfile.uid?.phoneNo || 'N/A'}</p>
                <button
                  className="edit-profile-btn"
                  onClick={() => navigate('/complete-profile')}
                >
                  Edit Profile
                </button>
              </div>
            ) : (
              <p>Profile information not available.</p>
            )}
          </section>
        </>
      )}
    </div>
  );
};

export default TouristDashboard;
