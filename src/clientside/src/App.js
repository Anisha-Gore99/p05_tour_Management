import logo from './logo.svg';
import './App.css';
import Login from './components/login';
import AdminHome from './components/AdminHome';
import TourAgencyReg from './components/TourAgencyReg';
import RegisterForm from './components/RegisterForm';                                                                                                    
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
   const handleRegister = async info => {
    console.log('Registering agency:', info);
    try {
      const res = await fetch('/api/touragencyreg', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(info)
      });
      if (!res.ok) throw new Error(res.statusText);
      alert('Tour agency registered successfully.');
    } catch (err) {
      console.error(err);
      alert('Registration failed.');
    }
  };
  return (
   <Router>
      <div className="App">
        <header>Tour Management System</header>

        <nav>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
          <Link to="/packages">Tour Packages</Link>
          {/* <Link to="/touragencyreg">Tour Agency Registration</Link> */}
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/adminhome" element={<AdminHome />} />
          <Route path="/register" element={<RegisterForm onSubmitAgency={handleRegister} />} />
          {/* <Route path="/touragencyreg" element={<TourAgencyReg onSubmit={handleRegister} />} /> */}
          {/* Add other routes here like <Route path="/register" element={<Register />} /> */}
        </Routes>

        <footer>
          &copy; 2025 Tour Management System. All rights reserved.
        </footer>
      </div>
    </Router>
  
  );
}

// function App() {
  // const handleRegister = async info => {
  //   console.log('Registering agency:', info);
  //   try {
  //     const res = await fetch('/api/touragencyreg', {
  //       method: 'POST',
  //       headers: { 'Content-Type': 'application/json' },
  //       body: JSON.stringify(info)
  //     });
  //     if (!res.ok) throw new Error(res.statusText);
  //     alert('Tour agency registered successfully.');
  //   } catch (err) {
  //     console.error(err);
  //     alert('Registration failed.');
  //   }
  // };

//   return (
//     <Router>
//       {/* header/nav */}
//       <Routes>
//         <Route path="/" element={<Home />} />
//         <Route path="/login" element={<Login />} />
//         <Route path="/touragencyreg" element={<TourAgencyReg onSubmit={handleRegister} />} />
//         <Route path="/adminhome" element={<AdminHome />} />
//       </Routes>
//       {/* footer */}
//     </Router>
//   );
// }

export default App;