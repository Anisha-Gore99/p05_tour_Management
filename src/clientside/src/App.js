import logo from './logo.svg';
import './App.css';
import Login from './components/login';
import AdminHome from './components/AdminHome';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function Home() {
  return (<>
      <div className="hero">
        <h1>Explore India with Ease</h1>
        <p>Plan, book, and manage your tours in one place</p>
      </div>

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
}

function App() {
  return (
   <Router>
      <div className="App">
        <header>Tour Management System</header>

        <nav>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
          <Link to="/packages">Tour Packages</Link>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/adminhome" element={<AdminHome />} />
          {/* Add other routes here like <Route path="/register" element={<Register />} /> */}
        </Routes>

        <footer>
          &copy; 2025 Tour Management System. All rights reserved.
        </footer>
      </div>
    </Router>
  
  );
}

export default App;