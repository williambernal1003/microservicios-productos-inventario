 API Inventario - Spring Boot
Este proyecto es una API REST para la gestión del inventario de productos. 
Permite crear registros de inventario, consultar la cantidad disponible por producto, 
actualizar stock y procesar compras.

 Implementa documentación Swagger y es compatible con herramientas como Postman.
 Utiliza una base de datos en memoria H2 solo para fines de prueba técnica.
 El proyecto corre sobre el puerto 8082.

 Tecnologías utilizadas
- Java 17
- Spring Boot 3+
- Spring Web
- Spring Validation
- Swagger (OpenAPI)
- Base de datos H2 (en memoria)

 Requisitos
- JDK 17+
- Maven 3.8+
- Postman (opcional)

 Levantar el proyecto
1. Clonar el repositorio:
   git clone https://github.com/usuario/inventario-api.git
   cd inventario-api

2. Ejecutar con Maven:
   mvn spring-boot:run

3. Acceder a Swagger:
   http://localhost:8082/swagger-ui/index.html

 Seguridad
Este proyecto no implementa autenticación JWT. Todos los endpoints están públicos durante la prueba.
 Endpoints disponibles
1.  Consultar inventario por producto ID
   - Método: GET
   - URL: /inventario/{id}
   - Requiere token:  No
   - Ejemplo:
     GET http://localhost:8082/inventario/1

2.  Actualizar cantidad en inventario
   - Método: PUT
   - URL: /inventario/{id}?cantidad=30
   - Requiere token:  No
   - Ejemplo:
     PUT http://localhost:8082/inventario/1?cantidad=20

3.  Procesar compra
   - Método: POST
   - URL: /inventario/comprar
   - Requiere token:  No
   - Content-Type: application/json
   - RequestBody:
     {
       "productoId": 1,
       "cantidad": 2
     }

4.  Crear inventario nuevo
   - Método: POST
   - URL: /inventario
   - Requiere token:  No
   - Content-Type: application/json
   - RequestBody:
     {
       "productoId": 1,
       "cantidad": 100
     }

 Manejo de errores
Este proyecto implementa un controlador global de excepciones que captura y formatea errores como:
- Validación de campos (400 Bad Request)
- Recurso no encontrado (404 Not Found)
- Error interno (500 Internal Server Error)

 Pruebas y validaciones
Se recomienda usar Postman o Swagger UI para probar todos los servicios.
