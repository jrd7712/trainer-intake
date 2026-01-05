import "./Dashboard.css";

function Dashboard() {
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Welcome to your Dashboard</h2>

      <div className="dashboard-token-box">
        <strong>Your token:</strong>
        <br />
        {token}
      </div>

      <button className="logout-btn" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}

export default Dashboard;