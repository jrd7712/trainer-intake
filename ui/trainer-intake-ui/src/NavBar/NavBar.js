// src/NavBar.jsx
import { Link } from "react-router-dom";

function NavBar() {
  return (
    <nav style={{ padding: "10px", background: "#eee" }}>
      <Link to="/login" style={{ marginRight: "10px" }}>Login</Link>
      <Link to="/register" style={{ marginRight: "10px" }}>Register</Link>
      <Link to="/dashboard"style={{ marginRight: "10px" }}>Dashboard</Link>
      <Link to="/CreateWorkout" style={{ marginRight: "10px" }}>Create Workout</Link>
    </nav>
  );
}

export default NavBar;