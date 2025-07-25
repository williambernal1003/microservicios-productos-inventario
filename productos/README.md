# Microservicio Productos

Este microservicio gestiona el catálogo de productos: permite crearlos, listarlos, actualizarlos, eliminarlos y obtenerlos por ID. Se ejecuta en el puerto **8081** y está diseñado para ser consumido mediante herramientas como Postman o desde otros microservicios (como el de Inventario).

---

## 📦 Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- API REST
- H2 Database
- Docker

---

## 📌 Endpoints disponibles

| Método | Endpoint             | Descripción                              |
|--------|----------------------|------------------------------------------|
| GET    | /productos           | Listar todos los productos               |
| GET    | /productos/{id}      | Obtener un producto por su ID            |
| POST   | /productos           | Crear un nuevo producto                  |
| PUT    | /productos/{id}      | Actualizar un producto existente         |
| DELETE | /productos/{id}      | Eliminar un producto                     |

---

## 🧪 Ejemplos de uso con Postman

### Crear producto

**POST http://localhost:8081/productos**

```json
{
  "nombre": "Teclado Mecánico",
  "descripcion": "Teclado RGB con switches rojos",
  "precio": 150000
}