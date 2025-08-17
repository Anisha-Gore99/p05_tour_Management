import React, { useEffect, useState } from "react";
import { fetchTourPackagesByAgency } from "../../api/TourApi"; // Ensure this function exists
import { useNavigate } from "react-router-dom";

const AgencyViewTours = () => {
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const agency = JSON.parse(localStorage.getItem("loggedUser"));
  const agencyId = agency?.uid;
  if (!agencyId) {
  console.error("Agency ID not found in localStorage. Raw data:", agency);
}

  useEffect(() => {
    const loadPackages = async () => {
      try {
        if (!agencyId) {
          throw new Error("Agency ID not found in local storage.");
        }

        console.log("Fetching tour packages for agency:", agencyId);
        const data = await fetchTourPackagesByAgency(agencyId);
        console.log("Received data:", data);
        setPackages(data);
      } catch (err) {
        console.error("Error fetching agency tour packages:", err);
        setError("Failed to load tour packages.");
      } finally {
        setLoading(false);
      }
    };

    loadPackages();
  }, [agencyId]);

  if (loading) return <p>Loading your tour packages...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Your Tour Packages</h2>
      {packages.length === 0 ? (
        <p>No tour packages found for your agency.</p>
      ) : (
        <div className="package-list">
          {packages.map((pkg) => (
            <div key={pkg.packageId} className="package-card">
              <h3>{pkg.pname}</h3>
              <p><strong>Destination:</strong> {pkg.destination}</p>
              <p><strong>Description:</strong> {pkg.description}</p>
              <button onClick={() => navigate(`/agency/tour/edit/${pkg.packageId}`)}>
                Edit Package
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AgencyViewTours;

