import React, { useState, useReducer } from 'react';
import PropTypes from 'prop-types';
import '../../styles/TouristReg.css'; 


export default function TouristReg({ onSubmit }) 
{
    const init={
  uname: "",
  password: "",
  dob: '',
  address: '',
  fname: '',
  lname: '',
  }

   const reducer=(state,action)=>{
    switch(action.type){
        case 'update':return {...state, [action.field]: action.value};
        case 'reset': return init;
    }
  }

  const [info, dispatch] = useReducer(reducer,init);

  const sendData=()=>{
   
    }
  
     return(
            <div>
                <h1>Tourist Registration</h1>
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
    
          <input type="date"
                 name="dob"
                 placeholder="Tourist Date of Birth"
                 value={info.dob}
                 onChange={e => dispatch({ type:'update', field:'dob', value:e.target.value })}
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
                 name="fname"
                 placeholder="First Name"
                 value={info.fname}
                 onChange={e => dispatch({ type:'update', field:'fname', value:e.target.value })}
                 required />
          <br/>

          <input type="text"
                 name="lname"
                 placeholder="Last Name"
                 value={info.fname}
                 onChange={e => dispatch({ type:'update', field:'lname', value:e.target.value })}
                 required />
          <br/>
    
          <div className="button-group">
            <button type="submit">Register Tourist</button>
            <button type="reset">Clear</button>
          </div>
        </form>
            </div>
        ) 
    }
     TouristReg.propTypes = {
      onSubmit: PropTypes.func.isRequired,
    };
