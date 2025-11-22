import React, { useState } from "react";

function WorkoutPlan() {
  const [surveyId, setSurveyId] = useState("");
  const [plan, setPlan] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchPlan = async () => {
    setLoading(true);
    try {
      const response = await fetch(`http://localhost:8080/generate-plan/${surveyId}`);
      const data = await response.json();
      setPlan(data);
    } catch (error) {
      console.error("Error fetching plan:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "2rem", fontFamily: "Arial" }}>
      <h2>AI Workout Plan Generator</h2>
      <input
        type="text"
        placeholder="Enter Survey ID"
        value={surveyId}
        onChange={(e) => setSurveyId(e.target.value)}
      />
      <button onClick={fetchPlan} disabled={!surveyId || loading}>
        {loading ? "Generating..." : "Generate Plan"}
      </button>

      {plan && (
        <div style={{ marginTop: "2rem" }}>
          <h3>Survey {plan.surveyId}</h3>
          <ul>
            {plan.answers.map((a, i) => (
              <li key={i}>{a}</li>
            ))}
          </ul>
          <h3>Workout Plan</h3>
          <pre style={{ background: "#f4f4f4", padding: "1rem" }}>
            {plan.workoutPlan}
          </pre>
        </div>
      )}
    </div>
  );
}

export default WorkoutPlan;