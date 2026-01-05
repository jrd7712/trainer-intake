import React, { useEffect, useState } from "react";
import { apiFetch } from "../api";
import ProgressBar from "../ProgressBar/ProgressBar";
import "./CreateWorkout.css";

function CreateWorkout() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [workoutPlan, setWorkoutPlan] = useState(null);
  const [surveyId, setSurveyId] = useState(null);
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    async function loadQuestions() {
      try {
        const data = await apiFetch("http://localhost:8080/survey/questions");
        const questionArray = Array.isArray(data) ? data : data.questions;
        setQuestions(questionArray || []);
      } catch (err) {
        console.error("Error loading survey questions:", err);
      }
    }
    loadQuestions();
  }, []);

  const handleChange = (questionId, value) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }));
  };

  const handleMultiSelectChange = (questionId, choice) => {
    setAnswers(prev => {
      const existing = prev[questionId] || [];
      return existing.includes(choice)
        ? { ...prev, [questionId]: existing.filter(c => c !== choice) }
        : { ...prev, [questionId]: [...existing, choice] };
    });
  };

  const handleSubmit = async () => {
    try {
      const formattedAnswers = Object.entries(answers).map(
        ([questionId, response]) => ({
          questionId,
          response: Array.isArray(response) ? response.join(", ") : response
        })
      );

      const response = await apiFetch("http://localhost:8080/survey/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formattedAnswers)
      });

      if (response.surveyId) {
        setSurveyId(response.surveyId);
        setWorkoutPlan(response.workoutPlan);
      }
    } catch (err) {
      console.error("Error submitting survey:", err);
    }
  };

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

  const progress =
    questions.length > 0 ? (currentIndex + 1) / questions.length : 0;

  return (
    <div className="create-workout-container">
      <h2 className="create-workout-title">Create Your Workout Plan</h2>

      {questions.length > 0 ? (
        <form>
          <div className="question-section">
            <h3>{questions[currentIndex].section}</h3>
            <label>{questions[currentIndex].questionText}</label>

            {/* Dynamic Input */}
            {(() => {
              const q = questions[currentIndex];
              const inputType = q.inputType;
              const choices = q.choices;

              // MULTIPLE CHOICE
              if (inputType === "multiple_choice" && Array.isArray(choices)) {
                return (
                  <select
                    value={answers[q.questionId] || ""}
                    onChange={e => handleChange(q.questionId, e.target.value)}
                  >
                    <option value="">Select an option</option>
                    {choices.map((choice, i) => (
                      <option key={i} value={choice}>
                        {choice}
                      </option>
                    ))}
                  </select>
                );
              }

              // MULTISELECT
              if (inputType === "multiselect" && Array.isArray(choices)) {
                const selected = answers[q.questionId] || [];
                return (
                  <div className="checkbox-list">
                    {choices.map((choice, i) => (
                      <label key={i}>
                        <input
                          type="checkbox"
                          checked={selected.includes(choice)}
                          onChange={() =>
                            handleMultiSelectChange(q.questionId, choice)
                          }
                        />
                        {choice}
                      </label>
                    ))}
                  </div>
                );
              }

              // DEFAULT TEXT INPUT
              return (
                <input
                  type="text"
                  value={answers[q.questionId] || ""}
                  onChange={e => handleChange(q.questionId, e.target.value)}
                />
              );
            })()}
          </div>

          <div className="nav-buttons">
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

          <p className="progress-text">
            Question {currentIndex + 1} of {questions.length}
          </p>
        </form>
      ) : (
        <p>Loading survey...</p>
      )}

      {workoutPlan && (
        <div className="workout-plan-box">
          <h3>Your AI‑Generated Workout Plan</h3>

          {surveyId && (
            <div className="survey-id-box">Survey ID: {surveyId}</div>
          )}

          <pre className="workout-plan-text">{workoutPlan}</pre>
        </div>
      )}

      {!workoutPlan && <ProgressBar progress={progress} />}
    </div>
  );
}

export default CreateWorkout;