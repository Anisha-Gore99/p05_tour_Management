import React, { useState, useReducer } from 'react';
import PropTypes from 'prop-types';
import './TourAgencyReg.css'; 



export default function TourAgencyReg({ onSubmit }) 
{
//     const [formData,setFormData]= useState({
//   uid: '',
//   password: '',
//   tour_agency_name: '',
//   phone_no: '',
//   agency_email: '',
//   address: '',
//   license_number: ''
// });
const init={
  uname: "",
  password: "",
  tour_agency_name: '',
  phone_no: '',
  agency_email: '',
  address: '',
  license_number: ''
  }


// function reducer(state, action) {
//   switch (action.type) {
//     case 'update':
//       return { ...state, [action.field]: action.value };
//     case 'reset':
//       return initialInfo;
//     default:
//       return state;
//   }
// }

 const reducer=(state,action)=>{
    switch(action.type){
        case 'update':return {...state, [action.field]: action.value};
        case 'reset': return init;
    }
  }

const [info, dispatch] = useReducer(reducer,init);

//     const handleFormSubmit = (e) => {
//     e.preventDefault();
//     onSubmit(formData);
//   };

 const sendData=()=>{
   
    }
  
    return(
        <div>
            <h1>Tour Agency Registration</h1>
            <form onSubmit={sendData} onReset={() => dispatch({ type: 'reset' })} noValidate>
          
      <input type="text"
             name="uid"
             placeholder="User ID"
             value={info.uid}
             onChange={e => dispatch({ type:'update', field:'uid', value:e.target.value })}
             required />
      <br/>

      <input type="password"
             name="password"
             placeholder="Password"
             value={info.pwd}
             onChange={e => dispatch({ type:'update', field:'password', value:e.target.value })}
             required />
      <br/>

      <input type="text"
             name="tour_agency_name"
             placeholder="Agency Name"
             value={info.tour_agency_name}
             onChange={e => dispatch({ type:'update', field:'tour_agency_name', value:e.target.value })}
             required />
      <br/>

      <input type="tel"
             name="phone_no"
             placeholder="Phone Number"
             value={info.phone_no}
             onChange={e => dispatch({ type:'update', field:'phone_no', value:e.target.value })}
             pattern="[0-9]{10}"
             required />
      <br/>

      <input type="email"
             name="agency_email"
             placeholder="Agency Email"
             value={info.agency_email}
             onChange={e => dispatch({ type:'update', field:'agency_email', value:e.target.value })}
             required />
      <br/>

      <textarea
             name="address"
             placeholder="Address"
             value={info.address}
             onChange={e => dispatch({ type:'update', field:'address', value:e.target.value })}
             required />
      <br/>

      <input type="text"
             name="license_number"
             placeholder="License Number"
             value={info.license_number}
             onChange={e => dispatch({ type:'update', field:'license_number', value:e.target.value })}
             required />
      <br/>

      <div className="button-group">
        <button type="submit">Register Agency</button>
        <button type="reset">Clear</button>
      </div>
    </form>
        </div>
    ) 
}
 TourAgencyReg.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};