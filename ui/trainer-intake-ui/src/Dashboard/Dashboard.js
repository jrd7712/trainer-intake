import React, { useRef } from "react";

function Dashboard() {
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login"; // redirect after logout
  };

  return (
    <div>
      <h2>Welcome to your Dashboard</h2>
      <p>Your token: {token}</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Dashboard;