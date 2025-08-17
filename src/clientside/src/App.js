import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

import Login from './components/userauth/login';
import AdminHome from './components/admin/AdminHome';
import TourAgencyReg from './components/touragency/TourAgencyReg';
import RegisterForm from './components/userauth/RegisterForm';
import TouristReg from './components/tourist/TouristReg';
import ViewTours from './components/tour/ViewTours';
import TourDetails from './components/tour/TourDetails';
import TransactionReport from './components/admin/TransactionReport';
import LogoutComp from './components/userauth/LogoutComp';
import TouristHome from './components/tourist/TouristHome';
import ManageCategories from "./components/admin/ManageCategory";
import ManageAccounts from "./components/admin/ManageAccount";
import GenerateReport from "./components/admin/GenerateReport";
import TourAgencyHome from './components/touragency/TourAgencyHome';
import AgencyViewTour from './components/touragency/AgencyViewTour';
import ViewAllTours from './components/tourist/ViewAllTours';
import BookingPage from './components/tour/BookingPage';
import ClearLogin from './components/userauth/clearlogin';
import AgencyCreatePackage from './components/touragency/AgencyCreatePackage';
import AgencyEditPackage from './components/touragency/AgencyEditPackage';
import PaymentPage from './components/tour/Payment';
import PaymentSuccess from './components/tour/PaymentSuccess';

import './App.css';

/** Role helper used by the guard */
const getNormalizedRole = () => {
  try {
    const u = JSON.parse(localStorage.getItem('loggedUser'));
    const raw = u?.rid?.rname ?? u?.role ?? u?.roleName ?? u?.role?.name ?? "";
    const s = String(raw).toLowerCase().replace(/[^a-z]/g, "");
    if (["agency", "touragency", "travelagency", "agencyowner"].includes(s)) return "agency";
    if (["admin", "administrator"].includes(s)) return "admin";
    if (["tourist", "user", "customer"].includes(s)) return "tourist";
    return s || null;
  } catch { return null; }
};

/** Minimal guard so only agency accounts can open agency routes */
function RequireAgency({ children }) {
  const role = getNormalizedRole();
  if (role === "agency") return children;
  // if logged in but not agency, send to a sensible home
  if (role === "admin") return <Navigate to="/adminhome" replace />;
  if (role === "tourist") return <Navigate to="/touristhome" replace />;
  // not logged in
  return <Navigate to="/login" replace state={{ from: window.location.pathname }} />;
}

// NavLinks component handles showing/hiding links based on login status and current path
const NavLinks = () => {
  const location = useLocation();
  const mystate = useSelector(state => state.logged);
  const loggedIn = mystate?.loggedIn;

  // Show links if user is NOT logged in OR if on Home page
  const showLinks = !loggedIn || location.pathname === "/";

  if (!showLinks) return null;

  return (
    <nav>
      <Link to="/login">Login</Link> |{' '}
      <Link to="/register">Register</Link> |{' '}
      <Link to="/viewtours"><button>View Tours</button></Link>
      {" "}
      {loggedIn && <ClearLogin />}
    </nav>
  );
};

const Home = () => (
  <>
    <div className="hero">
      <h1>Explore India with Ease</h1>
      <p>Plan, book, and manage your tours in one place</p>
    </div>
    <div
      className="hero"
      style={{
        backgroundImage: `url(${process.env.PUBLIC_URL + '/south-india-tourism.png'})`,
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center center',
        backgroundSize: 'cover',
        padding: '60px 20px',
        textAlign: 'center',
        color: 'black',
      }}
    />
    <section>
      <h2>Why Choose Us?</h2>
      <div className="cards">
        <div className="card">
          <h3>Verified Agencies</h3>
          <p>Book tours only through verified and trusted travel agencies.</p>
        </div>
        <div className="card">
          <h3>Easy Booking</h3>
          <p>Simple, fast, and secure booking process for individuals and families.</p>
        </div>
        <div className="card">
          <h3>24/7 Support</h3>
          <p>Customer service available round the clock for your convenience.</p>
        </div>
      </div>
    </section>
  </>
);

function App() {
  const mystate = useSelector((state) => state.logged);

  // NOTE: kept your handlers as-is
  const handleRegister = (createdAgency) => {
    console.log("Registered (from child):", createdAgency);
    alert('Tour agency registered successfully.');
  };

  const handleTouristRegister = async (info) => {
    try {
      const res = await fetch('/api/touristreg', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(info),
      });
      if (!res.ok) throw new Error(res.statusText);
      alert('Tourist registered successfully.');
    } catch (err) {
      console.error(err);
      alert('Tourist registration failed.');
    }
  };

  return (
    <Router>
      <div className="App">
        <header>Tour Management System</header>

        <NavLinks />

        <Routes>
          {/* Public */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<RegisterForm onSubmitAgency={handleRegister} />} />
          <Route path="/logout" element={<LogoutComp />} />
          <Route path="/viewtours" element={<ViewTours />} />
          {/* Support BOTH URL styles for details */}
          <Route path="/tourdetails/:packageId" element={<TourDetails />} />
          <Route path="/tours/:id" element={<TourDetails />} />
          {/* Booking always includes a scheduleId */}
          <Route path="/booking/:scheduleId" element={<BookingPage />} />

          {/* Admin */}
          <Route path="/adminhome" element={<AdminHome />} />
          <Route path="/admin/manage-categories" element={<ManageCategories />} />
          <Route path="/admin/account-management" element={<ManageAccounts />} />
          <Route path="/admin/transactions" element={<TransactionReport />} />
          <Route path="/admin/reports" element={<GenerateReport />} />

          {/* Agency (guarded) */}
          <Route path="/agencyhome" element={
            <RequireAgency><TourAgencyHome /></RequireAgency>
          } />
          <Route path="/agency/tours" element={
            <RequireAgency><AgencyViewTour /></RequireAgency>
          } />
          <Route path="/agency/tour/new" element={
            <RequireAgency><AgencyCreatePackage /></RequireAgency>
          } />
          <Route path="/agency/tour/edit/:packageId" element={
            <RequireAgency><AgencyEditPackage /></RequireAgency>
          } />

          {/* Tourist (post-login area) */}
          <Route path="/touristreg" element={<TouristReg onSubmit={handleTouristRegister} />} />
          <Route path="/touristhome" element={<TouristHome />}>
            <Route path="viewtours" element={<ViewAllTours />} />
          </Route>

          {/* Optional direct URL for tourist list */}
          <Route path="/viewalltours" element={<ViewAllTours />} />
          <Route path="/pay/:paymentId" element={<PaymentPage />} />
           <Route path="/payment-success" element={<PaymentSuccess />} />
        </Routes>

        <footer>&copy; 2025 Tour Management System. All rights reserved.</footer>
      </div>
    </Router>
  );
}

export default App;
