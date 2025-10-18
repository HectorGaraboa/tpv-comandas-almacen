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
├── backend/
│   └── db/
│       ├── schema.sql     ← estructura completa de la base de datos
│       └── seed.sql       ← datos iniciales de prueba
├── docs/
│   ├── erd.png            ← diagrama EER de la base de datos
│   └── erd.mwb            ← modelo original de MySQL Workbench
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
