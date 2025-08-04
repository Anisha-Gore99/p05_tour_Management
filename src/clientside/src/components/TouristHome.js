import { useEffect, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';

export default function TouristHome() {
    const [tourist, setTourist] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    console.log("Tourist object:", tourist);

    useEffect(() => {

          let loggedUser;
    try {
        loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
    } catch (e) {
        setError("Corrupted login session. Please log in again.");
        setLoading(false);
        return;
    }

    const uid = loggedUser?.uid;
    if (!uid) {
        setError("User ID missing. Please log in again.");
        setLoading(false);
        return;
    }

    fetch("http://localhost:8080/getTourist?uid=" + uid)
        .then(resp => {
            if (!resp.ok) throw new Error("Server error");
            return resp.json();
        })
        .then(obj => {
            localStorage.setItem("loggedTourist", JSON.stringify(obj));
            setTourist(obj);
            setLoading(false);
        })
        .catch(err => {
            setError("Failed to load tourist data.");
            console.error(err);
            setLoading(false);
        });
        // const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
        // const uid = loggedUser?.uid;

        // if (uid) {
        //     fetch("http://localhost:8080/getTourist?uid=" + uid)
        //         .then(resp => resp.json())
        //         .then(obj => {
        //             localStorage.setItem("loggedTourist", JSON.stringify(obj));
        //             setTourist(obj);
        //         })
        //         .catch(err => console.error("Error fetching tourist data:", err));
        // }
    }, []);

    return (
        <div>
            <header><h2>Tourist Dashboard</h2></header>

            <nav style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
                <Link to="/viewtours">View Tours</Link>
                <Link to="/bookinghistory">Booking History</Link>
                <Link to="/logout">Logout</Link> {/* ✅ corrected typo: /logoout → /logout */}
            </nav>

            <section className="hero">
                <h1>
                    Welcome, {tourist ? `${tourist.fname} ${tourist.lname}` : 'Loading...'}
                </h1>
                <Outlet />
            </section>
        </div>
    );
}
