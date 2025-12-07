import React, { useEffect, useState } from "react";
import { apiFetch } from "../api"; // helper with JWT

function CreateWorkout() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [workoutPlan, setWorkoutPlan] = useState(null);
  const [surveyId, setSurveyId] = useState(null);

  // Load survey questions
  useEffect(() => {
    async function loadQuestions() {
      try {
        const data = await apiFetch("http://localhost:8080/survey/questions");
        console.log("Survey questions response:", data);

        const questionArray = Array.isArray(data) ? data : data.questions;
        setQuestions(questionArray || []);
      } catch (err) {
        console.error("Error loading survey questions:", err);
      }
    }
    loadQuestions();
  }, []);

  // Handle input changes
  const handleChange = (questionId, value) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }));
  };

  // Submit answers
  const handleSubmit = async () => {
    try {
      const formattedAnswers = Object.entries(answers).map(([questionId, response]) => ({
        questionId,
        response,
      }));

      const response = await apiFetch("http://localhost:8080/survey/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formattedAnswers),
      });

      console.log("Submit response:", response);

      if (response.surveyId) {
        setSurveyId(response.surveyId);
        setWorkoutPlan(response.workoutPlan); // ✅ just capture the plan text
      }
    } catch (err) {
      console.error("Error submitting survey:", err);
    }
  };

  return (
    <div style={{ margin: "20px" }}>
      <h2>Create Your Workout Plan</h2>

      {questions.length > 0 ? (
        <form>
          {questions.map((q) => (
            <div key={q.questionId} style={{ marginBottom: "15px" }}>
              <label>{q.questionText}</label>
              <input
                type="text"
                value={answers[q.questionId] || ""}
                onChange={e => handleChange(q.questionId, e.target.value)}
              />
            </div>
          ))}
          <button type="button" onClick={handleSubmit}>
            Submit Survey
          </button>
        </form>
      ) : (
        <p>Loading survey...</p>
      )}

      {workoutPlan && (
        <div style={{ marginTop: "20px" }}>
          <h3>Your AI‑Generated Workout Plan</h3>
          <p><strong>Survey ID:</strong> {surveyId}</p>
          <pre style={{ whiteSpace: "pre-wrap" }}>{workoutPlan}</pre> 
          {/* ✅ display the plan text nicely */}
        </div>
      )}
    </div>
  );
}

export default CreateWorkout;