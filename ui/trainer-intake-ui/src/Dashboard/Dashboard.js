import "./Dashboard.css";

function Dashboard() {
  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Welcome Back</h2>

      <p className="dashboard-subtitle">
        You're logged in and ready to continue your fitness journey.
      </p>

      <div className="dashboard-card">
        <h3>Your Account</h3>
        <p>Access your programs, track progress, and update your preferences.</p>
      </div>

      <button className="logout-btn" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}

export default Dashboard;