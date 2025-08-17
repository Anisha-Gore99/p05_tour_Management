import React, { useEffect, useState } from "react";

export default function AgencyPackageForm({ initial, onSubmit, submitting }) {
  const [form, setForm] = useState({
    packageName: "",
    description: "",
    destination: "",
    price: "",
    durationDays: "",
    categoryId: "",
  });

  useEffect(() => {
    if (initial) {
      setForm({
        packageName: initial.packageName ?? initial.pname ?? "",
        description: initial.description ?? "",
        destination: initial.destination ?? "",
        price: initial.price ?? initial.cost ?? "",
        durationDays: initial.durationDays ?? initial.duration ?? "",
        categoryId: initial.category?.categoryId ?? initial.categoryId ?? "",
      });
    }
  }, [initial]);

  const handle = (e) => {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      packageName: form.packageName.trim(),
      description: form.description.trim(),
      destination: form.destination.trim(),
      price: Number(form.price),
      durationDays: Number(form.durationDays),
      // backend expects numeric or nested category; sending categoryId is simplest
      categoryId: form.categoryId ? Number(form.categoryId) : null,
    });
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: "grid", gap: 12, maxWidth: 520 }}>
      <label>
        Package Name
        <input name="packageName" value={form.packageName} onChange={handle} required />
      </label>
      <label>
        Description
        <textarea name="description" value={form.description} onChange={handle} rows={4} />
      </label>
      <label>
        Destination
        <input name="destination" value={form.destination} onChange={handle} />
      </label>
      <label>
        Price
        <input name="price" type="number" min="0" step="0.01" value={form.price} onChange={handle} required />
      </label>
      <label>
        Duration (days)
        <input name="durationDays" type="number" min="1" value={form.durationDays} onChange={handle} required />
      </label>
      <label>
        Category ID (optional)
        <input name="categoryId" type="number" value={form.categoryId} onChange={handle} />
      </label>

      <button type="submit" disabled={!!submitting}>
        {submitting ? "Saving..." : "Save Package"}
      </button>
    </form>
  );
}
