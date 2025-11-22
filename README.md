# Trainer Intake App

## 📖 Overview
Trainer Intake is a Spring Boot + React application that collects client survey data and generates **AI‑powered workout plans** using OpenAI.  
The backend handles survey storage, prompt serialization, and plan persistence. The frontend provides a simple UI for entering survey IDs and viewing generated plans.

---

## ⚙️ Features
- **Survey Management**: Questions and answers stored in SQL Server.
- **AI Integration**: Calls OpenAI securely with environment variables.
- **Plan Persistence**: Saves generated workout plans to `workout_plans` table.
- **Frontend UI**: React app to fetch and display plans by survey ID.
- **CORS Enabled**: Backend configured to allow requests from React dev server.

---

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

## 🚀 Getting Started

### Backend
1. Configure `application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TrainerIntake
   spring.datasource.username=your_user
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   openai.api.key=${OPENAI_API_KEY}
   openai.api.url=https://api.openai.com/v1/chat/completions

2. Run:
    mvn spring-boot:run

3. Test endpoint:
    curl http://localhost:8080/generate-plan/{surveyId}

### Frontend
1. Navigate to trainer-intake-ui

2. Run
    npm start

3. Open http://localhost:3000 and enter a survey ID

### Development Log
During setup we resolved:
• 	Identity column errors () → fixed by letting SQL Server auto‑generate IDs.
• 	Unique constraint errors () → avoided duplicate prompts in .
• 	Foreign key errors () → ensured surveys exist before inserting answers.
• 	Null constraint errors () → created a client before inserting surveys.
• 	CORS issues → added  with  to allow React requests.

### Roadmap- 
- Add /plans/{surveyId} endpoint to fetch saved plans.
- Build full survey form in React.
- Add authentication (JWT).
- Deploy backend + frontend together.
