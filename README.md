🏋️‍♂️ Trainer Intake App
A full‑stack survey + AI workout plan generator built with Spring Boot, React, SQL Server, and OpenAI.

📖 Overview
Trainer Intake is a complete intake and workout‑generation platform for personal trainers.
Clients fill out a dynamic, multi‑step survey, and the system generates a personalized AI‑powered workout plan based on their responses.
The backend manages surveys, answers, and plan generation.
The frontend provides a polished, modern UI with a sport‑red design system and smooth page transitions.

## 🛠️ Tech Stack
- **Backend**: Spring Boot, JPA, SQL Server
- **Frontend**: React (Create React App)
- **AI**: OpenAI API (`gpt-4.1-mini`)
- **Database**: SQL Server with tables:
  - `Surveys`
  - `Questions`
  - `Answers`
  - `WorkoutPlans`
---

✨ Features
📝 Dynamic Survey System
• 	Multi‑step survey with progress tracking
• 	Supports:
• 	Text inputs
• 	Multiple‑choice dropdowns
• 	Multiselect checkboxes
• 	Questions loaded dynamically from the backend
• 	Per‑user survey numbering (Survey #1, #2, #3…)
🤖 AI‑Generated Workout Plans
• 	Uses OpenAI to generate structured, personalized workout plans
• 	Plans are stored in SQL Server and tied to the survey
• 	Returned instantly to the frontend after submission

💾 Full Data Persistence
• 	Surveys
• 	Questions
• 	Answers
• 	Workout Plans

All stored in SQL Server with proper relationships.
🎨 Modern React Frontend
• 	Sport‑red design system
• 	Smooth fade‑in animations
• 	Clean checkbox alignment
• 	Progress bar that tracks question completion
• 	“Create Workout” page with dynamic rendering
• 	Displays generated workout plan with formatting preserved

🔐 Secure Backend
• 	Environment‑based OpenAI keys
• 	CORS configured for React dev server
• 	Clean DTOs and serialization
• 	Error‑resistant survey submission flow

🛠️ Tech Stack
Backend
• 	Spring Boot
• 	Java
• 	JPA / Hibernate
• 	SQL Server
• 	OpenAI API

Frontend
• 	React
• 	Modern CSS with global variables
• 	Fetch wrapper (apiFetch)
• 	Component‑based UI
Database Tables
• 	Users
• 	Surveys
• 	Questions
• 	Answers
• 	WorkoutPlans

🚀 Getting Started
🔧 Backend Setup
1. 	Configure application.properties:
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TrainerIntake
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

openai.api.key=${OPENAI_API_KEY}
openai.api.url=https://api.openai.com/v1/chat/completions

2.  Run the backend:
    mvn spring-boot:run


3.  Test the API:
   curl http://localhost:8080/survey/questions

💻 Frontend Setup
1. Navigate to the UI folder:
   cd trainer-intake-ui
2. Install dependencies:
   npm install
4. Start the dev server:
  npm start
5. Open:
   http://localhost:3000

🧪 How the Workflow Operates
1. 	React loads survey questions from 
2. 	User completes the multi‑step survey
3. 	React submits answers to 
4. 	Backend:
• 	Creates a new survey
• 	Saves answers
• 	Generates AI workout plan
• 	Stores the plan
• 	Returns  + 
5. 	React displays the generated plan beautifully

🧱 Development Notes
During development we resolved:
• 	Identity column issues → fixed by letting SQL Server auto‑generate IDs
• 	Unique constraint issues → prevented duplicate prompts
• 	Foreign key issues → ensured surveys exist before inserting answers
• 	Null constraint issues → required user creation before survey creation
• 	CORS issues → enabled React dev server access
• 	JSON parsing issues → ensured multiselect choices stored as valid JSON arrays

🗺️ Roadmap
• 	Add authentication (JWT)
• 	Add trainer dashboard
• 	Add client accounts
• 	Add plan editing + regeneration
• 	Deploy backend + frontend together
• 	Add analytics for survey responses



