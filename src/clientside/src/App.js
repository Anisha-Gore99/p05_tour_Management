// // import React from 'react';
// // import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// // import { Provider } from 'react-redux';
// // //import { store } from './Reduxfeatures/store';
// // import Navbar from './components/tour/Navbar';
// // import Home from './components/tour/Home';
// // //import Tours from './components/tour/Tours';
// // //import Login from './components/userauth/Login';
// // //import Register from './components/userauth/Register';
// // // Import other components as you create them
// // // import TouristDashboard from './components/tourist/TouristDashboard';
// // // import TourAgencyDashboard from './components/touragency/TourAgencyDashboard';
// // // import AdminDashboard from './components/admin/AdminDashboard';

// // import './css/App.css';

// // function App() {
// //   return (
// //     <Provider store={store}>
// //       <Router>
// //         <div className="App">
// //           <Navbar />
// //           <main>
// //             <Routes>
// //               <Route path="/" element={<Home />} />
// //               <Route path="/tours" element={<Tours />} />
// //               <Route path="/login" element={<Login />} />
// //               <Route path="/register" element={<Register />} />
// //               {/* Add protected routes here for authenticated users */}
// //               {/* <Route path="/tourist/dashboard" element={<TouristDashboard />} /> */}
// //               {/* <Route path="/agency/dashboard" element={<TourAgencyDashboard />} /> */}
// //               {/* <Route path="/admin/dashboard" element={<AdminDashboard />} /> */}
// //             </Routes>
// //           </main>
// //         </div>
// //       </Router>
// //     </Provider>
// //   );
// // }

// // export default App;

// // import React, { useEffect } from 'react';
// // import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// // import TouristDashboard from './components/tourist/TouristDashboard';
// // import MyBookings from './components/tourist/MyBookings'; 
// // import BookingDetails from './components/tourist/BookingDetails';
// // import TourDetails from './components/tour/TourDetails';
// // import Home from './components/tour/Home';
// // import Login from './components/userauth/Login';
// // import AgencyDashboard from './components/touragency/AgencyDashboard';
// // import CreatePackage from './components/touragency/CreatePackage';
// // import ManagePackages from './components/touragency/ManagePackages';
// // import ViewBookings from './components/touragency/ViewBookings';
// // import { useDispatch, useSelector } from 'react-redux';
// // import { setTourAgencyUser } from './Reduxfeatures/touragency/touragencySlice';
// // import { loginSuccess } from './Reduxfeatures/user/userSlice';


// // /**
// //  * The main component that handles all application routing.
// //  */
// // function App() {
// //   const dispatch = useDispatch();
  
// //   // Example of using the tour agency state
// //   const tourAgencyUser = useSelector(state => state.touragency.tourAgencyUser);
  
// //   useEffect(() => {
// //     // This is where you would dispatch actions to set the user or tour agency user
// //     // upon initial load, for example, by checking a token in local storage or session.
// //     // For now, this is just a placeholder.
// //     const storedUser = JSON.parse(localStorage.getItem('user'));
// //     const storedTourAgencyUser = JSON.parse(localStorage.getItem('tourAgencyUser'));
    
// //     if (storedUser) {
// //       dispatch(loginSuccess(storedUser));
// //     }
    
// //     if (storedTourAgencyUser) {
// //       dispatch(setTourAgencyUser(storedTourAgencyUser));
// //     }
    
// //   }, [dispatch]);
  
// //   return (
// //     <Router>
// //       <div className="App">
// //         <Routes>
// //           <Route path="/" element={<Home />} />
// //           <Route path="/login" element={<Login />} />
// //           <Route path="/dashboard" element={<TouristDashboard />} />
// //           <Route path="/my-bookings" element={<MyBookings />} />
// //           <Route path="/bookings/:id" element={<BookingDetails />} /> 
// //           <Route path="/tour-details/:id" element={<TourDetails />} />
// //           <Route path="/agency-dashboard" element={<AgencyDashboard />} />
// //           <Route path="/agency/create-package" element={<CreatePackage />} />
// //           <Route path="/agency/manage-packages" element={<ManagePackages />} />
// //           <Route path="/agency/view-bookings" element={<ViewBookings />} />
// //         </Routes>
// //       </div>
// //     </Router>
// //   );
// // }

// // export default App;

// import React, { useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import TouristDashboard from './components/tourist/TouristDashboard';
// import MyBookings from './components/tourist/MyBookings'; 
// import BookingDetails from './components/tourist/BookingDetails';
// import TourDetails from './components/tour/TourDetails';
// import Home from './components/tour/Home';
// import Login from './components/userauth/Login';
// import AgencyDashboard from './components/touragency/AgencyDashboard';
// import CreatePackage from './components/touragency/CreatePackage';
// import ManagePackages from './components/touragency/ManagePackages';
// import ViewBookings from './components/touragency/ViewBookings';
// import PaymentForm from './components/tourist/PaymentForm'; // Added PaymentForm import
// import { useDispatch, useSelector } from 'react-redux';
// import { setTourAgencyUser } from './Reduxfeatures/touragency/touragencySlice';
// import { loginSuccess } from './Reduxfeatures/user/userSlice';


// /**
//  * The main component that handles all application routing.
//  */
// function App() {
//   const dispatch = useDispatch();
  
//   // Example of using the tour agency state
//   const tourAgencyUser = useSelector(state => state.touragency.tourAgencyUser);
  
//   useEffect(() => {
//     // This is where you would dispatch actions to set the user or tour agency user
//     // upon initial load, for example, by checking a token in local storage or session.
//     // For now, this is just a placeholder.
//     const storedUser = JSON.parse(localStorage.getItem('user'));
//     const storedTourAgencyUser = JSON.parse(localStorage.getItem('tourAgencyUser'));
    
//     if (storedUser) {
//       dispatch(loginSuccess(storedUser));
//     }
    
//     if (storedTourAgencyUser) {
//       dispatch(setTourAgencyUser(storedTourAgencyUser));
//     }
    
//   }, [dispatch]);
  
//   return (
//     <Router>
//       <div className="App">
//         <Routes>
//           <Route path="/" element={<Home />} />
//           <Route path="/login" element={<Login />} />
//           <Route path="/dashboard" element={<TouristDashboard />} />
//           <Route path="/my-bookings" element={<MyBookings />} />
//           <Route path="/bookings/:id" element={<BookingDetails />} /> 
//           <Route path="/tour-details/:id" element={<TourDetails />} />
//           <Route path="/agency-dashboard" element={<AgencyDashboard />} />
//           <Route path="/agency/create-package" element={<CreatePackage />} />
//           <Route path="/agency/manage-packages" element={<ManagePackages />} />
//           <Route path="/agency/view-bookings" element={<ViewBookings />} />
//           <Route path="/pay/:paymentId" element={<PaymentForm />} />
//         </Routes>
//       </div>
//     </Router>
//   );
// }

// export default App;


// import React, { useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import { useDispatch, useSelector } from 'react-redux';
// import { setTourAgencyUser } from './Reduxfeatures/touragency/touragencySlice';
// import { loginSuccess } from './Reduxfeatures/user/userSlice';

// // Import Navbar and other components
// import Navbar from './components/tour/Navbar';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails';
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import TouristDashboard from './components/tourist/TouristDashboard';
// import MyBookings from './components/tourist/MyBookings'; 
// import BookingDetails from './components/tourist/BookingDetails';
// import PaymentForm from './components/tourist/PaymentForm';
// import AgencyDashboard from './components/touragency/AgencyDashboard';
// import CreatePackage from './components/touragency/CreatePackage';
// import ManagePackages from './components/touragency/ManagePackages';
// import ViewBookings from './components/touragency/ViewBookings';
// import AdminDashboard from './components/admin/AdminDashboard'; // Assuming you have this component
// import ManageUsers from './components/admin/ManageUsers'; // Assuming you have this component
// import Reports from './components/admin/Reports'; // Assuming you have this component

// /**
//  * The main component that handles all application routing.
//  */
// function App() {
//   const dispatch = useDispatch();
  
//   useEffect(() => {
//     // This is where you would dispatch actions to set the user or tour agency user
//     // upon initial load, for example, by checking a token in local storage or session.
//     const storedUser = JSON.parse(localStorage.getItem('user'));
//     const storedTourAgencyUser = JSON.parse(localStorage.getItem('tourAgencyUser'));
    
//     if (storedUser) {
//       dispatch(loginSuccess(storedUser));
//     }
    
//     if (storedTourAgencyUser) {
//       dispatch(setTourAgencyUser(storedTourAgencyUser));
//     }
    
//   }, [dispatch]);
  
//   return (
//     <Router>
//       <div className="App">
//         {/* The Navbar is rendered here, outside of the Routes */}

//         {/*<Navbar /> */}
//         <main>
//           <Routes>
//             {/* Public Routes */}
//             {/* Public Routes */}
//             {/* The Home and Navbar components are now rendered together in this fragment */}
//             <Route path="/" element={
//               <>
//                 <Home />
//                 <Navbar />
//               </>
//             } />
//             {/*<Route path="/" element={<Home />} />*/}
//             <Route path="/tours" element={<Tours />} />
//             <Route path="/tour-details/:id" element={<TourDetails />} />
//             <Route path="/login" element={<Login />} />
//             <Route path="/register" element={<Register />} />

//             {/* Tourist Routes (Protected) */}
//             <Route path="/dashboard" element={<TouristDashboard />} />
//             <Route path="/my-bookings" element={<MyBookings />} />
//             <Route path="/bookings/:id" element={<BookingDetails />} /> 
//             <Route path="/pay/:paymentId" element={<PaymentForm />} />

//             {/* Tour Agency Routes (Protected) */}
//             <Route path="/agency-dashboard" element={<AgencyDashboard />} />
//             <Route path="/agency/create-package" element={<CreatePackage />} />
//             <Route path="/agency/manage-packages" element={<ManagePackages />} />
//             <Route path="/agency/view-bookings" element={<ViewBookings />} />

//             {/* Admin Routes (Protected) */}
//             <Route path="/admin-dashboard" element={<AdminDashboard />} />
//             <Route path="/admin/manage-users" element={<ManageUsers />} />
//             <Route path="/admin/reports" element={<Reports />} />
            
//           </Routes>
//         </main>
//       </div>
//     </Router>
//   );
// }

// export default App;


// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { Provider } from 'react-redux';
// import { store } from './Reduxfeatures/store'; 
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails';
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import Footer from './components/common/Footer';
// import './css/App.css';

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         <Route path="/tours/:id" element={<TourDetails />}/>
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;


// App.js

// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { Provider } from 'react-redux';
// import { store } from './Reduxfeatures/store'; // Correct path to the store
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails'; // Correct import for TourDetails
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import Footer from './components/common/Footer';
// import './css/App.css';

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         {/* CORRECTED: Changed the route parameter from ":id" to ":packageId" */}
//                         {/*<Route path="/tours/:packageId" element={<TourDetails />} />*/}
//                         <Route path="/tour-details/:packageId" element={<TourDetails />} />
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;


// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { Provider } from 'react-redux';
// import { store } from './Reduxfeatures/store';
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails'; 
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import Footer from './components/common/Footer';
// import './css/App.css';

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         <Route path="/tours/:packageId" element={<TourDetails />} /> {/* New Route */}
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;

//correct code
// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { Provider } from 'react-redux';
// import { store } from './Reduxfeatures/store';
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails'; 
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import Footer from './components/common/Footer';
// import './css/App.css';

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         <Route path="/tours/:packageId" element={<TourDetails />} />
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;


//wrong code 

// import React from 'react';
// import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
// import { Provider, useSelector } from 'react-redux';
// import { store } from './Reduxfeatures/store';
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails';
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import TouristDashboard from './components/tourist/TouristDashboard'; // Import the new component
// import Footer from './components/common/Footer';
// import './css/App.css';

// /**
//  * A wrapper component that protects a route.
//  * It checks if a user is authenticated (using a placeholder for now).
//  * If authenticated, it renders the child component. Otherwise, it redirects to the login page.
//  */
// const PrivateRoute = ({ children }) => {
//     // Get the user and tour agency state from the Redux store to check if either is logged in
//     const user = useSelector(state => state.user.user);
//     const tourAgencyUser = useSelector(state => state.touragency.tourAgencyUser);
//     const isLoggedIn = !!user || !!tourAgencyUser;
    
//     return isLoggedIn ? children : <Navigate to="/login" />;
// };

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         <Route path="/tours/:packageId" element={<TourDetails />} />
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                         {/* The Tourist Dashboard is now a private route */}
//                         <Route
//                             path="/TouristDashboard"
//                             element={
//                                 <PrivateRoute>
//                                     <TouristDashboard />
//                                 </PrivateRoute>
//                             }
//                         />
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;

// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { Provider } from 'react-redux';
// import { store } from './Reduxfeatures/store';
// import Header from './components/common/Header';
// import Home from './components/tour/Home';
// import Tours from './components/tour/Tours';
// import TourDetails from './components/tour/TourDetails'; 
// import Login from './components/userauth/Login';
// import Register from './components/userauth/Register';
// import TouristDashboard from './components/tourist/TouristDashboard';
// import TourBookingForm from './components/tour/TourBookingForm';
// import CompleteProfileForm from './components/tourist/CompleteProfileForm';
// import Footer from './components/common/Footer';
// import './css/App.css';

// const App = () => {
//     return (
//         <Provider store={store}>
//             <Router>
//                 <div className="App">
//                     <Header />
//                     <Routes>
//                         <Route path="/" element={<Home />} />
//                         <Route path="/tours" element={<Tours />} />
//                         <Route path="/tours/:packageId" element={<TourDetails />} />
//                         <Route path="/login" element={<Login />} />
//                         <Route path="/register" element={<Register />} />
//                         <Route path="/touristdashboard" element={<TouristDashboard />} />
//                         <Route path="/bookings/create/:scheduleId" element={<TourBookingForm />} /> 
//                         <Route path="/complete-profile" element={<CompleteProfileForm />} />
                        
//                     </Routes>
//                     <Footer />
//                 </div>
//             </Router>
//         </Provider>
//     );
// };

// export default App;



import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider, useDispatch, useSelector } from 'react-redux';
import { store } from './Reduxfeatures/store';
import { fetchTouristProfile, selectUser } from './Reduxfeatures/user/userSlice'; // ✅ Import selectors and thunk
import Header from './components/common/Header';
import Home from './components/tour/Home';
import Tours from './components/tour/Tours';
import TourDetails from './components/tour/TourDetails'; 
import Login from './components/userauth/Login';
import Register from './components/userauth/Register';
import TouristDashboard from './components/tourist/TouristDashboard';
import TourBookingForm from './components/tour/TourBookingForm';
import CompleteProfileForm from './components/tourist/CompleteProfileForm';
import BookingConfirmation from './components/tour/BookingConfirmation';
import AgencyDashboard from './components/touragency/AgencyDashboard';
import Footer from './components/common/Footer';
import './css/App.css';

// ✅ Wrapper to access Redux inside App
const AppContent = () => {
  const dispatch = useDispatch();
  const user = useSelector(selectUser);

  // useEffect(() => {
  //   if (user?.uid) {
  //     dispatch(fetchTouristProfile(user.uid));
  //   }
  // }, [dispatch, user?.uid]);

useEffect(() => {
  const role = user?.role?.toLowerCase();
  if (role === 'tourist' && user?.uid) {
    dispatch(fetchTouristProfile(user.uid));
  }
}, [dispatch, user?.uid, user?.role]);



  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/tours" element={<Tours />} />
        <Route path="/tours/:packageId" element={<TourDetails />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/touristdashboard" element={<TouristDashboard />} />
        <Route path="/bookings/create/:scheduleId" element={<TourBookingForm />} /> 
        <Route path="/complete-profile" element={<CompleteProfileForm />} />
        <Route path="/booking-confirmation" element={<BookingConfirmation />} />
        <Route path="/agencydashboard" element={<AgencyDashboard />} />
      </Routes>
      <Footer />
    </div>
  );
};

const App = () => {
  return (
    <Provider store={store}>
      <Router>
        <AppContent />
      </Router>
    </Provider>
  );
};

export default App;
