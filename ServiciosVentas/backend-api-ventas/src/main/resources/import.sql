/* Populate tabla clientes */


INSERT INTO `empresas` (enabled,nombre,created_at) VALUES (1,'EMPRESA 1','2019-01-01');

INSERT INTO `users` (username, password, enabled, nombre, apellido, email,created_at,empresa_id) VALUES ('admin1','$2a$10$UMYzRzlYtEit3TjcR26Ah.zJDwHYwUH9q1Asay7fjDv4EA.Qv5Syy',1, 'Andres', 'Guzman','profesor@bolsadeideas.com','2019-01-01',1);
INSERT INTO `users` (username, password, enabled, nombre, apellido, email,created_at,empresa_id) VALUES ('admin','$2a$10$s29t6glPVn.W9nxAYH6UheUUUnCIZTEWkl7bnHDtUpSJ0G.hFMGCG',1, 'John', 'Doe','jhon.doe@bolsadeideas.com','2019-01-01',1);

INSERT INTO clientes (nombres, apellidos, email, cedula,direccion,telefono,created_at,empresa_id) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '1721856776','quito','09766776655','2019-01-01',1);
INSERT INTO clientes (nombres, apellidos, email, cedula,direccion,telefono,created_at,empresa_id) VALUES('mnauel', 'chimbo', 'profeso44@bolsadeideas.com', '1721856775','quito','09766776655','2019-01-01',1);

INSERT INTO provedores (nombre, apellido, email, cedula,direccion1,telefono,created_at,empresa_id) VALUES('maria', 'Guzmán', 'maria@bolsadeideas.com', '1721856776','quito','09766776655','2019-01-01',1);
INSERT INTO provedores (nombre, apellido, email, cedula,direccion1,telefono,created_at,empresa_id) VALUES('juan', 'chimbo', 'juan@bolsadeideas.com', '1721856775','quito','09766776655','2019-01-01',1);


INSERT INTO `authorities` (authority) VALUES ('ROLE_USER');
INSERT INTO `authorities` (authority) VALUES ('ROLE_ADMIN');
INSERT INTO `authorities` (authority) VALUES ('ROLE_ROOT');

INSERT INTO `user_authorities` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_authorities` (user_id, role_id) VALUES (2, 2);
INSERT INTO `user_authorities` (user_id, role_id) VALUES (2, 1);
INSERT INTO `user_authorities` (user_id, role_id) VALUES (2, 3);


INSERT INTO `modules` (nombre,descripcion,created_at) VALUES ('VENTAS','Modulo de ventas','2019-01-01');
INSERT INTO `modules` (nombre,descripcion,created_at) VALUES ('INVENTARIO','Modulo de inventario','2019-01-01');
INSERT INTO `modules` (nombre,descripcion,created_at) VALUES ('CONTABILIDAD','Modulo de contabilidad','2019-01-01');

INSERT INTO `empresa_modules` (empresa_id, module_id) VALUES (1, 1);
INSERT INTO `empresa_modules` (empresa_id, module_id) VALUES (1, 2);
INSERT INTO `empresa_modules` (empresa_id, module_id) VALUES (1, 3);


INSERT INTO `menu_modules` (nombre,created_at, module_id) VALUES ('PANEL', '2019-01-01',1);
INSERT INTO `menu_modules` (nombre,created_at, module_id) VALUES ('PANEL', '2019-01-01',2);
INSERT INTO `menu_modules` (nombre,created_at, module_id) VALUES ('PANEL', '2019-01-01',3);

INSERT INTO `tipo_producto` (nombre,created_at,descripcion) VALUES ('Comestible', '2019-01-01',"Comestible");
INSERT INTO `tipo_producto` (nombre,created_at,descripcion) VALUES ('Servicio', '2019-01-01',"Servicio");
INSERT INTO `tipo_producto` (nombre,created_at,descripcion) VALUES ('Almacenable', '2019-01-01',"Almacenable");

INSERT INTO `punto_venta` (ciudad,direccion,empresa_id,provincia,nombre) VALUES ('Quito', 'Los Zauces',1,"Pichicnha","Tienda Los Sauces");


INSERT INTO `categoria_producto` (nombre_categoria,descripcion_categoria,empresa_id) VALUES ('VIVERES','Productos comestibles',1);
INSERT INTO `categoria_producto` (nombre_categoria,descripcion_categoria,empresa_id) VALUES ('ELECTRODOMESTICOS','Productos de casa',1);
INSERT INTO `categoria_producto` (nombre_categoria,descripcion_categoria,empresa_id) VALUES ('LEGUNBRES','Productos vegetales',1);
INSERT INTO `categoria_producto` (nombre_categoria,descripcion_categoria,empresa_id) VALUES ('BEBIDAS','Proeuctos de bebidas',1);

INSERT INTO `provincias` (nombre,) VALUES ('Azuay');
INSERT INTO `provincias` (nombre,) VALUES ('Bolívar');
INSERT INTO `provincias` (nombre) VALUES ('Cañar');
INSERT INTO `provincias` (nombre) VALUES ('Carchi');
INSERT INTO `provincias` (nombre) VALUES ('Chimborazo');
INSERT INTO `provincias` (nombre) VALUES ('Cotopaxi');
INSERT INTO `provincias` (nombre) VALUES ('El Oro');
INSERT INTO `provincias` (nombre) VALUES ('Esmeraldas');
INSERT INTO `provincias` (nombre) VALUES ('Galápagos');
INSERT INTO `provincias` (nombre) VALUES ('Guayas');
INSERT INTO `provincias` (nombre) VALUES ('Imbabura');
INSERT INTO `provincias` (nombre) VALUES ('Loja');
INSERT INTO `provincias` (nombre) VALUES ('Los Ríos');
INSERT INTO `provincias` (nombre) VALUES ('Manabí');
INSERT INTO `provincias` (nombre) VALUES ('Morona Santiago');
INSERT INTO `provincias` (nombre) VALUES ('Napo');
INSERT INTO `provincias` (nombre) VALUES ('Orellana');
INSERT INTO `provincias` (nombre) VALUES ('Pastaza');
INSERT INTO `provincias` (nombre) VALUES ('Pichincha');
INSERT INTO `provincias` (nombre) VALUES ('Santa Elena');
INSERT INTO `provincias` (nombre) VALUES ('Santo Domingo de los Tsáchilas');
INSERT INTO `provincias` (nombre) VALUES ('Sucumbíos');
INSERT INTO `provincias` (nombre) VALUES ('Tungurahua');
INSERT INTO `provincias` (nombre) VALUES ('Zamora Chinchipe');



INSERT INTO `ciudades` (nombre,provincia_id) VALUES ('Quito',19);
INSERT INTO `ciudades` (nombre,provincia_id) VALUES ('Sangolqui',19);
INSERT INTO `ciudades` (nombre,provincia_id) VALUES ('Latacunga',6);
INSERT INTO `ciudades` (nombre,provincia_id) VALUES ('Salcedo',6);


INSERT INTO `tipo_documento` (nombre_documento,descripcion,empresa_id) VALUES ('Factura', 'Factura',1);
INSERT INTO `tipo_documento` (nombre_documento,descripcion,empresa_id) VALUES ('Nota de Venta', 'No necesita factura',1);
INSERT INTO `tipo_documento` (nombre_documento,descripcion,empresa_id) VALUES ('Retención', 'Retencion de impuesto',1);

INSERT INTO `productos` (nombre_producto,observaciones,categoria_id,empresa_id,tipo_producto_id,precio_venta_producto) VALUES ('Arroz','arros de mesa',1,1,1,12);
INSERT INTO `productos` (nombre_producto,observaciones,categoria_id,empresa_id,tipo_producto_id,precio_venta_producto) VALUES ('Azucar','Azucar de mesa',1,1,1,123);

INSERT INTO `ventas` (descuento_venta,estado_venta,id_empresa,numero_venta,observacion,punto_venta_id,tipo_documento_id,usuario_id,cliente_id) VALUES (12,1,1,1,'venta de arroz',1,1,1,1);

INSERT INTO `detalle_venta` (cantidad,costo_detalle,iva_detalle,total_detalle,producto_id,venta_id) VALUES (12,12,0.12,123,1,1);
INSERT INTO `detalle_venta` (cantidad,costo_detalle,iva_detalle,total_detalle,producto_id,venta_id) VALUES (12,12,0.12,123,2,1);