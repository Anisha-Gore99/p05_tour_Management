import React, { useReducer } from "react";
import PropTypes from "prop-types";
import axios from "axios";

const initialState = {
  uid: {
    uname: "",
    password: "",
    email: "",
    phone_no: ""
  },
  tour_agency_name: "",
  phone_no: "",
  agency_email: "",
  address: "",
  license_number: ""
};

function reducer(state, action) {
  switch (action.type) {
    case "updateNested":
      return {
        ...state,
        [action.parent]: {
          ...state[action.parent],
          [action.field]: action.value
        }
      };
    case "update":
      return {
        ...state,
        [action.field]: action.value
      };
    case "reset":
      return initialState;
    default:
      return state;
  }
}

export default function TourAgencyReg({ onSubmit }) {
  const [info, dispatch] = useReducer(reducer, initialState);

  const sendData = async (e) => {
    e.preventDefault();

    try {
      // Adjust the URL to your backend endpoint
      const response = await axios.post(
        "http://localhost:8081/touragency/register",
        info
      );
      console.log("Registration successful:", response.data);
      if (onSubmit) onSubmit(response.data);
      dispatch({ type: "reset" });
    } catch (error) {
      console.error("Error registering tour agency:", error.response || error);
      alert(
        error.response?.data || "Failed to register agency. Please try again."
      );
    }
  };

  return (
    <div>
      <h1>Tour Agency Registration</h1>
      <form onSubmit={sendData} onReset={() => dispatch({ type: "reset" })} noValidate>
        <input
          type="text"
          name="uname"
          placeholder="User Name"
          value={info.uid.uname}
          onChange={(e) =>
            dispatch({
              type: "updateNested",
              parent: "uid",
              field: "uname",
              value: e.target.value
            })
          }
          required
        />
        <br />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={info.uid.password}
          onChange={(e) =>
            dispatch({
              type: "updateNested",
              parent: "uid",
              field: "password",
              value: e.target.value
            })
          }
          required
        />
        <br />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={info.uid.email}
          onChange={(e) =>
            dispatch({
              type: "updateNested",
              parent: "uid",
              field: "email",
              value: e.target.value
            })
          }
          required
        />
        <br />

        <input
          type="tel"
          name="phone_no_user"
          placeholder="User Phone Number"
          value={info.uid.phone_no}
          onChange={(e) =>
            dispatch({
              type: "updateNested",
              parent: "uid",
              field: "phone_no",
              value: e.target.value
            })
          }
          pattern="[0-9]{10}"
          required
        />
        <br />

        <input
          type="text"
          name="tour_agency_name"
          placeholder="Agency Name"
          value={info.tour_agency_name}
          onChange={(e) =>
            dispatch({ type: "update", field: "tour_agency_name", value: e.target.value })
          }
          required
        />
        <br />

        <input
          type="tel"
          name="phone_no_agency"
          placeholder="Agency Phone Number"
          value={info.phone_no}
          onChange={(e) =>
            dispatch({ type: "update", field: "phone_no", value: e.target.value })
          }
          pattern="[0-9]{10}"
          required
        />
        <br />

        <input
          type="email"
          name="agency_email"
          placeholder="Agency Email"
          value={info.agency_email}
          onChange={(e) =>
            dispatch({ type: "update", field: "agency_email", value: e.target.value })
          }
          required
        />
        <br />

        <textarea
          name="address"
          placeholder="Address"
          value={info.address}
          onChange={(e) =>
            dispatch({ type: "update", field: "address", value: e.target.value })
          }
          required
        />
        <br />

        <input
          type="text"
          name="license_number"
          placeholder="License Number"
          value={info.license_number}
          onChange={(e) =>
            dispatch({ type: "update", field: "license_number", value: e.target.value })
          }
          required
        />
        <br />

        <div className="button-group">
          <button type="submit">Register Agency</button>
          <button type="reset">Clear</button>
        </div>
      </form>
    </div>
  );
}

TourAgencyReg.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};
