<h1 align="center">Task Manager API <kbd>v1.2</kbd></h1>


### v1.2 Updates
While v1.1 was about architectural decoupling, v1.2 is about implementing behavioral design patterns and modernizing the deployment workflow. I introduced the Strategy Pattern to handle complex business rules and fully containerized the environment for seamless scaling.
* **Strategy Design Pattern:** Implemented a dynamic system to handle different task types (Daily, Weekly, Monthly). This ensures the system is easily extendable without modifying existing calculation logic.
* **Dynamic Strategy Factory:** Added a TaskRepeatStrategyFactory that utilizes Spring's dependency injection to resolve the correct execution date strategy at runtime based on the user's request.
* **Rich Domain Model:** Enhanced the Task domain model by moving business logic, such as the complete() method and state validation, into the model itself to prevent anemic domain behavior.
* **Domain-Driven Value Objects:** Introduced TaskTitle as a Value Object to encapsulate title-specific validation rules and invariants directly within the domain layer.
* **Full Containerization:** Added a multi-stage Dockerfile and docker-compose.yml to orchestrate the Spring Boot API and PostgreSQL database, ensuring environment parity across development and production.
* **Robust Error Handling:** Expanded the GlobalExceptionHandler to manage new domain-specific exceptions, including DomainTaskAlreadyExist and InvalidTaskTypeException.

---

### Tech Stack
| Component | Technology |
| :--- | :--- |
| **Backend** | Spring Boot 4.0.2 & Java 17 |
| **Database** | PostgreSQL & Spring Data JPA |
| **Mapping** | MapStruct 1.5.5 |
| **Boilerplate** | Lombok 1.18.42 |
| **API Docs** | SpringDoc OpenAPI (Swagger UI) |
| **Container** | Docker & Docker Compose |

---

### 📂 Project Structure
The project is organized into four functional layers to maintain a strict separation of concerns:

* `presentation`: REST Controllers and global exception handling logic.
* `application`: Service interfaces, DTOs, and the integration of the Strategy Factory.
* `domain`: The core "brain"; contains business models, Value Objects, and strategy interfaces.
* `infrastructure`: Technical implementations including JPA entities, data mappers, and concrete repetition strategies.

---

### Getting Started
1.  **Environment:** Ensure PostgreSQL is running and your `DB_PASSWORD` is set in your environment variables.
2.  **Build & Run:**
    ```bash
    docker-compose up --build
    ```
3.  **Test:** Access the interactive Swagger UI to test the endpoints directly:
    `http://localhost:8080/swagger-ui/index.html`

---
### 📜 Looking for v1.0?
You can find the documentation and code for the previous Layered Architecture version by viewing the [file history](https://github.com/boradra/taskmanager/commit/723a0480c2b517536ec268af4fccaa88102a9e39).
