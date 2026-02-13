# MSV-Prueba-VenturesSoft 

## Tecnologías Utilizadas
* **Java 17**.
* **Spring Boot 3.x**
* **Spring Security & JWT**
* **Hibernate/JPA**
* **H2 Database**
* **Docker**

---

## Seguridad y Autenticación
El microservicio utiliza **JSON Web Tokens (JWT)** para proteger los endpoints. 
* **Algoritmo**: HS256.
* **Seguridad**: Se ha implementado un filtro de autenticación que valida el token en cada petición, asegurando que solo usuarios autorizados puedan gestionar la información.
