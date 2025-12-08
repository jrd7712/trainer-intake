import React, { useEffect, useState } from "react";
import { apiFetch } from "../api"; // helper with JWT

function CreateWorkout() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [workoutPlan, setWorkoutPlan] = useState(null);
  const [surveyId, setSurveyId] = useState(null);
  const [currentIndex, setCurrentIndex] = useState(0); // ✅ track which question is shown

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
        setWorkoutPlan(response.workoutPlan); // ✅ capture the plan text
      }
    } catch (err) {
      console.error("Error submitting survey:", err);
    }
  };

  // Navigation
  const goNext = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const goPrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  return (
    <div style={{ margin: "20px" }}>
      <h2>Create Your Workout Plan</h2>

      {questions.length > 0 ? (
        <form>
          {/* ✅ Show only the current question */}
          <div style={{ marginBottom: "15px" }}>
            <h3 style={{ color: "#555" }}>
              {questions[currentIndex].section}
            </h3>
            <label>{questions[currentIndex].questionText}</label>
            <input
              type="text"
              value={answers[questions[currentIndex].questionId] || ""}
              onChange={e => handleChange(questions[currentIndex].questionId, e.target.value)}
            />
          </div>

          {/* ✅ Navigation arrows */}
          <div style={{ marginTop: "10px" }}>
            <button type="button" onClick={goPrev} disabled={currentIndex === 0}>
              ← Previous
            </button>
            {currentIndex < questions.length - 1 ? (
              <button type="button" onClick={goNext}>
                Next →
              </button>
            ) : (
              <button type="button" onClick={handleSubmit}>
                Submit Survey
              </button>
            )}
          </div>

          <p>
            Question {currentIndex + 1} of {questions.length}
          </p>
        </form>
      ) : (
        <p>Loading survey...</p>
      )}

      {workoutPlan && (
        <div style={{ marginTop: "20px" }}>
          <h3>Your AI‑Generated Workout Plan</h3>
          <p><strong>Survey ID:</strong> {surveyId}</p>
          <pre style={{ whiteSpace: "pre-wrap" }}>{workoutPlan}</pre>
        </div>
      )}
    </div>
  );
}

export default CreateWorkout;