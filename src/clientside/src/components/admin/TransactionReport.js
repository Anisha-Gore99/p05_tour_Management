import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TransactionReport = () => {
  const [payments, setPayments] = useState([]);
  const [modeMap, setModeMap] = useState({});

  useEffect(() => {
    // Fetch payments
    axios.get("http://localhost:8083/api/admin/payments")
      .then((response) => {
        console.log("Fetched payments:", response.data);
        setPayments(response.data);
      })
      .catch(error => console.error("Error fetching payments:", error));

    // Fetch payment modes
    axios.get("http://localhost:8083/api/admin/payment-modes")
      .then((response) => {
        const map = {};
        response.data.forEach(mode => {
          map[mode.modeId] = mode.modeName;
        });
        setModeMap(map);
      })
      .catch(error => console.error("Error fetching payment modes:", error));
  }, []);

  return (
    <div className="container mt-4">
      <h2>Transaction Report</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Payment ID</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Method</th>
            <th>Booking ID</th>
          </tr>
        </thead>
        <tbody>
  {payments.map((payment) => (
    <tr key={payment.paymentId}>
      <td>{payment.paymentId}</td>
      <td>{payment.amount}</td>
      <td>{new Date(payment.paymentDate).toLocaleDateString()}</td>
      <td>{payment.modeId?.modeName || 'Unknown'}</td>
      <td>{payment.bookingId}</td>
    </tr>
  ))}
</tbody>

      </table>
    </div>
  );
};

export default TransactionReport;
