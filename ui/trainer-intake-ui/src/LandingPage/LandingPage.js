import { Link } from "react-router-dom";
import "./LandingPage.css";

export default function LandingPage() {
  return (
    <div className="landing-container">
      {/* Hero Section */}
      <section className="hero">
        <h1 className="hero-title">Build Your Best Self</h1>
        <p className="hero-subtitle">
          Personalized workout plans powered by smart training insights.
        </p>

        <div className="hero-buttons">
          <Link to="/register" className="primary-btn">Get Started</Link>
          <Link to="/login" className="secondary-btn">Login</Link>
        </div>
      </section>

      {/* Features */}
      <section className="features">
        <div className="feature-card">
          <h3>Smart Surveys</h3>
          <p>Tell us your goals and we’ll build a plan that fits your lifestyle.</p>
        </div>

        <div className="feature-card">
          <h3>Custom Workouts</h3>
          <p>Create and track your own workouts with ease.</p>
        </div>

        <div className="feature-card">
          <h3>Progress Tracking</h3>
          <p>Stay motivated with visual progress and weekly summaries.</p>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        © {new Date().getFullYear()} Your Fitness App
      </footer>
    </div>
  );
}