# TPV-Comandas-Almacén
Aplicación de TPV y Comandas con gestión de almacén para hostelería.  
Proyecto TFC — 2º DAM (2025)

## Descripción general
**Trabajo Final de Ciclo (TFC) — Desarrollo de Aplicaciones Multiplataforma (DAM).**

El proyecto consiste en el desarrollo de un sistema completo para la gestión de un bar o restaurante.  
El sistema se compone de tres módulos principales:

- **Aplicación de escritorio (TPV/Almacén):** gestión de mesas, comandas, productos, stock, escandallos y recetas.
- **Aplicación móvil (Android):** comandero para los camareros, permite enviar pedidos a cocina o barra en tiempo real.
- **Backend (API REST con Spring Boot + MySQL):** centraliza la lógica del negocio y mantiene sincronizados los datos entre los distintos módulos.

---

## Estructura del repositorio
```
tpv-comandas-almacen/
├── back/
│   ├── bd/
│   │   ├── schema.sql     ← estructura completa de la base de datos
│   │   └── seed.sql       ← datos iniciales de prueba
│   └── tpv-api/           ← backend Spring Boot (API REST)
│       ├── src/main/java/
│       │   ├── controller/    ← controladores REST
│       │   ├── repository/    ← repositorios y DAO personalizados
│       │   ├── model/         ← entidades JPA
│       │   └── dto/           ← DTOs de respuesta
│       └── pom.xml
│
├── desktop/
│   └── tpv-desktop/       ← aplicación de escritorio (Swing)
│       ├── src/main/java/
│       │   ├── api/       ← cliente HTTP que consume la API REST
│       │   ├── model/     ← clases de datos (Producto, Mesa, Comanda…)
│       │   └── ui/        ← interfaz gráfica (VentanaPrincipal, tablas…)
│       └── pom.xml
│
├── android/               ← módulo móvil (pendiente de desarrollo)
│
├── docs/
│   ├── erd.png            ← diagrama EER de la base de datos
│   └── erd.mwb            ← modelo original MySQL Workbench
│
├── LICENSE
└── README.md

```

---

## Base de datos

### 1. Requisitos previos
- **MySQL Server 8.0** o superior  
- **MySQL Workbench** (opcional, para visualizar el diagrama)

### 2. Creación de la base de datos
Ejecutar desde consola o Workbench:

```bash
mysql -u root -p < backend/db/schema.sql
mysql -u root -p < backend/db/seed.sql
```

Esto creará toda la estructura y cargará los datos de ejemplo.

### 3. Verificación
Comprobar que las tablas se han creado correctamente:

```sql
USE tpv_tfc;
SHOW TABLES;
SELECT * FROM producto;
SELECT * FROM insumo;
```
---

## Ejecución del proyecto

### 1. Backend (tpv-api)
Requisitos:
- Java 17 o superior  
- Maven 3.8+  

Configurar `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tpv_tfc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

Ejecutar:
```bash
cd back/tpv-api
mvn spring-boot:run
```

Endpoints principales disponibles:
- `GET /api/mesas`
- `GET /api/mesas/{id}/abierto` → consulta agregada de productos abiertos
- `GET /api/productos`
- `POST /api/comandas`

---

### 2. Aplicación de escritorio (tpv-desktop)
Requisitos:
- Java 17 o superior  
- Maven

Configurar `application.properties`:
```
api.baseUrl=http://localhost:8080
```

Ejecutar:
```bash
cd desktop/tpv-desktop
mvn clean compile exec:java -Dexec.mainClass="com.hector.tpv.tpv.desktop.Main"
```

#### Funcionalidades implementadas:
- Visualización de **mesas reales** desde la BD.  
- Visualización de **productos disponibles** desde la API.  
- Consulta de **comandas abiertas** por mesa (`/api/mesas/{id}/abierto`).  
- Envío de **nuevas comandas** a través del backend (`/api/comandas`).  
- Cálculo de totales y visualización del estado actual de cada mesa.

---

## Estado actual del desarrollo

| Módulo | Estado | Descripción |
|--------|---------|-------------|
| **Backend (tpv-api)** | 🟢 Operativo | Endpoints funcionales con persistencia real y consultas agregadas |
| **TPV de Escritorio** | 🟢 Funcional | Interfaz Swing conectada al backend con envío de comandas |
| **Android (Comandero)** | ⚪ Pendiente | Previsto para siguiente iteración |
| **Modo Almacén / Escandallos** | ⚪ Pendiente | Implementación planificada para siguientes fases |

---
