import React, { useState, useEffect, useRef } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { logout } from '../../Reduxfeatures/user/userSlice';
import { clearTourAgencyUser } from '../../Reduxfeatures/touragency/tourAgencySlice';
import { getAllCategories } from '../../api/categories';
import { searchTourPackages } from '../../api/tours';
import '../../css/header.css';
import logo from '../../assets/tournest-logo.png';

const mockDestinationApi = async (query) => {
  const allDestinations = [
    "Paris", "London", "New York", "Tokyo", "Rome", "Dubai", "Singapore",
    "Sydney", "Bangkok", "Iceland", "Maldives", "Switzerland", "Goa",
    "Uttarakhand", "Ranthambore, Rajasthan", "Alleppey, Kerala", "North India", "Andaman and Nicobar Islands",
    "Spiti Valley, Himachal Pradesh", "Nainital, Uttarakhand", "Interlaken, Switzerland", "Serengeti, Tanzania",
    "Phuket, Thailand", "Cairo, Egypt", "Kilimanjaro, Tanzania", "Melbourne, Australia", "Uttarakhand, India",
    "Jammu and Kashmir, India", "Varanasi, Uttar Pradesh", "Manali, Himachal Pradesh", "Darjeeling, West Bengal",
    "Ooty, Tamil Nadu", "Jaipur, Rajasthan", "Haridwar, Uttarakhand",
    "Chennai, Tamil Nadu", "Jaisalmer, Rajasthan", "Hampi, Karnataka", "Khajuraho, Madhya Pradesh",
    "Aurangabad, Maharashtra", "Dubai, UAE"
  ];
  await new Promise(resolve => setTimeout(resolve, 300));
  return allDestinations.filter(dest =>
    dest.toLowerCase().includes(query.toLowerCase())
  );
};

const Header = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const user = useSelector(state => state.user.user); // tourist or admin
  const tourAgencyUser = useSelector(state => state.touragency.tourAgencyUser); // tour agency

  const [destination, setDestination] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [isCategoryDropdownOpen, setIsCategoryDropdownOpen] = useState(false);
  const categoryDropdownRef = useRef(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const data = await getAllCategories();
        setCategories(data);
      } catch (error) {
        console.error("Failed to fetch categories:", error);
        setCategories([]);
      }
    };
    fetchCategories();
  }, []);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (categoryDropdownRef.current && !categoryDropdownRef.current.contains(event.target)) {
        setIsCategoryDropdownOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleDestinationChange = async (e) => {
    const value = e.target.value;
    setDestination(value);
    if (value.length > 0) {
      try {
        const results = await mockDestinationApi(value);
        setSuggestions(results);
      } catch (error) {
        console.error("Failed to fetch suggestions:", error);
        setSuggestions([]);
      }
    } else {
      setSuggestions([]);
    }
  };

  const handleSuggestionClick = (suggestedDestination) => {
    setDestination(suggestedDestination);
    setSuggestions([]);
  };

  const handleCategorySelect = (categoryId) => {
    setSelectedCategory(categoryId);
    setIsCategoryDropdownOpen(false);
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const results = await searchTourPackages(destination, selectedCategory);
      navigate('/tours', { state: { searchResults: results } });
    } catch (error) {
      console.error("Search failed:", error);
      navigate('/tours', { state: { searchResults: [], error: 'Search failed' } });
    }
  };

  
  const handleLogout = () => {
    dispatch(logout());
    dispatch(clearTourAgencyUser());
    navigate('/');
  };

  const renderLinks = () => {
    const role = user?.role?.toLowerCase();

    if (role === 'tourist') {
      return (
        <>
          <li><Link to="/touristdashboard">Tourist Dashboard</Link></li>
          <li><button onClick={handleLogout} className="logout-btn">Logout</button></li>
        </>
      );
    } else if (role === 'tour_agency' || tourAgencyUser) {
      return (
        <>
          <li><Link to="/agencydashboard">AgencyDashboard</Link></li>
          <li><button onClick={handleLogout} className="logout-btn">Logout</button></li>
        </>
      );
    } else if (role === 'admin') {
      return (
        <>
          <li><Link to="/admindashboard">Admin Dashboard</Link></li>
          <li><button onClick={handleLogout} className="logout-btn">Logout</button></li>
        </>
      );
    } else {
      return (
        <>
          <li><Link to="/login">Login</Link></li>
          <li><Link to="/register">Register</Link></li>
        </>
      );
    }
  };
  const selectedCategoryName = categories.find(c => c.catId === selectedCategory)?.cname || 'Category';

  return (
    <header className="main-header">
      <div className="header-left">
        <Link to="/" className="header-logo">
          TourNest
          <img src={logo} alt="TourNest Logo" className="logo-img" />
        </Link>
        <form className="search-bar" onSubmit={handleSearch}>
          <div style={{ position: 'relative' }}>
            <input
              type="text"
              placeholder="Search by destination..."
              value={destination}
              onChange={handleDestinationChange}
            />
            {suggestions.length > 0 && (
              <ul className="suggestions-dropdown">
                {suggestions.map((sug) => (
                  <li key={sug} onClick={() => handleSuggestionClick(sug)}>
                    {sug}
                  </li>
                ))}
              </ul>
            )}
          </div>

          <div className="custom-dropdown" ref={categoryDropdownRef}>
            <div
              className="dropdown-toggle"
              onClick={() => setIsCategoryDropdownOpen(!isCategoryDropdownOpen)}
            >
              {selectedCategoryName}
            </div>
            {isCategoryDropdownOpen && (
              <ul className="suggestions-dropdown">
                <li onClick={() => handleCategorySelect('')}>All</li>
                {categories.map((category) => (
                  <li
                    key={category.catId}
                    onClick={() => handleCategorySelect(category.catId)}
                  >
                    {category.cname}
                  </li>
                ))}
              </ul>
            )}
          </div>

          <button type="submit">Search</button>
        </form>
      </div>
      <nav className="header-right">
        <ul className="nav-links">
          <li><Link to="/">Home</Link></li>
          <li><Link to="/tours">Tours</Link></li>
          {renderLinks()}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
