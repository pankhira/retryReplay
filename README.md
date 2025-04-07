A robust and scalable framework built using Spring Boot 3 (Java 17) and React to ensure reliability in business operations through automated retry scheduling, intelligent replay handling, and a secure, user-friendly interface.

🚀 Features
✅ Retry Mechanism
Quartz Job Scheduler for retry job execution.

Configurable retry attempts and intervals.

Supports multiple retry strategies:

Fixed Interval

Exponential Backoff

Jitter

Circuit Breaker

Optional Kafka integration for event-driven retries.

🔁 Replay Process
Manual transaction replay from UI.

Validates system context during replay.

Supports both stateful and stateless transaction replays.

Configurable scope and timing (immediate/scheduled).

🔐 User Authentication & Access Control
Role-based authentication using Spring Security.

Admin and User roles with specific privileges.

Session management and filters for secure API access.

📊 Monitoring & Dashboard
React-based dashboard for:

Monitoring transaction status.

Triggering manual retries/replays.

Viewing logs and reports.

📬 Email Notification
Integrated with JavaMailSender and SMTP.

Sends email alerts on retry/replay events.

Customizable templates.

🧾 Logging
Structured logging with correlation IDs.

Logs every retry/replay event with status.

Useful for debugging and audits.

🛠️ Tech Stack
Backend
Java 17

Spring Boot 3

Spring Security

Quartz Scheduler

JavaMailSender (SMTP)

SLF4J / Logback Logging

H2 / MySQL (configurable)

Frontend
React

Axios

Bootstrap / Tailwind CSS

📁 Project Structure (Backend)
bash
Copy
Edit
com.chubb
│
├── config             # Spring Security, Quartz Configs
├── controller         # REST Controllers
├── entity             # JPA Entities: RetryMetadata, ReplayLog, User
├── repository         # JPA Repositories
├── service            # Retry, Replay, Email services
├── serviceImpl        # Business logic implementations
└── RetryReplayApp.java

🧪 How to Run
Prerequisites
Java 17

Maven

Node.js (for frontend)

SMTP credentials for email

Backend
bash
Copy
Edit
git clone https://github.com/yourusername/retry-replay-framework.git
cd backend
./mvnw spring-boot:run
Frontend
bash
Copy
Edit
cd frontend
npm install
npm start
📬 Environment Configuration (application.properties)
properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/retrydb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
🧑‍💼 Default User Setup
You must insert an admin or user manually into the users table with hashed password and roles:

sql
Copy
Edit
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$...'); -- BCrypt hashed password
INSERT INTO user_role (user_id, role) VALUES (1, 'ADMIN');
Use the password encoder in SecurityConfig to hash your password.


