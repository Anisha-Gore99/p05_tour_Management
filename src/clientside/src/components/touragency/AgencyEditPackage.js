import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AgencyPackageForm from "./AgencyPackageForm";
import { fetchTourPackageById, updateTourPackage } from "../../api/TourApi";

export default function AgencyEditPackage() {
  const { packageId } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [pkg, setPkg] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchTourPackageById(packageId);
        setPkg(data);
      } catch (e) {
        console.error(e);
        alert("Failed to load package.");
      } finally {
        setLoading(false);
      }
    })();
  }, [packageId]);

  const handleUpdate = async (payload) => {
    setSaving(true);
    try {
      // Make sure tourAgencyId persists (some backends require it on update):
      const body = { ...pkg, ...payload, tourAgencyId: pkg?.tourAgencyId };
      await updateTourPackage(packageId, body);
      navigate("/agency/tours");
    } catch (e) {
      console.error(e);
      alert("Failed to update package.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div style={{ padding: 16 }}>Loading…</div>;
  if (!pkg) return <div style={{ padding: 16 }}>Package not found.</div>;

  return (
    <div style={{ padding: 16 }}>
      <h2>Edit Tour Package</h2>
      <AgencyPackageForm initial={pkg} onSubmit={handleUpdate} submitting={saving} />
    </div>
  );
}
