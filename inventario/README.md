
---

## ✅ `inventario/README.md`

```markdown
# Microservicio Inventario

Este microservicio gestiona el stock de productos. Permite crear inventario, consultar cantidades, actualizarlas y realizar compras. Se comunica con el microservicio de Productos para validar que el producto exista antes de operar.

Este servicio corre en el puerto **8082**.

---

## 📦 Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- API REST
- H2 Database
- Docker
- Comunicación REST entre microservicios

---

## 📌 Endpoints disponibles

| Método | Endpoint                | Descripción                                       |
|--------|-------------------------|---------------------------------------------------|
| GET    | /inventario/{id}        | Consultar cantidad disponible por producto ID     |
| PUT    | /inventario/{id}?cantidad=X | Actualizar cantidad del producto                |
| POST   | /inventario/comprar     | Comprar un producto y descontar stock            |
| POST   | /inventario             | Crear inventario para un producto                |

---

## 🧪 Ejemplos de uso con Postman

### Crear inventario

**POST http://localhost:8082/inventario**

```json
{
  "productoId": 1,
  "cantidad": 10
}