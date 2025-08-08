import React, { use, useState } from 'react';
import '../../styles/login.css'; // Adjust the path as necessary
import axios from 'axios';
import { type } from '@testing-library/user-event/dist/type';
import { useNavigate } from 'react-router-dom';
import { login } from '../../Reduxfeatures/slice.js'; // Adjust the import path as necessary
import { useDispatch, useSelector } from 'react-redux';

function Login() {
//   const [username, setUsername] = useState('');
//   const [password, setPassword] = useState('');
   const [message, setMessage] = useState('');

  const init={
    uname: "",
    password: ""
  }

  const reducer=(state,action)=>{
    switch(action.type){
        case 'update':return {...state, [action.field]: action.value};
        case 'reset': return init;
    }
  }

  const[info , dispatch] = React.useReducer(reducer, init);
  const navigate = useNavigate();
  const reduxAction=useDispatch(); //redux action to update the state



  const SendData=(e)=>{
    e.preventDefault();
    const reqOptions={
        method:'POST',
        headers:{'content-type':'application/json'},
        body:JSON.stringify(info)
        }   
        fetch("http://localhost:8081/api/user/chkLogin", reqOptions)
        .then(resp => {if(resp.ok)
                         return resp.text();
                        else      
                         throw new Error('Network response was not ok')
                        })
        .then(text => text.length ? JSON.parse(text) : {})
       .then(obj => 
        {
                  if(Object.keys(obj).length ===0) {
                    setMessage("Wrong username or password!");
                    // Redirect or perform further actions here
                  }else{
                    if(obj.status ===  false)
                    {
                      alert("Request has not been approved!");
                    }else{
                      reduxAction(login())
                      localStorage.setItem("loggedUser", JSON.stringify(obj));
                      if(obj.rid.rid===1)
                      {
                          navigate('/adminhome');  // Redirect to admin home page
                      }
                      else if(obj.rid.rid===2)
                      {
                        navigate('/touristhome');  // Redirect to tourist home page 
                      }
                      else if(obj.rid.rid===3)
                      {
                      
                      }
                      else
                      {
                        setMessage("Invalid role ID!");
                    }
                  }
                }
      
      })
    
      .catch(() => setMessage("Server error. Please try again later."));                         //user rest api login loginchek
    }

 
  

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={SendData}>
        <input type="text" placeholder="Username" value={info.uname} onChange={(e)=>{dispatch({type:'update',field:'uname',value:e.target.value})}} required />
        <br />
        <input type="password" placeholder="Password" value={info.password} onChange={(e)=>{dispatch({type:'update',field:'password',value:e.target.value})}} required />
        <br />
        <div className="button-group">
        <button type="submit">Login</button>
        <button type="reset" onClick={()=>{dispatch({type:'reset'})}}>Clear</button>
        </div>
      </form>
      <p> {JSON.stringify(info)}</p>
      <p>{message}</p>
    </div>
  );

  }

export default Login;