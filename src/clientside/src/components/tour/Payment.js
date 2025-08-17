import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

const PAYMENT_API = process.env.REACT_APP_PAYMENT_API || "https://localhost:5000"; // set to YOUR .NET base
const BOOKING_API = process.env.REACT_APP_BOOKING_API || "http://localhost:8084";

const GET_PAYMENT = (id) => `${PAYMENT_API}/api/payments/${id}`;
const CONFIRM_PAYMENT = (id) => `${PAYMENT_API}/api/payments/${id}/confirm`;
const CONFIRM_BOOKING = (id) => `${BOOKING_API}/api/bookings/${id}/confirm`; // you'll add this tiny endpoint below

function formatINR(n) {
  try { return Number(n).toLocaleString("en-IN",{style:"currency",currency:"INR"}); }
  catch { return `₹${n}`; }
}

export default function PaymentPage() {
  const { paymentId } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [pay, setPay] = useState(null);
  const [err, setErr] = useState("");

  useEffect(() => {
    let live = true;
    (async () => {
      try {
        const r = await fetch(GET_PAYMENT(paymentId));
        if (!r.ok) throw new Error(`HTTP ${r.status}`);
        const data = await r.json();
        if (!live) return;
        setPay(data);
      } catch (e) {
        if (!live) return;
        setErr(`Unable to load payment ${paymentId}. Is PaymentService running?`);
      } finally {
        if (live) setLoading(false);
      }
    })();
    return () => { live = false; };
  }, [paymentId]);

  const handlePayNow = async () => {
    if (!pay?.paymentId) return;
    try {
      // 1) confirm payment in .NET
      const r1 = await fetch(CONFIRM_PAYMENT(pay.paymentId), {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: "SUCCESS", providerRef: "demo_txn_123" }),
      });
      if (!r1.ok) throw new Error(`Payment confirm failed: HTTP ${r1.status}`);

      // 2) mark booking confirmed in BookingService (Java)
      if (pay.bookingId) {
        const r2 = await fetch(CONFIRM_BOOKING(pay.bookingId), { method: "POST" });
        if (!r2.ok) throw new Error(`Booking confirm failed: HTTP ${r2.status}`);
      }

      // 3) show success (route or inline)
      navigate(`/payment-success?bookingId=${pay.bookingId}&paymentId=${pay.paymentId}`);
    } catch (e) {
      alert(e.message || "Payment failed.");
    }
  };

  const handleCancel = async () => {
    if (!pay?.paymentId) return;
    try {
      await fetch(CONFIRM_PAYMENT(pay.paymentId), {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: "FAILED" }),
      });
    } finally {
      alert("Payment canceled.");
      navigate("/");
    }
  };

  if (loading) return <div style={{padding:20}}>Loading payment…</div>;
  if (err) return <div style={{padding:20, color:"crimson"}}>{err}</div>;
  if (!pay) return <div style={{padding:20}}>Payment not found.</div>;

  return (
    <div style={{padding:20, maxWidth:480}}>
      <h2>Secure Payment</h2>
      <div style={{border:"1px solid #eee", borderRadius:12, padding:16, marginTop:8}}>
        <div><strong>Payment ID:</strong> {pay.paymentId}</div>
        <div><strong>Booking ID:</strong> {pay.bookingId}</div>
        <div><strong>Amount:</strong> {formatINR(pay.amount)}</div>
        <div><strong>Status:</strong> {pay.status}</div>
      </div>

      <div style={{display:"flex", gap:8, marginTop:16}}>
        <button onClick={handlePayNow}>Pay Now</button>
        <button onClick={handleCancel}>Cancel</button>
      </div>
      <p style={{marginTop:12, color:"#666"}}>
        (This demo page stands in for a real gateway like Razorpay/Stripe. Integrate their SDK here later.)
      </p>
    </div>
  );
}
