// src/AppRoutes.js
import { Routes, Route, Navigate } from "react-router-dom";
import LoginForm from "./LoginForm/LoginForm";
import RegisterForm from "./RegisterForm/RegisterForm";
import Dashboard from "./Dashboard/Dashboard";
import CreateWorkout from "./CreateWorkout/CreateWorkout";
import Survey from "./Survey/Survey"; // your survey component




function AppRoutes() {
  const token = localStorage.getItem("token");

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
      <Route
        path="/dashboard"
        element={token ? <Dashboard /> : <Navigate to="/login" />}
      />
      <Route path="/CreateWorkout" element={<CreateWorkout />} />
      <Route path="/survey" element={<Survey />} />
    </Routes>
  );
}

export default AppRoutes;