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
│
├── bd/
│   ├── schema.sql           ← estructura completa de la base de datos
│   ├── seed.sql             ← datos iniciales de prueba
│   └── Dump20251120.sql     ← volcado completo usado durante el desarrollo
│
├── back/
│   └── tpv-api/             ← backend Spring Boot (API REST)
│       ├── src/main/java/com/hector/tpv/tpvapi/
│       │   ├── controller/   ← controladores REST
│       │   ├── repository/   ← repositorios y consultas agregadas
│       │   ├── model/        ← entidades JPA
│       │   ├── service/      ← lógica de negocio (tickets, comandas…)
│       │   └── dto/          ← objetos de transferencia de datos
│       ├── src/main/resources/
│       │   └── reports/      ← plantillas JasperReports (tickets, cierres…)
│       └── pom.xml
│
├── desktop/
│   └── tpv-desktop/         ← aplicación de escritorio (Swing)
│       ├── src/main/java/com/hector/tpv/tpv/desktop/
│       │   ├── api/          ← cliente HTTP que consume la API REST
│       │   ├── model/        ← clases de datos (Producto, Mesa, Comanda…)
│       │   └── ui/           ← interfaz gráfica (TPV, Mesas, Productos…)
│       ├── src/main/resources/
│       │   └── application.properties
│       └── pom.xml
│
├── android/
│   └── Comandero/           ← aplicación móvil Android (comandero)
│       ├── app/src/main/java/com/example/comandero/
│       │   ├── api/          ← Retrofit + cliente API
│       │   ├── model/        ← modelos Android (Mesa, Producto, Categoría…)
│       │   ├── ui/           ← Activities + adapters
│       │   └── offline/      ← WorkManager (modo offline y reintentos)
│       └── app/src/main/res/ ← layouts XML, drawables, menus…
│
├── docs/
│   ├── erd.png ← diagrama EER de la base de datos
│   └── erd.mwb ← modelo original MySQL Workbench
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

##  Ejecución del proyecto

## 1. Backend (API REST)

### Requisitos
- Java 17+
- Maven

### Configuración (`application.properties`)
```
spring.datasource.url=jdbc:mysql://localhost:3306/tpv_tfc
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=none
```

### Ejecutar
```
cd back/tpv-api
mvn spring-boot:run
```

### Endpoints principales
```
GET    /api/mesas
GET    /api/mesas/{id}/abierto
GET    /api/productos
GET    /api/categorias
POST   /api/comandas
POST   /api/mesas/{id}/cobrar
```

---

## 2. TPV Escritorio (Swing)

### Requisitos
- Java 17+
- Maven

### Configurar `api.baseUrl`
```
api.baseUrl=http://localhost:8080
```

### Ejecutar
```
cd desktop/tpv-desktop
mvn clean compile exec:java -Dexec.mainClass="com.hector.tpv.tpv.desktop.Main"
```

### Funcionalidades
- Mesas en tiempo real.
- Productos por categorías.
- Carrito editable.
- Enviar comanda.
- Cobro y cierre.

---

## 3. Aplicación Android (Comandero)

### Requisitos
- Android Studio
- Móvil o emulador

### Configurar API
En `ApiService`:
```
BASE_URL = "http://192.168.X.XX:8080/";
```

### Ejecutar
1. Abrir `android/Comandero/`
2. Sincronizar Gradle
3. Ejecutar en emulador/móvil

### Funcionalidades
- Mesas y productos en tiempo real.
- Carrito con modificar/eliminar.
- Resumen de comanda.
- Envío al backend.
- Modo offline:
  - Cola de envíos
  - Reintentos
  - WorkManager



---

## Estado actual del desarrollo

| Módulo | Estado | Descripción |
|--------|---------|-------------|
| **Backend (tpv-api)** | 🟢 Operativo | API REST funcional |
| **TPV de Escritorio** | 🟢 Operativo | Mesas, productos, envío, cobro |
| **Android (Comandero)** | 🟢 Operativo | Flujo completo |
| **Modo Almacén** | ⚪ Pendiente | Implementación planificada para siguientes fases |

---
