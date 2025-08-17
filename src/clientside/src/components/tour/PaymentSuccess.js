import React from "react";
import { useSearchParams, Link } from "react-router-dom";

export default function PaymentSuccess() {
  const [params] = useSearchParams();
  const bookingId = params.get("bookingId");
  const paymentId = params.get("paymentId");

  return (
    <div style={{padding:20}}>
      <h2>Booking Confirmed 🎉</h2>
      <p>Your payment was successful.</p>
      <p><strong>Booking ID:</strong> {bookingId}</p>
      <p><strong>Payment ID:</strong> {paymentId}</p>
      <Link to="/">Go to Home</Link>
    </div>
  );
}
