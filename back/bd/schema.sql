CREATE DATABASE  IF NOT EXISTS `tpv_tfc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tpv_tfc`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: tpv_tfc
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria_producto`
--

DROP TABLE IF EXISTS `categoria_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cierre_turno`
--

DROP TABLE IF EXISTS `cierre_turno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cierre_turno` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creado_at` datetime NOT NULL,
  `desde` datetime NOT NULL,
  `hasta` datetime NOT NULL,
  `num_comandas` int NOT NULL,
  `base_total` double NOT NULL,
  `iva_total` double NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comanda`
--

DROP TABLE IF EXISTS `comanda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comanda` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mesa_id` bigint NOT NULL,
  `camarero_id` bigint NOT NULL,
  `estado` enum('ABIERTA','ENVIADA','ANULADA','COBRADA','CERRADA') NOT NULL DEFAULT 'ABIERTA',
  `creada_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enviada_at` datetime DEFAULT NULL,
  `cobrada_at` datetime DEFAULT NULL,
  `cerrada_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `camarero_id` (`camarero_id`),
  KEY `idx_comanda_mesa` (`mesa_id`),
  KEY `idx_comanda_estado` (`estado`),
  CONSTRAINT `comanda_ibfk_1` FOREIGN KEY (`mesa_id`) REFERENCES `mesa` (`id`),
  CONSTRAINT `comanda_ibfk_2` FOREIGN KEY (`camarero_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comanda_linea`
--

DROP TABLE IF EXISTS `comanda_linea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comanda_linea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comanda_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL,
  `texto_modificador` varchar(200) DEFAULT NULL,
  `anulada` tinyint(1) NOT NULL DEFAULT '0',
  `precio_unitario` decimal(10,2) NOT NULL,
  `iva_tipo` decimal(4,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_linea_comanda` (`comanda_id`),
  KEY `idx_linea_producto` (`producto_id`),
  CONSTRAINT `comanda_linea_ibfk_1` FOREIGN KEY (`comanda_id`) REFERENCES `comanda` (`id`),
  CONSTRAINT `comanda_linea_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `insumo`
--

DROP TABLE IF EXISTS `insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `unidad_base` varchar(20) NOT NULL,
  `tipo_stock` enum('MEDIBLE','UNITARIO') NOT NULL,
  `factor_base` decimal(12,4) NOT NULL DEFAULT '1.0000',
  `coste_unit_base` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `stock_actual_base` decimal(12,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `zona_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `idx_mesa_zona` (`zona_id`),
  CONSTRAINT `mesa_ibfk_1` FOREIGN KEY (`zona_id`) REFERENCES `zona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `iva_tipo` decimal(4,2) NOT NULL DEFAULT '10.00',
  `destino` enum('COCINA','BARRA') NOT NULL DEFAULT 'BARRA',
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_producto_categoria` (`categoria_id`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria_producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto_insumo`
--

DROP TABLE IF EXISTS `producto_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_insumo` (
  `producto_id` bigint NOT NULL,
  `insumo_id` bigint NOT NULL,
  `cantidad_base_por_ud` decimal(12,4) NOT NULL,
  PRIMARY KEY (`producto_id`,`insumo_id`),
  KEY `insumo_id` (`insumo_id`),
  KEY `idx_pi_producto` (`producto_id`),
  CONSTRAINT `producto_insumo_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  CONSTRAINT `producto_insumo_ibfk_2` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receta`
--

DROP TABLE IF EXISTS `receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `producto_id` bigint DEFAULT NULL,
  `tiempo_preparacion_seg` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `producto_id` (`producto_id`),
  KEY `idx_receta_producto` (`producto_id`),
  CONSTRAINT `receta_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receta_insumo`
--

DROP TABLE IF EXISTS `receta_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta_insumo` (
  `receta_id` bigint NOT NULL,
  `insumo_id` bigint NOT NULL,
  `cantidad_base` decimal(12,4) NOT NULL,
  PRIMARY KEY (`receta_id`,`insumo_id`),
  KEY `insumo_id` (`insumo_id`),
  KEY `idx_ri_receta` (`receta_id`),
  CONSTRAINT `receta_insumo_ibfk_1` FOREIGN KEY (`receta_id`) REFERENCES `receta` (`id`),
  CONSTRAINT `receta_insumo_ibfk_2` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock_movimiento`
--

DROP TABLE IF EXISTS `stock_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_movimiento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `insumo_id` bigint NOT NULL,
  `tipo` enum('ENTRADA','SALIDA','AJUSTE') NOT NULL,
  `cantidad_base` decimal(12,4) NOT NULL,
  `motivo` varchar(200) DEFAULT NULL,
  `ref_comanda_id` bigint DEFAULT NULL,
  `ref_linea_id` bigint DEFAULT NULL,
  `creado_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ref_comanda_id` (`ref_comanda_id`),
  KEY `ref_linea_id` (`ref_linea_id`),
  KEY `idx_sm_insumo` (`insumo_id`),
  CONSTRAINT `stock_movimiento_ibfk_1` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`),
  CONSTRAINT `stock_movimiento_ibfk_2` FOREIGN KEY (`ref_comanda_id`) REFERENCES `comanda` (`id`),
  CONSTRAINT `stock_movimiento_ibfk_3` FOREIGN KEY (`ref_linea_id`) REFERENCES `comanda_linea` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comanda_id` bigint DEFAULT NULL,
  `base_total` decimal(10,2) NOT NULL,
  `iva_total` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `creado_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `metodo_pago` enum('EFECTIVO','TARJETA') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comanda_id` (`comanda_id`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`comanda_id`) REFERENCES `comanda` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_comanda`
--

DROP TABLE IF EXISTS `ticket_comanda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_comanda` (
  `ticket_id` bigint NOT NULL,
  `comanda_id` bigint NOT NULL,
  PRIMARY KEY (`ticket_id`,`comanda_id`),
  KEY `fk_tc_comanda` (`comanda_id`),
  CONSTRAINT `fk_tc_comanda` FOREIGN KEY (`comanda_id`) REFERENCES `comanda` (`id`),
  CONSTRAINT `fk_tc_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `pin_hash` varchar(255) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_rol` (
  `usuario_id` bigint NOT NULL,
  `rol_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `rol_id` (`rol_id`),
  CONSTRAINT `usuario_rol_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `usuario_rol_ibfk_2` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zona`
--

DROP TABLE IF EXISTS `zona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zona` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-07 16:17:09
