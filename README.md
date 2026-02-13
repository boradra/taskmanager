<h1>Task Manager API (v1.0)</h1>

<h2>Description</h2>
<p>This project is my <b>first comprehensive deep-dive</b> into the Java and Spring Boot ecosystem. While it serves as a "first draft," my goal was to move beyond basic tutorials and implement a structured architecture. It is a Task Management REST API designed to practice clean code principles, layered architecture, and modern backend integration techniques.</p>

<h2>What I Learned & Implemented</h2>
<p>During the development of this initial version, I focused on mastering the following core backend concepts:</p>
<ul>
    <li><b>Layered Architecture:</b> I organized the code into distinct layers—Controller, Service, and Repository—to ensure a clear separation of concerns.</li>
    <li><b>DTO Pattern & Data Mapping:</b> To protect the integrity of database entities, I implemented the DTO (Data Transfer Object) pattern. I used MapStruct to handle high-performance object mapping between layers.</li>
    <li><b>Centralized Exception Handling:</b> I built a global error management system using <code>@ControllerAdvice</code> to provide consistent and user-friendly error responses across the entire API.</li>
    <li><b>Data Validation:</b> I ensured data integrity by using Jakarta Bean Validation to validate user input before it reaches the business logic.</li>
    <li><b>Secure Configuration:</b> I learned how to handle sensitive information by moving database credentials to <b>Environment Variables</b>, following best practices for managing externalized configuration.</li>
</ul>

<h2>Tech Stack</h2>
<ul>
    <li><b>Spring Boot 4.0.2:</b> The core framework used for application bootstrapping and auto-configuration.</li>
    <li><b><a href="https://spring.io/projects/spring-data-jpa">Spring Data JPA</a> & <a href="https://www.postgresql.org/">PostgreSQL</a>:</b> For robust data persistence and relational database management.</li>
    <li><b><a href="https://mapstruct.org/">MapStruct 1.5.5</a>:</b> For compile-time safe object conversions.</li>
    <li><b><a href="https://projectlombok.org/">Lombok</a>:</b> To reduce boilerplate code like getters, setters, and constructors.</li>
    <li><b><a href="https://springdoc.org/">SpringDoc OpenAPI (Swagger UI)</a>:</b> To provide an interactive interface for testing and documenting the API endpoints.</li>
</ul>

<h2>Project Structure</h2>
<pre><code>.
├── src/main/java/com/example/boradra/taskmanager/
│   ├── controller/      # REST API Endpoints
│   ├── service/         # Business Logic and Interfaces
│   ├── repository/      # Data Access Layer (JPA)
│   ├── entity/          # Database Models
│   ├── dto/             # Data Transfer Objects
│   ├── taskMapper/      # MapStruct Mapping Interfaces
│   └── exception/       # Global Exception Handling Logic
├── src/main/resources/  # application.properties and configurations
├── pom.xml              # Maven Project Object Model
└── README.md            # Project Documentation</code></pre>

<h2>Key Directory Overviews</h2>
<ul>
    <li><a href="./src/main/java/com/example/boradra/taskmanager/controller/"><code>controller/</code></a>: The entry point for HTTP requests, handling the communication between the client and the service layer.</li>
    <li><a href="./src/main/java/com/example/boradra/taskmanager/service/"><code>service/</code></a>: The "brain" of the application where all business rules and task logic are processed.</li>
    <li><a href="./src/main/java/com/example/boradra/taskmanager/taskMapper/"><code>taskMapper/</code></a>: Dedicated interfaces for MapStruct to automate the conversion between Entities and DTOs.</li>
    <li><a href="./src/main/resources/"><code>resources/</code></a>: Contains the <a href="./src/main/resources/application.properties">application.properties</a> file, which manages database connections and environment variable placeholders.</li>
</ul>

<h2>How to Test</h2>
<p>Once the application is running, you can access the interactive <b>Swagger UI</b> to test the endpoints directly from your browser:</p>
<code>http://localhost:8080/swagger-ui/index.html</code>
