import React, { useState, useEffect } from "react";
import { apiFetch } from "../api";
import "./MyPrograms.css";
import { exportToPDF } from "../utils/exportToPDF";
import { exportToWord } from "../utils/exportToWord";

function MyPrograms() {
  const [programs, setPrograms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      setLoading(false);
      return;
    }

    let cancelled = false;

    const loadPrograms = async () => {
      try {
        const data = await apiFetch("http://localhost:8080/programs/me");
        if (!cancelled) {
          setPrograms(Array.isArray(data) ? data : []);
        }
      } catch (err) {
        if (!cancelled) {
          console.error("Error loading programs:", err);
          setError(err);
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    };

    loadPrograms();

    return () => {
      cancelled = true;
    };
  }, []);

  const deleteProgram = async (id) => {
    try {
      await apiFetch(`http://localhost:8080/programs/${id}`, {
        method: "DELETE",
      });

      setPrograms((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      console.error("Error deleting program:", err);
      alert("Failed to delete program.");
    }
  };

  if (loading) return <p>Loading your programs...</p>;

  return (
    <div className="myprograms-container">
      <h2 className="myprograms-title">My Programs</h2>

      {error && (
        <p className="myprograms-error">
          Failed to load programs: {error.message}
        </p>
      )}

      {programs.length === 0 ? (
        <p>No programs found.</p>
      ) : (
        <ul className="program-list">
          {programs.map((p) => (
            <li key={p.id} className="program-card">
              <div className="program-card-header">
                <div className="program-info">
                  <p>
                    <strong>Survey #:</strong> {p.surveyNumber ?? "N/A"}
                  </p>
                  <p>
                    <strong>Created At:</strong>{" "}
                    {p.createdAt
                      ? new Date(p.createdAt).toLocaleString()
                      : "Unknown"}
                  </p>
                </div>

                <button
                  className="delete-btn"
                  onClick={() => deleteProgram(p.id)}
                >
                  Delete
                </button>
              </div>

              <pre className="program-plan">{p.planText}</pre>

              {/* Download buttons */}
              <div className="download-buttons">
                <button
                  className="download-btn"
                  onClick={() => exportToPDF(p)}
                >
                  Download PDF
                </button>

                <button
                  className="download-btn"
                  onClick={() => exportToWord(p)}
                >
                  Download Word
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default MyPrograms;