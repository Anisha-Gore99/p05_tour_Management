// src/components/touragency/AgencyCreatePackage.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AgencyPackageForm from "./AgencyPackageForm";
import { createTourPackage, fetchAgencyByUserId } from "../../api/TourApi";

// Read agency id from various possible shapes and normalize to a Number
const readAgencyId = (obj) =>
  Number(
    obj?.tourAgencyId ??    // normalized/cached key
    obj?.tour_agency_id ??  // backend snake_case
    obj?.taid ??            // alternate backend key
    obj?.id ??              // generic id
    NaN
  );

export default function AgencyCreatePackage() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);

  // Resolve the Tour Agency ID (cache-first, then API), and normalize into localStorage
  const getTourAgencyId = async () => {
    // 1) try cache
    try {
      const cached = JSON.parse(localStorage.getItem("loggedAgency") || "null");
      const id = readAgencyId(cached);
      if (!Number.isNaN(id)) return id;
    } catch {
      // ignore parse errors
    }

    // 2) fetch by user id and cache
    const user = JSON.parse(localStorage.getItem("loggedUser") || "null");
    const uid = user?.uid;
    if (!uid) throw new Error("Not logged in as an agency.");

    const agency = await fetchAgencyByUserId(uid); // GET /api/agency/by-user?uid=...

    // normalize snake_case -> a consistent camelCase id
    const id = readAgencyId(agency);
    if (Number.isNaN(id)) {
      console.warn("Agency payload shape:", agency);
      throw new Error("Agency profile not found for this user.");
    }

    const normalized = { ...agency, tourAgencyId: id };
    localStorage.setItem("loggedAgency", JSON.stringify(normalized));
    return id;
  };

  const handleCreate = async (payload) => {
    setSaving(true);
    try {
      const tourAgencyId = await getTourAgencyId();

      // Build request body for your tour-service
      // If your backend expects snake_case (common when DB columns are snake_case), use this:
      const body = {
        package_name: payload.packageName,
        description: payload.description,
        destination: payload.destination,
        category_id: payload.categoryId ?? null,
        tour_agency_id: tourAgencyId,
      };

      // If your backend expects camelCase instead, switch to:
      // const body = {
      //   packageName: payload.packageName,
      //   description: payload.description,
      //   destination: payload.destination,
      //   categoryId: payload.categoryId ?? null,
      //   tourAgencyId,
      // };

      await createTourPackage(body);
      navigate("/agency/tours");
    } catch (e) {
      console.error(e);
      alert(e?.response?.data?.message || e?.message || "Failed to create package.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div style={{ padding: 16 }}>
      <h2>Create Tour Package</h2>
      <AgencyPackageForm onSubmit={handleCreate} submitting={saving} />
    </div>
  );
}
