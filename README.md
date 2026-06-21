# 🍽️ FoodFlow

## Plataforma de Delivery Basada en Microservicios

FoodFlow es una plataforma de pedidos a domicilio que conecta clientes, restaurantes y repartidores mediante una arquitectura de microservicios desarrollada con Spring Boot.

---

## 👥 Equipo de Desarrollo

| Integrante | Responsabilidades                                 |
| ---------- | ------------------------------------------------- |
| Erick      | Usuario, Restaurante, Menú, Reserva, Calificación |
| Cristofer  | Orden, Delivery, Pago, Promoción, Reporte         |

---

## 📖 Descripción del Proyecto

FoodFlow permite gestionar todo el ciclo de vida de un pedido de comida:

* Registro de usuarios.
* Administración de restaurantes.
* Gestión de menús.
* Reservas de mesas.
* Creación de pedidos.
* Procesamiento de pagos.
* Asignación de repartidores.
* Calificaciones y comentarios.
* Promociones y descuentos.
* Generación de reportes.

La plataforma está diseñada bajo una arquitectura de microservicios para facilitar la escalabilidad, mantenibilidad y desacoplamiento de funcionalidades.

---

## 🎯 Objetivo

Brindar una solución tecnológica que permita a restaurantes administrar sus operaciones digitales mientras los clientes disfrutan de una experiencia simple y eficiente para realizar pedidos y reservas.

---

# 🏗️ Arquitectura del Sistema

La solución está compuesta por:

* 10 Microservicios de negocio.
* Eureka Server para descubrimiento de servicios.
* API Gateway para centralizar las peticiones.
* Bases de datos independientes por microservicio.
* Comunicación síncrona mediante OpenFeign.

---

## 🔄 Flujo Principal

### Gestión de Usuarios y Reservas

1. Registro de usuarios.
2. Registro de restaurantes.
3. Creación de reservas.
4. Validación de existencia de usuario y restaurante mediante Feign.

### Gestión de Pedidos y Entregas

1. Creación de pedido.
2. Procesamiento de pago.
3. Asignación de repartidor.
4. Entrega del pedido.

---

# 🗄️ Microservicios

## Microservicios de Erick

| Servicio        | Puerto | Base de Datos   |
| --------------- | ------ | --------------- |
| usuario-ms      | 8081   | bd_usuario      |
| restaurante-ms  | 8082   | bd_restaurante  |
| menu-ms         | 8083   | bd_menu         |
| reserva-ms      | 8084   | bd_reserva      |
| calificacion-ms | 8085   | bd_calificacion |

## Microservicios de Cristofer

| Servicio     | Puerto | Base de Datos |
| ------------ | ------ | ------------- |
| orden-ms     | 8086   | bd_orden      |
| delivery-ms  | 8087   | bd_delivery   |
| pago-ms      | 8088   | bd_pago       |
| promocion-ms | 8089   | bd_promocion  |
| reporte-ms   | 8090   | bd_reporte    |

---

# 🔧 Tecnologías Utilizadas

## Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* Spring Cloud OpenFeign
* Spring Cloud Gateway
* Eureka Server

## Base de Datos

* MySQL

## DevOps

* Docker
* Docker Compose
* Maven

## Testing

* Postman
* JUnit

---

# 🚀 Instalación y Ejecución

## Requisitos Previos

* Java 17
* Maven
* MySQL
* Docker (opcional)

---

## 1️⃣ Crear Bases de Datos

```sql
CREATE DATABASE bd_usuario;
CREATE DATABASE bd_restaurante;
CREATE DATABASE bd_menu;
CREATE DATABASE bd_reserva;
CREATE DATABASE bd_calificacion;
CREATE DATABASE bd_orden;
CREATE DATABASE bd_delivery;
CREATE DATABASE bd_pago;
CREATE DATABASE bd_promocion;
CREATE DATABASE bd_reporte;
```

---

## 2️⃣ Configurar application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bd_nombre
spring.datasource.username=root
spring.datasource.password=tu_contraseña

server.port=xxxx
```

---

## 3️⃣ Levantar Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
```

Acceso:

```text
http://localhost:8761
```

---

## 4️⃣ Levantar Microservicios

Ejemplo:

```bash
cd usuario-ms
mvn spring-boot:run
```

Repetir para cada microservicio.

---

## 5️⃣ Levantar Gateway

```bash
cd gateway-proxy
mvn spring-boot:run
```

Acceso:

```text
http://localhost:8080
```

---

# 🌐 API Gateway

Todas las solicitudes pasan por el Gateway.

| Ruta Gateway              | Destino         |
| ------------------------- | --------------- |
| /api/v1/usuarios/**       | usuario-ms      |
| /api/v1/restaurantes/**   | restaurante-ms  |
| /api/v1/menu/**           | menu-ms         |
| /api/v1/reservas/**       | reserva-ms      |
| /api/v1/calificaciones/** | calificacion-ms |
| /api/v1/ordenes/**        | orden-ms        |
| /api/v1/deliverys/**      | delivery-ms     |
| /api/v1/pagos/**          | pago-ms         |

Ejemplo:

```http
GET http://localhost:8080/api/v1/restaurantes
```

---

# 📡 Endpoints Principales

## Usuario

### Crear Usuario

```http
POST /api/v1/usuarios
```

### Listar Usuarios

```http
GET /api/v1/usuarios
```

### Buscar Usuario

```http
GET /api/v1/usuarios/{id}
```

---

## Restaurante

### Crear Restaurante

```http
POST /api/v1/restaurantes
```

### Listar Restaurantes

```http
GET /api/v1/restaurantes
```

---

## Reserva

### Crear Reserva

```http
POST /api/v1/reservas
```

Valida existencia de:

* Usuario.
* Restaurante.

Utilizando OpenFeign.

---

# 🔗 Comunicación Entre Microservicios

| Origen          | Destino        | Propósito           |
| --------------- | -------------- | ------------------- |
| reserva-ms      | usuario-ms     | Validar usuario     |
| reserva-ms      | restaurante-ms | Validar restaurante |
| calificacion-ms | restaurante-ms | Actualizar promedio |
| orden-ms        | pago-ms        | Procesar pago       |
| orden-ms        | delivery-ms    | Asignar repartidor  |

---

# 📋 Ejemplos de Peticiones

## Crear Usuario

```json
{
  "nombre": "Juan Perez",
  "email": "juan@example.com",
  "telefono": "912345678",
  "direccion": "Av. Principal 123",
  "rol": "CLIENTE"
}
```

---

## Crear Restaurante

```json
{
  "nombre": "Pizzeria Centro",
  "direccion": "Av. Siempre Viva 123",
  "telefono": "912345678",
  "horarioApertura": "09:00",
  "horarioCierre": "22:00"
}
```

---

## Crear Reserva

```json
{
  "restauranteId": 1,
  "usuarioId": 1,
  "fecha": "2026-05-15",
  "hora": "20:30:00",
  "numeroPersonas": 4,
  "comentarios": "Ventana por favor"
}
```

---

# 📚 Documentación API

Cada microservicio expone Swagger/OpenAPI:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8084/swagger-ui/index.html
http://localhost:8085/swagger-ui/index.html
http://localhost:8086/swagger-ui/index.html
http://localhost:8087/swagger-ui/index.html
http://localhost:8088/swagger-ui/index.html
```

---

# 🧪 Pruebas Unitarias

Ejemplo:

```bash
cd usuario-ms
mvn test
```

Resultado esperado:

```text
BUILD SUCCESS
```

---

# 🐳 Docker

Levantar todo el ecosistema:

```bash
docker-compose up --build
```

Detener servicios:

```bash
docker-compose down
```

---

# 📂 Estructura del Proyecto

```text
FoodFlow
├── usuario-ms
├── restaurante-ms
├── menu-ms
├── reserva-ms
├── calificacion-ms
├── orden-ms
├── delivery-ms
├── pago-ms
├── promocion-ms
├── reporte-ms
├── eureka-server
├── gateway-proxy
├── docker-compose.yml
└── README.md
```

---

# 👨‍💻 Autores

### Erick

* Usuario
* Restaurante
* Menú
* Reserva
* Calificación

### Cristofer

* Orden
* Delivery
* Pago
* Promoción
* Reporte

---

## 📄 Licencia

Proyecto desarrollado con fines académicos para la asignatura de Ingeniería de Software.
