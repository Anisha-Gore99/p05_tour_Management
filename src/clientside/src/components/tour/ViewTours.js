import React, { useEffect, useState } from "react";
import { fetchTourPackages } from "../../api/TourApi";
import { useNavigate } from "react-router-dom";

const ViewTours = () => {
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const loadPackages = async () => {
      try {
        const data = await fetchTourPackages();
        console.log("Fetched packages:", data);
        setPackages(data);
      } catch (error) {
        console.error("Error fetching tour packages:", error);
      } finally {
        setLoading(false);
      }
    };
    loadPackages();
  }, []);

  if (loading) return <p>Loading tour packages...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Available Tour Packages</h2>
      {packages.length === 0 ? (
        <p>No tour packages found.</p>
      ) : (
        <div className="package-list">
          {packages.map((pkg) => {
            console.log(pkg); // ✅ You can keep this for now
            return (
              <div key={pkg.packageId} className="package-card">
                <h3>{pkg.pname}</h3>
                <p><strong>Destination:</strong> {pkg.destination}</p>
                <p><strong>Description:</strong> {pkg.description}</p>
                <button onClick={() => navigate(`/tourdetails/${pkg.packageId}`)}>
                  View Details / Book Now
                </button>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default ViewTours;
