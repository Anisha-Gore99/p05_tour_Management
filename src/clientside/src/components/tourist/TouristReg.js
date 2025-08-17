import React, { useState } from "react";
import axios from "axios";

export default function TouristReg() {
  const [tourist, setTourist] = useState({
    fname: "",
    lname: "",
    address: "",
    dob: "",
    uid: {
      uname: "",
      email: "",
      password: "",
      phone_no: ""
    }
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (["uname", "email", "password", "phone_no"].includes(name)) {
      setTourist((prev) => ({
        ...prev,
        uid: {
          ...prev.uid,
          [name]: value
        }
      }));
    } else {
      setTourist((prev) => ({
        ...prev,
        [name]: value
      }));
    }
  };

const sendData = async () => {
    try {
        const result = await axios.post("http://localhost:8081/tourist/registertourist", tourist);
        alert("Registration successful!");
        // optionally clear form
    } catch (error) {
        if (error.response && error.response.status === 400) {
            alert(error.response.data); // Shows "Email already exists"
        } else {
            alert("Something went wrong. Please try again.");
        }
        console.error("Error registering tourist:", error);
    }
};


  return (
    <div>
      <h2>Tourist Registration</h2>

      <input type="text" name="fname" placeholder="First Name" onChange={handleChange} />
      <input type="text" name="lname" placeholder="Last Name" onChange={handleChange} />
      <input type="text" name="address" placeholder="Address" onChange={handleChange} />
      <input type="date" name="dob" placeholder="Date of Birth" onChange={handleChange} />

      <input type="text" name="uname" placeholder="Username" onChange={handleChange} />
      <input type="email" name="email" placeholder="Email" onChange={handleChange} />
      <input type="password" name="password" placeholder="Password" onChange={handleChange} />
      <input type="text" name="phone_no" placeholder="Phone No" onChange={handleChange} />

      <button onClick={sendData}>Register</button>
    </div>
  );
}

