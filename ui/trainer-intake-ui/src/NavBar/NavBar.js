import { Link } from "react-router-dom";
import { useCurrentUser } from "../CurrentUser/CurrentUser";
import "./NavBar.css";

function NavBar() {
  const { user, loading } = useCurrentUser();

  return (
    <nav className="navbar">
      <div>
        {!loading && !user && (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}

        <Link to="/dashboard">Dashboard</Link>
        <Link to="/CreateWorkout">Create Workout</Link>
        <Link to="/MyPrograms">MyPrograms</Link>
      </div>

      <div>
        {!loading && user && <>Signed in as {user.firstName}</>}
        {!loading && !user && <>Not signed in</>}
      </div>
    </nav>
  );
}

export default NavBar;