<h1 align="center">Task Manager API <kbd>v1.1</kbd></h1>


### v1.1 Updates
While v1.0 was about getting the system running, **v1.1** is about doing it right. I refactored the project to implement a decoupled, layer-based architecture to ensure that business logic stays at the center, independent of external frameworks.
* **Clean Architecture:** Refactored from a traditional Layered Architecture to a Clean Architecture to strictly decouple core business logic from external frameworks and infrastructure.
* **Domain-Driven Isolation:** I moved the core logic into a pure `domain` layer. The `Task` model is now a POJO, completely separated from JPA annotations.
* **Decoupled Infrastructure:** Database logic is now strictly contained within the `infrastructure` layer. The application logic communicates with the database through interfaces, making the core system tech-agnostic.
* **Global Resilience:** The `GlobalExceptionHandler` was upgraded to catch both custom domain errors like `DomainTaskNotFoundException` and Jakarta validation failures.

---

### Tech Stack
| Component | Technology |
| :--- | :--- |
| **Backend** | Spring Boot 4.0.2 & Java 17 |
| **Database** | PostgreSQL & Spring Data JPA |
| **Mapping** | MapStruct 1.5.5 |
| **Boilerplate** | Lombok 1.18.42 |
| **API Docs** | SpringDoc OpenAPI (Swagger UI) |

---

### 📂 Project Structure
The project is organized into four functional layers to maintain a strict separation of concerns:

* `presentation`: REST Controllers and global exception handling logic.
* `application`: Service interfaces, implementations, and DTOs.
* `domain`: The core "brain"; contains business models and repository abstractions.
* `infrastructure`: Technical implementation including JPA entities, mappers, and repository logic.

---

### Getting Started
1.  **Environment:** Ensure PostgreSQL is running and your `DB_PASSWORD` is set in your environment variables.
2.  **Build & Run:**
    ```bash
    ./mvnw spring-boot:run
    ```
3.  **Test:** Access the interactive Swagger UI to test the endpoints directly:
    `http://localhost:8080/swagger-ui/index.html`

---
### 📜 Looking for v1.0?
You can find the documentation and code for the previous Layered Architecture version by viewing the [file history](https://github.com/boradra/taskmanager/commit/723a0480c2b517536ec268af4fccaa88102a9e39).
