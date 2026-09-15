# ms-pedidos360-catalog

Microservicio de **Catálogo** de Pedidos360: CRUD de productos, precios y stock. No es público: lo invocan el BFF y ms-pedidos360-orders.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/catalog/products` | Listar productos |
| GET | `/api/catalog/products/{id}` | Detalle |
| POST | `/api/catalog/products` | Crear |
| PUT | `/api/catalog/products/{id}` | Actualizar |
| DELETE | `/api/catalog/products/{id}` | Eliminar |
| POST | `/api/catalog/products/decrease-stock` | Interno (orders): descuenta stock al aceptar un pedido; `409` si no alcanza |

Swagger: `http://localhost:8082/swagger-ui.html`

Al iniciar con la base vacía se cargan 4 productos de ejemplo (`DataSeeder`).

## Base de datos

MySQL (Amazon RDS en AWS, Docker en local). Schema `pedidos360_catalog`, tabla `products` creada por JPA.

| Variable | Por defecto |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | `root` |

## Ejecutar

```bash
./mvnw test
./mvnw spring-boot:run
```

## Autores

Germán Maraboli & Camila Vera

Proyecto Pedidos360 · DSY1107 Desarrollo Cloud Native I · Duoc UC
