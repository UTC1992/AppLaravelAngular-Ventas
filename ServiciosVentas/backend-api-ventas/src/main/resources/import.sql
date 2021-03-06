/* Populate tabla clientes */
INSERT INTO clientes (nombres, apellidos, email, cedula,direccion,telefono,created_at) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '1721856776','quito','09766776655','2019-01-01');
INSERT INTO clientes (nombres, apellidos, email, cedula,direccion,telefono,created_at) VALUES('mnauel', 'chimbo', 'profeso44@bolsadeideas.com', '1721856776','quito','09766776655','2019-01-01');

INSERT INTO `empresas` (enabled,nombre,created_at) VALUES (1,'EMPRESA 1','2019-01-01');

INSERT INTO `users` (username, password, enabled, nombre, apellido, email,created_at,empresa_id) VALUES ('admin1','$2a$10$UMYzRzlYtEit3TjcR26Ah.zJDwHYwUH9q1Asay7fjDv4EA.Qv5Syy',1, 'Andres', 'Guzman','profesor@bolsadeideas.com','2019-01-01',1);
INSERT INTO `users` (username, password, enabled, nombre, apellido, email,created_at,empresa_id) VALUES ('admin','$2a$10$s29t6glPVn.W9nxAYH6UheUUUnCIZTEWkl7bnHDtUpSJ0G.hFMGCG',1, 'John', 'Doe','jhon.doe@bolsadeideas.com','2019-01-01',1);



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