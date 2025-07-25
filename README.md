# 🔧 Microservicios Productos & Inventario – Prueba Técnica Linktic

Este proyecto está conformado por dos microservicios construidos con Spring Boot: **Productos** e **Inventario**. Ambos se comunican entre sí y se pueden levantar simultáneamente usando Docker y `docker-compose`.

---

## 📦 Requisitos para la ejecución

- Docker  
- Docker Compose  
- JDK 17+  
- Maven 3.8+  
- Navegador web o Postman (para probar endpoints)  
- Git (opcional para clonar el proyecto)  

---

## 📂 Estructura del proyecto

```
.
├── docker-compose.yml
├── productos/
│   ├── Dockerfile
│   └── (código del microservicio Productos)
└── inventario/
    ├── Dockerfile
    └── (código del microservicio Inventario)
```

---

## 🚀 Cómo levantar los microservicios

1. Clona el repositorio con ambos microservicios y el archivo `docker-compose.yml`.

```bash
git clone https://github.com/usuario/repositorio-prueba.git
cd repositorio-prueba
```

2. Ejecuta el siguiente comando para construir y levantar los contenedores:

```bash
docker-compose up --build
```

Esto construirá y levantará los servicios en red, exponiéndolos en los siguientes puertos:

- 🧱 `productos`: http://localhost:8081  
- 📦 `inventario`: http://localhost:8082  

---

## 🔗 Comunicación entre microservicios

El microservicio **Productos** consume servicios del microservicio **Inventario** mediante un cliente **Feign**. La red `microservicios_net` definida en `docker-compose` permite que los contenedores se comuniquen entre sí por nombre de servicio (ej: `inventario:8082`).

---

## 🧾 Contenido del archivo `docker-compose.yml`

```yaml
version: '3.8'

services:
  productos:
    build:
      context: ./productos
      dockerfile: Dockerfile
    ports:
      - "8081:8081"
    depends_on:
      - inventario
    networks:
      - microservicios_net

  inventario:
    build:
      context: ./inventario
      dockerfile: Dockerfile
    ports:
      - "8082:8082"
    networks:
      - microservicios_net

networks:
  microservicios_net:
    driver: bridge
```

---

## ✅ Verificación

Puedes acceder a la documentación Swagger de cada servicio:

- [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) – Microservicio Productos  
- [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html) – Microservicio Inventario  
