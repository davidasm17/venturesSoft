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

## Despliegue con Docker

Este proyecto está completamente dockerizado. La imagen oficial se encuentra alojada en **Docker Hub**

### Requisitos previos
* Docker instalado.

### Pasos para ejecutar la aplicación

1. **Descargar y ejecutar la imagen:**
   Ejecuta el siguiente comando en tu terminal para descargar la imagen y levantar el contenedor en el puerto `8081`:

   ```bash
   docker run -d -p 8081:8081 --name api-ventures davidasm17/msv-venturessoft:1.0.0

Una vez ejecutado el contenedor, el Swagger estará disponible en: http://localhost:8081/swagger-ui/index.html

