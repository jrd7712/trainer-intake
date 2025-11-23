import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import NavBar from "./NavBar/NavBar";
import AppRoutes from "./AppRoutes";

function App() {
  return (
    <Router>
      <NavBar />
      <AppRoutes />
    </Router>
  );
}

export default App;