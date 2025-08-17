import React, { useEffect, useState } from "react";
import axios from "axios";

const ManageCategories = () => {
  const [packages, setPackages] = useState([]);

  useEffect(() => {
    const fetchPackages = async () => {
      try {
        const res = await axios.get("http://localhost:8082/api/admin/tourpackages");
        setPackages(res.data);
        console.log("packages", res.data);
      } catch (err) {
        console.error("Error fetching tour packages:", err);
      }
    };

    fetchPackages();
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Manage Travel Categories</h2>
      {packages.length === 0 ? (
        <p>No tour packages available.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Package Name</th>
              <th>Description</th>
              <th>Destination</th>
            </tr>
          </thead>
          <tbody>
            {packages.map((pkg, index) => (
              <tr key={pkg.package_id || index}>
                <td>{pkg.package_id}</td>
                <td>{pkg.pname}</td>
                <td>{pkg.description}</td>
                <td>{pkg.destination}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ManageCategories;
