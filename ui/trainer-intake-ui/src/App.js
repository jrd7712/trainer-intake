
import { BrowserRouter as Router } from "react-router-dom";
import NavBar from "./NavBar/NavBar";
import AppRoutes from "./AppRoutes";
import "./theme.css";
import Layout from "./layout";



function App() {
  return (
    <Router>
      <Layout>
        <NavBar />
        <AppRoutes />
      </Layout>
    </Router>
  );
}

export default App;