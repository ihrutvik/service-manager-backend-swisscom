# Service Manager Backend – Swisscom

This is a Spring Boot backend application designed for managing services with fast, in-memory caching and MongoDB persistence.

---

## 📦 Download & Setup

### 1. Clone or Download ZIP

You can either clone this repo or download the ZIP:

#### 🔗 GitHub (Recommended)

```bash
git clone https://github.com/your-username/service-manager-backend.git
cd service-manager-backend
```

#### 📁 Or Download ZIP

1. Download ZIP from GitHub
2. Extract the folder
3. Open terminal inside the extracted folder

---

## ✅ Prerequisites

- Java 17+
- [MongoDB](https://www.mongodb.com/try/download/community) running locally on port `27017`
- macOS/Linux/WSL (for running `run.sh`)
- Maven not required — Maven Wrapper (`./mvnw`) is included

---

## 🚀 Running the Project

### 🔁 Recommended: Use `run.sh`

The script does everything for you:

```bash
chmod +x run.sh
./run.sh
```

✔️ Auto-installs Maven wrapper (if missing)  
✔️ Builds project (skipping tests)  
✔️ Starts the Spring Boot app using `dev` profile  
✔️ Logs output to: `logs/app.log`

> Default Spring profile: `dev`

You can change the profile inside `run.sh`:

```bash
SPRING_PROFILE="dev"
```

---

### 🧱 Manual Option (if not using script)

```bash
# Build the project
./mvnw clean install

# Run with dev profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 🌐 Endpoints (to be available once controller is added)

| Method | Endpoint           | Description          |
|--------|--------------------|----------------------|
| POST   | `/services`        | Create a new service |
| GET    | `/services/{id}`   | Get service by ID    |
| PUT    | `/services/{id}`   | Update service by ID |
| DELETE | `/services/{id}`   | Delete service by ID |

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## ⚙️ Configuration (`application.properties`)

Default MongoDB settings:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/service_manager_db
```

---

## 🧾 Sample JSON – ServiceEntity

```json
{
  "id": "svc001",
  "name": "Customer Onboarding",
  "description": "Handles customer sign-up workflows",
  "owner": "Team ABC"
}
```

---

## 📂 Project Structure

```
.
├── src/
│   ├── main/java/com/swisscom/servicemanager/
│   │   ├── model/ServiceEntity.java
│   │   ├── repository/ServiceRepository.java
│   │   ├── service/ServiceManager.java
│   │   ├── service/ServiceCache.java
│   │   └── exception/ServiceNotFoundException.java
│   └── resources/application.properties
├── run.sh
├── logs/ (auto-created)
└── README.md
```

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Data MongoDB
- Lombok
- SLF4J + Logback
- ConcurrentHashMap (for in-memory cache)

---

## 🧑‍💻 Author

**Hrutvik Nagrale**  
GitHub: [@ihrutvik](https://github.com/ihrutvik)  
LinkedIn: [hrutvik-nagrale](https://linkedin.com/in/hrutvik-nagrale)

---
