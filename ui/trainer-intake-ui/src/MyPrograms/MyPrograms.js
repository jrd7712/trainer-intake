import React, { useState, useEffect } from "react";
import { apiFetch } from "../api";

function MyPrograms() {
  const [programs, setPrograms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function loadPrograms() {
      try {
        const data = await apiFetch("http://localhost:8080/programs/me");
        setPrograms(data || []);
      } catch (err) {
        console.error("Error loading programs:", err);
        setError(err);
      } finally {
        setLoading(false);
      }
    }
    loadPrograms();
  }, []);

  if (loading) return <p>Loading your programs...</p>;

  return (
    <div style={{ margin: "20px" }}>
      <h2>My Programs</h2>
      {error && <p style={{ color: "red" }}>Failed to load programs.</p>}
      {programs.length === 0 ? (
        <p>No programs found.</p>
      ) : (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {programs.map((p) => (
            <li key={p.id} style={{ marginBottom: "20px", border: "1px solid #ccc", padding: "10px" }}>
              <p><strong>Survey ID:</strong> {p.survey?.surveyId ?? "N/A"}</p>
              <p><strong>Created At:</strong> {p.createdAt ? new Date(p.createdAt).toLocaleString() : "Unknown"}</p>
              <pre style={{ whiteSpace: "pre-wrap" }}>{p.planText}</pre>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default MyPrograms;