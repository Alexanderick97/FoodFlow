# 🍽️ FoodFlow - Plataforma de Delivery

## 👥 Integrantes

* **Erick** - Usuario, Restaurante, Menú, Reserva, Calificación
* **Cristofer** - Orden, Delivery, Pago, Promoción, Reporte

---

## 📖 Descripción del Proyecto

**FoodFlow** es una plataforma de pedidos a domicilio que conecta clientes, restaurantes y repartidores. Permite gestionar pedidos en tiempo real, asignar repartidores, procesar pagos y generar reportes de ventas.

---

## 🗄️ Microservicios (10)

### 🔹 Microservicios de Erick

| Microservicio   | Puerto | Base de Datos   | Descripción                                          |
| --------------- | ------ | --------------- | ---------------------------------------------------- |
| usuario-ms      | 8081   | bd_usuario      | Gestión de usuarios (clientes, repartidores, admins) |
| restaurante-ms  | 8082   | bd_restaurante  | Gestión de restaurantes                              |
| menu-ms         | 8083   | bd_menu         | Gestión de platos y menús                            |
| reserva-ms      | 8084   | bd_reserva      | Reservas de mesas                                    |
| calificacion-ms | 8085   | bd_calificacion | Calificaciones y comentarios                         |

### 🔹 Microservicios de Cristofer

| Microservicio | Puerto | Base de Datos | Descripción                |
| ------------- | ------ | ------------- | -------------------------- |
| orden-ms      | 8086   | bd_orden      | Gestión de pedidos         |
| delivery-ms   | 8087   | bd_delivery   | Asignación de repartidores |
| pago-ms       | 8088   | bd_pago       | Procesamiento de pagos     |
| promocion-ms  | 8089   | bd_promocion  | Cupones y descuentos       |
| reporte-ms    | 8090   | bd_reporte    | Estadísticas y reportes    |

---

## 🔧 Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 4.0.6**
* **Spring Data JPA / Hibernate**
* **MySQL**
* **OpenFeign** (comunicación entre microservicios)
* **Maven**
* **Postman** (pruebas de API)

---

## 🚀 Cómo Ejecutar el Proyecto

### ✅ Prerrequisitos

* Java 17 instalado
* MySQL en ejecución
* Maven instalado (opcional, puedes usar el wrapper `mvnw`)

---

### 🧩 Paso 1: Crear las bases de datos

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

### ⚙️ Paso 2: Configurar `application.properties`

En cada microservicio:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bd_nombre
spring.datasource.username=root
spring.datasource.password=tu_contraseña
server.port=xxxx
```

---

### ▶️ Paso 3: Ejecutar los microservicios

```bash
# Ejemplo
cd usuario-ms
mvn spring-boot:run

# Repetir para cada microservicio
```

---

## 📡 Endpoints Principales

### 🔹 usuario-ms (Puerto 8081)

| Método | Endpoint              | Descripción     |
| ------ | --------------------- | --------------- |
| POST   | /api/v1/usuarios      | Crear usuario   |
| GET    | /api/v1/usuarios      | Listar usuarios |
| GET    | /api/v1/usuarios/{id} | Buscar por ID   |

---

### 🔹 restaurante-ms (Puerto 8082)

| Método | Endpoint             | Descripción         |
| ------ | -------------------- | ------------------- |
| POST   | /api/v1/restaurantes | Crear restaurante   |
| GET    | /api/v1/restaurantes | Listar restaurantes |

---

### 🔹 reserva-ms (Puerto 8084)

| Método | Endpoint         | Descripción                                            |
| ------ | ---------------- | ------------------------------------------------------ |
| POST   | /api/v1/reservas | Crear reserva (valida usuario y restaurante vía Feign) |

---

## 🔗 Comunicación entre Microservicios

* `reserva-ms` → `usuario-ms` (valida que el usuario exista)
* `reserva-ms` → `restaurante-ms` (valida que el restaurante exista)
* `orden-ms` → `usuario-ms` y `restaurante-ms` (validaciones)

---

## 📋 Ejemplos de Peticiones (Postman)

### 👤 Crear Usuario

```json
POST http://localhost:8081/api/v1/usuarios
{
  "nombre": "Juan Perez",
  "email": "juan@example.com",
  "telefono": "912345678",
  "direccion": "Av. Principal 123",
  "rol": "CLIENTE"
}
```

---

### 🍕 Crear Restaurante

```json
POST http://localhost:8082/api/v1/restaurantes
{
  "nombre": "Pizzeria Centro",
  "direccion": "Av. Siempre Viva 123",
  "telefono": "912345678",
  "horarioApertura": "09:00",
  "horarioCierre": "22:00"
}
```

---

### 📅 Crear Reserva

```json
POST http://localhost:8084/api/v1/reservas
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

## 📂 Estructura del Proyecto

```text
FoodFlow/
├── usuario-ms/
├── restaurante-ms/
├── menu-ms/
├── reserva-ms/
├── calificacion-ms/
├── orden-ms/
├── delivery-ms/
├── pago-ms/
├── promocion-ms/
├── reporte-ms/
└── README.md
```

---

## 📊 Estado del Proyecto

* ✅ 5 microservicios de Erick completos
* ⏳ 5 microservicios de Cristofer en desarrollo
* ✅ Comunicación Feign implementada
* ✅ DTOs y validaciones
* ✅ Consultas derivadas JPA
* ✅ Logs con SLF4J

---

## 👨‍💻 Autores

* **Erick** - Usuario, Restaurante, Menú, Reserva, Calificación
* **Cristofer** - Orden, Delivery, Pago, Promoción, Reporte
