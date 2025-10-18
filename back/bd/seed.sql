USE tpv_tfc;

INSERT INTO rol(nombre) VALUES ('ADMIN'), ('CAMARERO'), ('ALMACEN');

INSERT INTO usuario(nombre, pin_hash, activo) VALUES ('admin', 'hash_admin', 1);
INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (1, 1);

INSERT INTO zona(nombre) VALUES ('TERRAZA'), ('SALA');

INSERT INTO mesa(codigo, zona_id) VALUES ('TE21', 1), ('TE22', 1), ('SA01', 2);

INSERT INTO categoria_producto(nombre) VALUES ('CAFETERIA'), ('BEBIDAS'), ('COCTELES'), ('VINOS');

INSERT INTO producto(nombre, categoria_id, precio_venta, iva_tipo, destino, activo)
VALUES
('Café solo',        1, 1.50, 10.00, 'BARRA', 1),
('Café con leche',   1, 1.60, 10.00, 'BARRA', 1),
('Caña',             2, 2.20, 10.00, 'BARRA', 1),
('Refresco 33cl',    2, 2.00, 10.00, 'BARRA', 1),
('Cubata Ron-Cola',  3, 6.00, 10.00, 'BARRA', 1),
('Copa de vino',     4, 3.00, 10.00, 'BARRA', 1);

INSERT INTO insumo(nombre, unidad_base, tipo_stock, factor_base, coste_unit_base, stock_actual_base)
VALUES
('Café molido','g','MEDIBLE',1,0.0150,5000),
('Leche','ml','MEDIBLE',1,0.0010,10000),
('Cerveza barril','ml','MEDIBLE',1,0.0020,300000),
('Refresco 33cl','ud','UNITARIO',1,0.4000,100),
('Ron','ml','MEDIBLE',1,0.0200,5000),
('Vino tinto','ml','MEDIBLE',1,0.0060,50000);

INSERT INTO receta(nombre, producto_id, tiempo_preparacion_seg)
VALUES
('Café solo',       (SELECT id FROM producto WHERE nombre='Café solo'), 45),
('Café con leche',  (SELECT id FROM producto WHERE nombre='Café con leche'), 60);

INSERT INTO receta_insumo(receta_id, insumo_id, cantidad_base)
VALUES
((SELECT id FROM receta WHERE nombre='Café solo'),
 (SELECT id FROM insumo WHERE nombre='Café molido'), 7.0),
((SELECT id FROM receta WHERE nombre='Café con leche'),
 (SELECT id FROM insumo WHERE nombre='Café molido'), 7.0),
((SELECT id FROM receta WHERE nombre='Café con leche'),
 (SELECT id FROM insumo WHERE nombre='Leche'), 150.0);

INSERT INTO producto_insumo(producto_id, insumo_id, cantidad_base_por_ud)
VALUES
((SELECT id FROM producto WHERE nombre='Caña'),
 (SELECT id FROM insumo WHERE nombre='Cerveza barril'), 330.0),
((SELECT id FROM producto WHERE nombre='Refresco 33cl'),
 (SELECT id FROM insumo WHERE nombre='Refresco 33cl'), 1.0),
((SELECT id FROM producto WHERE nombre='Cubata Ron-Cola'),
 (SELECT id FROM insumo WHERE nombre='Ron'), 50.0),
((SELECT id FROM producto WHERE nombre='Cubata Ron-Cola'),
 (SELECT id FROM insumo WHERE nombre='Refresco 33cl'), 1.0),
((SELECT id FROM producto WHERE nombre='Copa de vino'),
 (SELECT id FROM insumo WHERE nombre='Vino tinto'), 150.0);

INSERT INTO stock_movimiento(insumo_id, tipo, cantidad_base, motivo)
VALUES
((SELECT id FROM insumo WHERE nombre='Café molido'), 'ENTRADA', 2000, 'Reposición'),
((SELECT id FROM insumo WHERE nombre='Leche'), 'ENTRADA', 5000, 'Reposición'),
((SELECT id FROM insumo WHERE nombre='Cerveza barril'), 'ENTRADA', 100000, 'Barril nuevo'),
((SELECT id FROM insumo WHERE nombre='Refresco 33cl'), 'ENTRADA', 50, 'Caja nueva'),
((SELECT id FROM insumo WHERE nombre='Ron'), 'ENTRADA', 2000, 'Botellas'),
((SELECT id FROM insumo WHERE nombre='Vino tinto'), 'ENTRADA', 10000, 'Cajas vino');
