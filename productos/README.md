API Productos - Spring Boot + JWT

Este proyecto es una API REST para la gestión de productos. Incluye autenticación mediante JWT, control de acceso a endpoints y pruebas unitarias.

Tecnologías utilizadas

\- Java 17

\- Spring Boot 3+

\- Spring Security

\- JWT (JSON Web Token)

\- Maven

\- JUnit y Mockito para testing

&nbsp;Requisitos

\- JDK 17+

\- Maven 3.8+

\- Postman (opcional, para probar)

&nbsp;Levantar el proyecto

1\. Clonar el repositorio:

```bash

git clone https://github.com/usuario/productos-api.git

cd productos-api

```

2\. Construir el proyecto:

```bash

mvn clean install

```

3\. Ejecutar:

```bash

mvn spring-boot:run

```

La aplicación quedará disponible en `http://localhost:8081`

&nbsp;Autenticación

Este proyecto usa JWT para proteger sus endpoints. Para autenticarte:



\### Endpoint de Login



`POST /auth/login`



\*\*Request:\*\*

```json

{

&nbsp; "username": "admin",

&nbsp; "password": "admin123"

}

```



\*\*Response:\*\*

```json

{

&nbsp; "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."

}

```



Este token se debe incluir en los siguientes requests usando el header:

```http

Authorization: Bearer <token>

```

&nbsp;Endpoints públicos (No requieren token)

\- `POST /auth/login`

\- `GET /productos/{id}`

\- `PUT /productos/{id}`

&nbsp;Endpoints protegidos (Requieren token)

\### `GET /productos`

Obtiene todos los productos.



\*\*Header:\*\*

```http

Authorization: Bearer <token>

```



\*\*Response:\*\*

```json

\[

&nbsp; {

&nbsp;   "id": 1,

&nbsp;   "nombre": "Camiseta",

&nbsp;   "precio": 20.5

&nbsp; }

]

```



---



\### `POST /productos`

Crea un nuevo producto.



\*\*Header:\*\*

```http

Authorization: Bearer <token>

```



\*\*Body:\*\*

```json

{

&nbsp; "nombre": "Zapatos",

&nbsp; "precio": 50.0

}

```



---



\### `DELETE /productos/{id}`

Elimina un producto por su ID.



\*\*Header:\*\*

```http

Authorization: Bearer <token>

```

&nbsp;Pruebas unitarias

Puedes ejecutar los tests con:

```bash

mvn test

```

&nbsp;Contacto

Desarrollado por William Sneider Bernal Gil

Email: williambernalcorredoresonline@gmail.com



