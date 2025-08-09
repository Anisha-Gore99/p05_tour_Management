import logo from './logo.svg';
import './App.css';
import Login from './components/userauth/login';
import AdminHome from './components/admin/AdminHome';
import TourAgencyReg from './components/touragency/TourAgencyReg';
import RegisterForm from './components/userauth/RegisterForm';   

import TouristReg from './components/tourist/TouristReg';        
import ViewTours from './components/tour/ViewTours';   
import TourDetails from './components/tour/TourDetails';        
import TransactionReport from './components/admin/TransactionReport';                                                                                                                                                                        

                                                                                                                                                                             
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import LogoutComp from './components/userauth/LogoutComp';
import { useSelector } from 'react-redux';
import TouristHome from './components/tourist/TouristHome';
import ManageCategories from "./components/admin/ManageCategory";
import ManageAccounts from "./components/admin/ManageAccount";
import GenerateReport from "./components/admin/GenerateReport";
import TourAgencyHome from './components/touragency/TourAgencyHome';
import AgencyViewTour from './components/touragency/AgencyViewTour';


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
  const mystate =useSelector((state)=>state.logged);// initial state of logged

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
 const handleTouristRegister = async info => {
  console.log('Registering tourist:', info);
  try {
    const res = await fetch('/api/touristreg', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(info)
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
        <div style={{display:mystate.loggedIn?'none':'block'}}>
        <nav>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
          <Link to="/packages">Tour Packages</Link>

          {/* <Link to="/touragencyreg">Tour Agency Registration</Link> */}

        {/* <Link to="/touristreg">Tourist Register</Link> */}

        </nav>
        </div>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/adminhome" element={<AdminHome />} />
          <Route path="/register" element={<RegisterForm onSubmitAgency={handleRegister}  />} />

          <Route path="/logout" element={<LogoutComp/>} />
          <Route path="/agencyhome" element={<TourAgencyHome />} />


          <Route path="/touristreg" element={<TouristReg onSubmit={handleTouristRegister} />} />
           <Route path="/touristhome" element={<TouristHome />}>
               <Route path="viewtours" element={<ViewTours />} />
           </Route>
          <Route path="/packages" element={<ViewTours />} />
           <Route path="/tourdetails/:packageId" element={<TourDetails />} />
           <Route path="/admin/manage-categories" element={<ManageCategories />} />
          <Route path="/admin/account-management" element={<ManageAccounts />} />
          <Route path="/admin/transactions" element={<TransactionReport />} />
          <Route path="/admin/reports" element={<GenerateReport />} />
          <Route path="/agency/tours" element={<AgencyViewTour />} />






         


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