from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/generate-plan", methods=["POST"])
def generate_plan():
    data = request.json
    survey_id = data.get("survey_id")
    answers = data.get("answers", [])
    # Fake AI logic: just echo back a plan
    return jsonify({
        "plan": f"Workout plan for survey {survey_id}: 3x per week, strength + cardio",
        "answers_received": answers
    })

if __name__ == "__main__":
    app.run(port=5000)