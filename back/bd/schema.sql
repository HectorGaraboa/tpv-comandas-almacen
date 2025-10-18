DROP DATABASE IF EXISTS tpv_tfc;
CREATE DATABASE tpv_tfc CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE tpv_tfc;

DROP TABLE IF EXISTS stock_movimiento;
DROP TABLE IF EXISTS producto_insumo;
DROP TABLE IF EXISTS receta_insumo;
DROP TABLE IF EXISTS receta;
DROP TABLE IF EXISTS comanda_linea;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS comanda;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS categoria_producto;
DROP TABLE IF EXISTS mesa;
DROP TABLE IF EXISTS zona;
DROP TABLE IF EXISTS insumo;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;

CREATE TABLE rol (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE usuario (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  pin_hash VARCHAR(255) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE usuario_rol (
  usuario_id BIGINT NOT NULL,
  rol_id BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, rol_id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (rol_id) REFERENCES rol(id)
) ENGINE=InnoDB;

CREATE TABLE zona (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE mesa (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  codigo VARCHAR(20) NOT NULL UNIQUE,
  zona_id BIGINT NOT NULL,
  FOREIGN KEY (zona_id) REFERENCES zona(id)
) ENGINE=InnoDB;

CREATE TABLE categoria_producto (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE producto (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  categoria_id BIGINT NULL,
  precio_venta DECIMAL(10,2) NOT NULL,
  iva_tipo DECIMAL(4,2) NOT NULL DEFAULT 10.00,
  destino ENUM('COCINA','BARRA') NOT NULL DEFAULT 'BARRA',
  activo TINYINT(1) NOT NULL DEFAULT 1,
  FOREIGN KEY (categoria_id) REFERENCES categoria_producto(id)
) ENGINE=InnoDB;

CREATE TABLE insumo (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  unidad_base VARCHAR(20) NOT NULL,
  tipo_stock ENUM('MEDIBLE','UNITARIO') NOT NULL,
  factor_base DECIMAL(12,4) NOT NULL DEFAULT 1,
  coste_unit_base DECIMAL(10,4) NOT NULL DEFAULT 0,
  stock_actual_base DECIMAL(12,4) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE comanda (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  mesa_id BIGINT NOT NULL,
  camarero_id BIGINT NOT NULL,
  estado ENUM('ABIERTA','ENVIADA','ANULADA','COBRADA','CERRADA') NOT NULL DEFAULT 'ABIERTA',
  creada_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enviada_at DATETIME NULL,
  cobrada_at DATETIME NULL,
  cerrada_at DATETIME NULL,
  FOREIGN KEY (mesa_id) REFERENCES mesa(id),
  FOREIGN KEY (camarero_id) REFERENCES usuario(id)
) ENGINE=InnoDB;

CREATE TABLE comanda_linea (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  comanda_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  texto_modificador VARCHAR(200) NULL,
  anulada TINYINT(1) NOT NULL DEFAULT 0,
  precio_unitario DECIMAL(10,2) NOT NULL,
  iva_tipo DECIMAL(4,2) NOT NULL,
  FOREIGN KEY (comanda_id) REFERENCES comanda(id),
  FOREIGN KEY (producto_id) REFERENCES producto(id)
) ENGINE=InnoDB;

CREATE TABLE ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  comanda_id BIGINT NOT NULL,
  base_total DECIMAL(10,2) NOT NULL,
  iva_total DECIMAL(10,2) NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  creado_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (comanda_id) REFERENCES comanda(id)
) ENGINE=InnoDB;

CREATE TABLE receta (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  producto_id BIGINT UNIQUE,
  tiempo_preparacion_seg INT DEFAULT 0,
  FOREIGN KEY (producto_id) REFERENCES producto(id)
) ENGINE=InnoDB;

CREATE TABLE receta_insumo (
  receta_id BIGINT NOT NULL,
  insumo_id BIGINT NOT NULL,
  cantidad_base DECIMAL(12,4) NOT NULL,
  PRIMARY KEY (receta_id, insumo_id),
  FOREIGN KEY (receta_id) REFERENCES receta(id),
  FOREIGN KEY (insumo_id) REFERENCES insumo(id)
) ENGINE=InnoDB;

CREATE TABLE producto_insumo (
  producto_id BIGINT NOT NULL,
  insumo_id BIGINT NOT NULL,
  cantidad_base_por_ud DECIMAL(12,4) NOT NULL,
  PRIMARY KEY (producto_id, insumo_id),
  FOREIGN KEY (producto_id) REFERENCES producto(id),
  FOREIGN KEY (insumo_id) REFERENCES insumo(id)
) ENGINE=InnoDB;

CREATE TABLE stock_movimiento (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  insumo_id BIGINT NOT NULL,
  tipo ENUM('ENTRADA','SALIDA','AJUSTE') NOT NULL,
  cantidad_base DECIMAL(12,4) NOT NULL,
  motivo VARCHAR(200),
  ref_comanda_id BIGINT NULL,
  ref_linea_id BIGINT NULL,
  creado_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (insumo_id) REFERENCES insumo(id),
  FOREIGN KEY (ref_comanda_id) REFERENCES comanda(id),
  FOREIGN KEY (ref_linea_id) REFERENCES comanda_linea(id)
) ENGINE=InnoDB;

CREATE INDEX idx_mesa_zona ON mesa(zona_id);
CREATE INDEX idx_producto_categoria ON producto(categoria_id);
CREATE INDEX idx_comanda_mesa ON comanda(mesa_id);
CREATE INDEX idx_comanda_estado ON comanda(estado);
CREATE INDEX idx_linea_comanda ON comanda_linea(comanda_id);
CREATE INDEX idx_linea_producto ON comanda_linea(producto_id);
CREATE INDEX idx_receta_producto ON receta(producto_id);
CREATE INDEX idx_pi_producto ON producto_insumo(producto_id);
CREATE INDEX idx_ri_receta ON receta_insumo(receta_id);
CREATE INDEX idx_sm_insumo ON stock_movimiento(insumo_id);
