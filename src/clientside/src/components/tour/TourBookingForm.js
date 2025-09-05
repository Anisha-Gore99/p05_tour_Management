import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import {
  fetchTouristProfile,
  selectIsProfileComplete,
  selectTouristId,
  selectUser
} from '../../Reduxfeatures/user/userSlice';
import { createBooking, fetchAvailability } from '../../api/bookingApi';
import { fetchTouristBookings } from '../../Reduxfeatures/booking/bookingSlice';
import '../../css/bookingForm.css';

const countryOptions = [
  "India", "United States", "United Kingdom", "Canada", "Australia",
  "Germany", "France", "Japan", "China", "Brazil", "South Africa",
  "New Zealand", "Singapore", "UAE", "Russia", "Italy", "Spain", "Mexico"
];

const TourBookingForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { scheduleId } = useParams();

  const isProfileComplete = useSelector(selectIsProfileComplete);
  const touristIdFromRedux = useSelector(selectTouristId);
  const user = useSelector(selectUser);

  const touristId = touristIdFromRedux || parseInt(localStorage.getItem('touristId'), 10);

  const [touristDetails, setTouristDetails] = useState([
    { fname: '', lname: '', age: '', gender: '', idProof: '', nationality: '' }
  ]);
  const [availableBookings, setAvailableBookings] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadAvailability = async () => {
      const available = await fetchAvailability(scheduleId);
      setAvailableBookings(available);
    };
    if (scheduleId) {
      loadAvailability();
    }
  }, [scheduleId]);

  useEffect(() => {
  if ((!isProfileComplete || !touristIdFromRedux) && user?.uid) {
    dispatch(fetchTouristProfile(user.uid)).then((res) => {
      const profile = res.payload;
      if (profile?.touristId) {
        localStorage.setItem('touristId', profile.touristId); // ✅ Store touristId
      }
    });
  }
}, [dispatch, user?.uid, isProfileComplete, touristIdFromRedux]);


  const handleTouristChange = (index, event) => {
    const { name, value } = event.target;
    const updatedList = [...touristDetails];
    updatedList[index][name] = value;
    setTouristDetails(updatedList);
  };

  const handleAddTourist = () => {
    if (availableBookings !== null && touristDetails.length >= availableBookings) {
      alert("You can't add more companions than available seats.");
      return;
    }
    if (touristDetails.length >= 7) {
      alert('You can only add up to 7 companions.');
      return;
    }
    setTouristDetails([
      ...touristDetails,
      { fname: '', lname: '', age: '', gender: '', idProof: '', nationality: '' }
    ]);
  };

const handleSubmit = async (event) => {
  event.preventDefault();

  if (!isProfileComplete || !touristId || isNaN(touristId)) {
    alert('Please complete your profile information before booking.');
    navigate('/complete-profile');
    return;
  }

  if (availableBookings !== null && touristDetails.length > availableBookings) {
    alert("Not enough seats available. Please reduce the number of companions.");
    return;
  }

  for (let i = 0; i < touristDetails.length; i++) {
    const { fname, lname, age, gender, idProof, nationality } = touristDetails[i];
    if (!fname || !lname || !age || !gender || !idProof || !nationality) {
      alert(`Please fill all fields for Companion ${i + 1}`);
      return;
    }
    const ageNum = parseInt(age);
    if (isNaN(ageNum) || ageNum <= 0) {
      alert(`Companion ${i + 1} must be older than 0 months.`);
      return;
    }
  }

  const bookingPayload = {
    touristId,
    scheduleId,
    touristDetails
  };

  try {
    setSubmitting(true);
    const bookingResponse = await createBooking(bookingPayload); // Create booking

    dispatch(fetchTouristBookings(touristId)); // Refresh Redux state with latest bookings

    const updatedAvailability = await fetchAvailability(scheduleId);
    setAvailableBookings(updatedAvailability);

    navigate('/booking-confirmation', {
      state: {
        bookingInfo: bookingResponse // Pass full booking object
      }
    });
  } catch (err) {
    console.error('Booking failed:', err);
    setError('Booking failed. Please ensure all details are correct.');
  } finally {
    setSubmitting(false);
  }
};


  return (
    <div className="form-container">
      <h2>Book Your Tour</h2>
      {availableBookings !== null && (
        <p className="availability-info">Seats Available: {availableBookings}</p>
      )}
      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        {touristDetails.map((tourist, index) => (
          <div key={index} className="tourist-card">
            <h3>Companion {index + 1}</h3>

            <div className="form-group">
              <label htmlFor={`fname-${index}`}>First Name</label>
              <input
                type="text"
                id={`fname-${index}`}
                name="fname"
                value={tourist.fname}
                onChange={(e) => handleTouristChange(index, e)}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor={`lname-${index}`}>Last Name</label>
              <input
                type="text"
                id={`lname-${index}`}
                name="lname"
                value={tourist.lname}
                onChange={(e) => handleTouristChange(index, e)}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor={`age-${index}`}>Age</label>
              <input
                type="number"
                id={`age-${index}`}
                name="age"
                value={tourist.age}
                onChange={(e) => handleTouristChange(index, e)}
                min="1"
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor={`gender-${index}`}>Gender</label>
              <select
                id={`gender-${index}`}
                name="gender"
                value={tourist.gender}
                onChange={(e) => handleTouristChange(index, e)}
                required
              >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor={`idProof-${index}`}>ID Proof Type</label>
              <select
                id={`idProof-${index}`}
                name="idProof"
                value={tourist.idProof}
                onChange={(e) => handleTouristChange(index, e)}
                required
              >
                <option value="">Select ID Proof</option>
                <option value="Aadhaar">Aadhaar</option>
                <option value="Passport">Passport</option>
                <option value="PAN">PAN</option>
                <option value="Driving License">Driving License</option>
                <option value="Voter ID">Voter ID</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor={`nationality-${index}`}>Nationality</label>
              <select
                id={`nationality-${index}`}
                name="nationality"
                value={tourist.nationality}
                onChange={(e) => handleTouristChange(index, e)}
                required
              >
                <option value="">Select Country</option>
                {countryOptions.map((country) => (
                  <option key={country} value={country}>{country}</option>
                ))}
              </select>
            </div>
          </div>
        ))}

        <button type="button" onClick={handleAddTourist} className="add-tourist-btn">
          Add Another Companion
        </button>

        <button type="submit" className="submit-btn" disabled={submitting}>
          {submitting ? 'Submitting...' : 'Book Now'}
        </button>

        {submitting && (
          <p className="loading-message">Creating your booking...</p>
        )}
      </form>
    </div>
  );
};

export default TourBookingForm;
