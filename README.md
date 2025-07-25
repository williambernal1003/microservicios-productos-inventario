Microservicios Productos & Inventario
Este proyecto está conformado por dos microservicios construidos con Spring Boot: el microservicio de Productos y el microservicio de Inventario. Ambos se comunican entre sí y se pueden levantar de forma simultánea mediante Docker y un archivo docker-compose.yml.
 Requisitos
- Docker
- Docker Compose
- JDK 17+
- Maven 3.8+
 Estructura del proyecto

.
├── docker-compose.yml
├── productos/
│   ├── Dockerfile
│   └── (código del microservicio Productos)
└── inventario/
    ├── Dockerfile
    └── (código del microservicio Inventario)

 Cómo levantar los microservicios
1. Clona el repositorio con ambos microservicios y el archivo docker-compose.yml.
2. Navega hasta la raíz donde se encuentra el archivo docker-compose.yml.
3. Ejecuta el siguiente comando para construir y levantar los contenedores:
docker-compose up --build
Esto construirá y levantará los servicios en red, exponiéndolos en los siguientes puertos:
- productos: http://localhost:8081
- inventario: http://localhost:8082
 Comunicación entre microservicios
El microservicio de Productos consume servicios del microservicio Inventario a través de un cliente Feign. La red "microservicios_net" definida en docker-compose permite que los contenedores se comuniquen por nombre de servicio (ej: inventario:8082).
 Contenido del archivo docker-compose.yml
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

 Verificación
Puedes verificar que los servicios estén corriendo visitando:
- http://localhost:8081/swagger-ui.html
- http://localhost:8082/swagger-ui.html
