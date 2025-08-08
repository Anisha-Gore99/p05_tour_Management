import React from 'react';

const GenerateReport = () => {
  const handleDownload = () => {
    fetch("http://localhost:8083/api/admin/report")
      .then((response) => {
        if (response.ok) {
          return response.blob();
        }
        throw new Error("Failed to generate report");
      })
      .then((blob) => {
        const url = window.URL.createObjectURL(new Blob([blob]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'payments_report.csv');
        document.body.appendChild(link);
        link.click();
        link.remove();
      })
      .catch((err) => {
        console.error("Error:", err);
        alert("Failed to generate report.");
      });
  };

  return (
    <div className="container mt-4">
      <h3>Generate Transaction Report</h3>
      <button onClick={handleDownload} className="btn btn-primary">Download CSV Report</button>
    </div>
  );
};

export default GenerateReport;
