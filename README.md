# 📌 Task Manager API

Backend REST API desarrollada con **Spring Boot** para la gestión de tareas de usuarios. El proyecto implementa autenticación JWT, arquitectura en capas y buenas prácticas de desarrollo backend.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Tokens)
- Hibernate
- PostgreSQL / MySQL
- Maven
- Git

---

## 🧱 Arquitectura del proyecto

El proyecto sigue una arquitectura en capas:

- **Controller** → Endpoints REST
- **Service** → Lógica de negocio
- **Repository** → Acceso a datos
- **Entity** → Modelos de base de datos
- **DTO** → Transferencia de datos
- **Security** → Configuración de autenticación JWT
- **Exception** → Manejo global de errores

---

## 🔐 Funcionalidades principales

- Registro de usuarios
- Login con autenticación JWT
- CRUD completo de tareas
- Asignación de tareas a usuarios
- Filtrado de tareas por estado
- Validación de datos
- Manejo global de errores
- API REST estructurada

---

## 📦 Endpoints principales

### Auth
- POST /auth/register → Registro de usuario
- POST /auth/login → Inicio de sesión

### Tasks
- GET    /api/users/{userId}/tasks
- POST   /api/users/{userId}/tasks
- GET    /api/users/{userId}/tasks/{taskId}
- PUT    /api/users/{userId}/tasks/{taskId}
- DELETE /api/users/{userId}/tasks/{taskId}

---

## 🧪 Cómo ejecutar el proyecto

### 1. Clonar repositorio

```bash
git clone https://github.com/MiguelReiriz/TaskManager.git