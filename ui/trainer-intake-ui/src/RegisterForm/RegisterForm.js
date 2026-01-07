import { useState } from "react";
import "./RegisterForm.css";

function RegisterForm() {
  const [username, setUsername] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: username,
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password
        }),
      });

      if (response.ok) {
        alert("Registration successful!");
        window.location.href = "/login";
      } else {
        alert("Registration failed");
      }
    } catch (err) {
      console.error(err);
      alert("Error registering");
    }
  };

  return (
    <form className="register-form" onSubmit={handleRegister}>
      <h2>Register</h2>

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        required
      />

      <input
        type="text"
        placeholder="First Name"
        value={firstName}
        onChange={(e) => setFirstName(e.target.value)}
        required
      />

      <input
        type="text"
        placeholder="Last Name"
        value={lastName}
        onChange={(e) => setLastName(e.target.value)}
        required
      />

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />

      {/* 🔒 Password safety note */}
      <p className="password-note">
        For your safety, please use a unique password that you don’t use for important accounts.
      </p>

      <button type="submit">Register</button>
    </form>
  );
}

export default RegisterForm;