import { useNavigate } from "react-router-dom";
import { logout } from "../../Reduxfeatures/slice.js";
import { useDispatch } from 'react-redux';

export default function LogoutComp(){
    const navigate =useNavigate();
    const dispatch =useDispatch();
    localStorage.clear();
    dispatch(logout()) //loggedIn will become false
    navigate("/")
    return null; // prevents rendering issues
}