CREATE DATABASE warehouse;
USE warehouse;

DROP TABLE IF EXISTS `outBoundOrder_items`;
DROP TABLE IF EXISTS `outBoundOrderItem`;
DROP TABLE IF EXISTS `outBoundOrder`;
DROP TABLE IF EXISTS `inventory`;
DROP TABLE IF EXISTS `inBoundOrder_items`;
DROP TABLE IF EXISTS `inBoundOrderItem`;
DROP TABLE IF EXISTS `inBoundOrder`;
DROP TABLE IF EXISTS `procurementRequest_items`;
DROP TABLE IF EXISTS `procurementRequestItem`;
DROP TABLE IF EXISTS `procurementRequest`;
DROP TABLE IF EXISTS `constructionSite`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `supplier`;
DROP TABLE IF EXISTS `brandName`;
DROP TABLE IF EXISTS `employee_role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `department`;

/*
Table structure for department
*/
CREATE TABLE `department`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
	PRIMARY KEY(`id`)
);

/*
Records for department
*/
BEGIN;
INSERT INTO `department` VALUES('1','procurement'), ('2','account'), ('3','construction site'), ('4','warehouse'), ('5','management');
COMMIT;


/*
Table structure for employee
*/
CREATE TABLE `employee`(
	`id` INT unsigned NOT NULL auto_increment,
	`name` varchar(50) NOT NULL,
	`password` varchar(32) NOT NULL,
    `age` tinyint unsigned NOT NULL,
    `admin` tinyint NOT NULL DEFAULT 0,
    `status` tinyint NOT NULL DEFAULT 0,
    `dept_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`dept_id`) REFERENCES `department`(`id`)
);

/*
Records for employee
For testing, the password of all employees is set to 1234
*/
BEGIN;
INSERT INTO `employee` VALUES('1','admin', '81dc9bdb52d04dc20036dbd8313ed055', 22, 1, 0, 5), ('2','jack', '81dc9bdb52d04dc20036dbd8313ed055', 20, 0, 0, 3), ('3','rose', '81dc9bdb52d04dc20036dbd8313ed055', 20, 0, 0, 2), ('4','peter', '81dc9bdb52d04dc20036dbd8313ed055', 21, 0, 0, 4), ('5','mary', '81dc9bdb52d04dc20036dbd8313ed055', 40, 0, 0, 2), ('6','david', '81dc9bdb52d04dc20036dbd8313ed055', 50, 0, 0, 1), ('7','aaron', '81dc9bdb52d04dc20036dbd8313ed055', 21, 0, 0, 1);
COMMIT;


/*
Table structure for permission
*/
CREATE TABLE `permission`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(100) NOT NULL,
    `expression` varchar(200) NOT NULL,
    PRIMARY KEY(`id`)
);

/*
Records for permission
*/
BEGIN;
INSERT INTO `permission` VALUES('1', 'List Employee Information', 'org.construction.wms.web.controller.EmployeeController:list'), ('2', 'Add/Edit Employee Information', 'org.construction.wms.web.controller.EmployeeController:saveOrUpdate'), ('3', 'Delete Employee Information', 'org.construction.wms.web.controller.EmployeeController:delete'), ('4', 'Input Employee Information','org.construction.wms.web.controller.EmployeeController:input'), ('5', 'Read Employee Information','org.construction.wms.web.controller.EmployeeController:read'), ('6', 'All Permission to deal with Employee Information','org.construction.wms.web.controller.EmployeeController:all'), ('7', 'List Department Information', 'org.construction.wms.web.controller.DepartmentController:list'), ('8', 'Add/Edit Department Information', 'org.construction.wms.web.controller.DepartmentController:saveOrUpdate'), ('9', 'Delete Department Information', 'org.construction.wms.web.controller.DepartmentController:delete'), ('10', 'Input Department Information','org.construction.wms.web.controller.DepartmentController:input'), ('11', 'All Permission to deal with Department Information','org.construction.wms.web.controller.DepartmentController:all'), ('12', 'List Role Information', 'org.construction.wms.web.controller.RoleController:list'), ('13', 'Add/Edit Role Information', 'org.construction.wms.web.controller.RoleController:saveOrUpdate'), ('14', 'Delete Role Information', 'org.construction.wms.web.controller.RoleController:delete'), ('15', 'Input Role Information','org.construction.wms.web.controller.RoleController:input'), ('16', 'All Permission to deal with Role Information','org.construction.wms.web.controller.RoleController:all'), ('17', 'List BrandName Information', 'org.construction.wms.web.controller.BrandNameController:list'), ('18', 'Add/Edit BrandName Information', 'org.construction.wms.web.controller.BrandNameController:saveOrUpdate'), ('19', 'Delete BrandName Information', 'org.construction.wms.web.controller.BrandNameController:delete'), ('20', 'Input BrandName Information','org.construction.wms.web.controller.BrandNameController:input'), ('21', 'All Permission to deal with BrandName Information','org.construction.wms.web.controller.BrandNameController:all'), ('22', 'List Supplier Information', 'org.construction.wms.web.controller.SupplierController:list'), ('23', 'Add/Edit Supplier Information', 'org.construction.wms.web.controller.SupplierController:saveOrUpdate'), ('24', 'Delete Supplier Information', 'org.construction.wms.web.controller.SupplierController:delete'), ('25', 'Input Supplier Information','org.construction.wms.web.controller.SupplierController:input'), ('26', 'All Permission to deal with Supplier Information','org.construction.wms.web.controller.SupplierController:all'), ('27', 'List Product Information', 'org.construction.wms.web.controller.ProductController:list'), ('28', 'Add/Edit Product Information', 'org.construction.wms.web.controller.ProductController:saveOrUpdate'), ('29', 'Delete Product Information', 'org.construction.wms.web.controller.ProductController:delete'), ('30', 'Input Product Information','org.construction.wms.web.controller.ProductController:input'), ('31', 'All Permission to deal with Product Information','org.construction.wms.web.controller.ProductController:all'), ('32', 'List Construction Site Information', 'org.construction.wms.web.controller.ConstructionSiteController:list'), ('33', 'Add/Edit Construction Site Information', 'org.construction.wms.web.controller.ConstructionSiteController:saveOrUpdate'), ('34', 'Delete Construction Site Information', 'org.construction.wms.web.controller.ConstructionSiteController:delete'), ('35', 'Input Construction Site Information','org.construction.wms.web.controller.ConstructionSiteController:input'), ('36', 'All Permission to deal with Construction Site Information','org.construction.wms.web.controller.ConstructionSiteController:all'), ('37', 'List Procurement Request Information', 'org.construction.wms.web.controller.ProcurementRequestController:list'), ('38', 'Add/Edit Procurement Request Information', 'org.construction.wms.web.controller.ProcurementRequestController:saveOrUpdate'), ('39', 'Delete Procurement Request Information', 'org.construction.wms.web.controller.ProcurementRequestController:delete'), ('40', 'Input Procurement Request Information','org.construction.wms.web.controller.ProcurementRequestController:input'), ('41', 'Confirm Procurement Request has been purchased','org.construction.wms.web.controller.ProcurementRequestController:purchase'), ('42', 'Read Procurement Request Information','org.construction.wms.web.controller.ProcurementRequestController:read'), ('43', 'All Permission to deal with Procurement Request Information','org.construction.wms.web.controller.ProcurementRequestController:all'), ('44', 'List InBound Order Information', 'org.construction.wms.web.controller.InBoundOrderController:list'), ('45', 'Add/Edit InBound Order Information', 'org.construction.wms.web.controller.InBoundOrderController:saveOrUpdate'), ('46', 'Delete InBound Order Information', 'org.construction.wms.web.controller.InBoundOrderController:delete'), ('47', 'Input InBound Order Information','org.construction.wms.web.controller.InBoundOrderController:input'), ('48', 'Audit InBound Order Information','org.construction.wms.web.controller.InBoundOrderController:audit'), ('49', 'Read InBound Order Information','org.construction.wms.web.controller.InBoundOrderController:read'), ('50', 'All Permission to deal with InBound Order Information','org.construction.wms.web.controller.InBoundOrderController:all'), ('51', 'List OutBound Order Information', 'org.construction.wms.web.controller.OutBoundOrderController:list'), ('52', 'Add/Edit OutBound Order Information', 'org.construction.wms.web.controller.OutBoundOrderController:saveOrUpdate'), ('53', 'Delete OutBound Order Information', 'org.construction.wms.web.controller.OutBoundOrderController:delete'), ('54', 'Input OutBound Order Information','org.construction.wms.web.controller.OutBoundOrderController:input'), ('55', 'Audit OutBound Order Information','org.construction.wms.web.controller.OutBoundOrderController:audit'), ('56', 'Read OutBound Order Information','org.construction.wms.web.controller.OutBoundOrderController:read'), ('57', 'All Permission to deal with OutBound Order Information','org.construction.wms.web.controller.OutBoundOrderController:all'), ('58', 'List Inventory Information','org.construction.wms.web.controller.InventoryController:list'), ('59', 'List Permission Information','org.construction.wms.web.controller.PermissionController:list');
COMMIT;


/*
Table structure for role
*/
CREATE TABLE `role`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY(`id`)
);

/*
Records for role
*/
BEGIN;
INSERT INTO `role` VALUES('1', 'admin'), ('2', 'merchandiser'), ('3', 'accountant'), ('4', 'construction site worker'), ('5', 'warehouse admin');
COMMIT;


/*
Table structure for role_permission
*/
CREATE TABLE `role_permission`(
	`role_id` INT unsigned NOT NULL,
    `permission_id` INT unsigned NOT NULL,
    PRIMARY KEY(`role_id`,`permission_id`),
    CONSTRAINT `FK_rolepermission_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    CONSTRAINT `FK_rolepermission_PERMISSION` FOREIGN KEY(`permission_id`) REFERENCES `permission`(`id`)
);


/*
Records for role_permission
*/
BEGIN;
INSERT INTO `role_permission` VALUES('2', '21'), ('2', '26'), ('2', '31'), ('2', '36'), ('3', '37'), ('5', '37'), ('3', '42'), ('5', '42'), ('2', '43'), ('2', '44'), ('3', '44'), ('2', '45'), ('2', '46'), ('2', '47'), ('2', '49'), ('3', '49'), ('5', '50'), ('2', '51'), ('3', '51'), ('4', '51'), ('2', '52'), ('4', '52'), ('2', '53'), ('4', '53'), ('2', '54'), ('4', '54'), ('2', '56'), ('3', '56'), ('4', '56'), ('5', '57'), ('2', '58'), ('4', '58'), ('5', '58');
COMMIT;


/*
Table structure for employee_role
*/
CREATE TABLE `employee_role`(
	`employee_id` INT unsigned NOT NULL,
    `role_id` INT unsigned NOT NULL,
    PRIMARY KEY(`employee_id`,`role_id`),
    CONSTRAINT `FK_employeerole_EMPLOYEE` FOREIGN KEY(`employee_id`) REFERENCES `employee`(`id`),
    CONSTRAINT `FK_employeerole_ROLE` FOREIGN KEY(`role_id`) REFERENCES `role`(`id`)
);

/*
Records for employee_role
*/
BEGIN;
INSERT INTO `employee_role` VALUES('6', '2'), ('7', '2'), ('3', '3'), ('5', '3'), ('2', '4'), ('4', '5');
COMMIT;


/*
Table structure for brandName
*/
CREATE TABLE `brandName`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
	PRIMARY KEY(`id`)
);
/*
Records for brandName
*/
BEGIN;
INSERT INTO `brandName` VALUES('1','LESSO'), ('2', 'Hikvision'), ('3', 'Samsung'), ('4', 'Nippon'), ('5', 'Western Digital'), ('6', 'Marco Polo tile'), ('7', 'Green Island Cement'), ('8', 'Philips');
COMMIT;


/*
Table structure for supplier
*/
CREATE TABLE `supplier`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
    `address` varchar(200) NOT NULL,
    `phone` varchar(50) NOT NULL,
	PRIMARY KEY(`id`)
);
/*
Records for supplier
*/
BEGIN;
INSERT INTO `supplier` VALUES ('1', 'ABC Paint Company Limited', 'Hong Kong', '23456789'), ('2', 'ABC Technology Company Limited', 'Kowloon', '98765432'), ('3', 'ABC Building Material Supply Company Limited', 'New Territories', '91234567'), ('4', 'ABC Distributor Company Limited', 'Hong Kong', '92345678');
COMMIT;


/*
Table structure for product
*/
CREATE TABLE `product`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
    `costPrice` decimal(19,2) NOT NULL,
    `brandName_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`brandName_id`) REFERENCES `brandName`(`id`)
);
/*
Records for product
*/
BEGIN;
INSERT INTO `product` VALUES ('1', 'Solar road light', '1000.00', '8'), ('2', 'Portable CO2 fire extinguisher', '50.00', '1'), ('3', 'PVC-U Water Supply Pipe', '60.00', '1'), ('4', 'Water-type Valve', '300.00', '1'), ('5', 'Green Island Cement', '30.00', '7'), ('6', 'Network camera', '325.00', '2'), ('7', 'Network switches', '3000.00', '2'), ('8', 'Door entry card terminals', '200.00', '3'), ('9', 'Quartz stone', '400.00', '1'), ('10', 'Nippon paint', '700.00', '4'), ('11', 'Nippon paint brush', '15.00', '4'), ('12', 'Hard disk 4TB', '500.00', '5'), ('13', 'Marco Polo Tile', '40.00', '6'), ('14', 'Monitor', '900.00', '8');
COMMIT;


/*
Table structure for constructionSite
*/
CREATE TABLE `constructionSite`(
	`id` INT unsigned NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
    `address` varchar(200) NOT NULL,
    PRIMARY KEY(`id`)
);
/*
Records for constructionSite
*/
BEGIN;
INSERT INTO `constructionSite` VALUES ('1', 'Housing Estate A', 'Hong Kong'), ('2', 'Housing Estate B', 'Kowloon'), ('3', 'Housing Estate C', 'New Territories');
COMMIT;


/*
Table structure for procurementRequest
*/
CREATE TABLE `procurementRequest`(
	`id` INT unsigned NOT NULL auto_increment,
    `serialNumber` varchar(100) NOT NULL,
    `status` tinyint NOT NULL DEFAULT 0,
    `totalAmount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `inputDate` datetime NOT NULL,
    `finishDate` datetime DEFAULT NULL,
    `supplier_id` INT unsigned NOT NULL,
    `inputUser_id` INT unsigned NOT NULL,
    `merchandiser_id` INT unsigned DEFAULT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_procurementRequest_SUPPLIER` FOREIGN KEY(`supplier_id`) REFERENCES `supplier`(`id`),
    CONSTRAINT `FK_procurementRequest_INPUTUSER` FOREIGN KEY(`inputUser_id`) REFERENCES `employee`(`id`),
    CONSTRAINT `FK_procurementRequest_MERCHANDISER` FOREIGN KEY(`merchandiser_id`) REFERENCES `employee`(`id`)
);
/*
Records for procurementRequest
*/
BEGIN;
INSERT INTO `procurementRequest` VALUES('1', '001', '1', '25.00', '14075.00', '2023-08-23', '2023-08-24', '1', '6', '7'), ('2', '002', '1', '115.00', '56500.00', '2023-08-24', '2023-08-25', '2', '7', '6'), ('3', '003', '0', '92.00', '11850.00', '2023-09-01', null, '3', '7', null), ('4', '004', '0', '2000.00', '80000.00', '2023-09-23', null, '4', '6', null);
COMMIT;



/*
Table structure for procurementRequestItem
*/
CREATE TABLE `procurementRequestItem`(
	`id` INT unsigned NOT NULL auto_increment,
    `costPrice` decimal(19,2) NOT NULL,
    `amount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `product_id` INT unsigned NOT NULL,
    `procurementRequest_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_procurementRequestItem_PRODUCT` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_procurementRequestItem_PROCUREMENTREQUEST` FOREIGN KEY(`procurementRequest_id`) REFERENCES `procurementRequest`(`id`)
);
/*
Records for procurementRequestItem
*/
BEGIN;
INSERT INTO `procurementRequestItem` VALUES ('1', '700.00', '20.00', '14000.00', '10', '1'), ('2', '15.00', '5.00', '75.00', '11', '1'), ('3', '325.00', '80.00', '26000.00', '6', '2'), ('4', '3000.00', '5.00', '15000.00', '7', '2'), ('5', '500.00', '20.00', '10000.00', '12', '2'), ('6', '900.00', '5.00', '4500.00', '14', '2'), ('7', '30.00', '20.00', '600.00', '5', '3'), ('8', '50.00', '5.00', '250.00', '2', '3'), ('9', '60.00', '40.00', '2400.00', '3', '3'), ('10', '300.00', '22.00', '6600.00', '4', '3'), ('11', '400.00', '5.00', '2000.00', '9', '3'), ('12', '200.00', '5.00', '1000.00', '8', '2'), ('13', '40.00', '2000.00', '80000.00', '13', '4');
COMMIT;


/*
Table structure for procurementRequest_items
*/
CREATE TABLE `procurementRequest_items`(
	`procurementRequest_id` INT unsigned NOT NULL,
    `items_id` INT unsigned NOT NULL,
    PRIMARY KEY(`procurementRequest_id`,`items_id`),
    CONSTRAINT `FK_procurementitems_PROCUREMENTREQUEST` FOREIGN KEY(`procurementRequest_id`) REFERENCES `procurementRequest`(`id`),
    CONSTRAINT `FK_procurementitems_ITEMS` FOREIGN KEY(`items_id`) REFERENCES `procurementRequestItem`(`id`)
);
/*
Records for procurementRequest_items
*/
BEGIN;
INSERT INTO `procurementRequest_items` VALUES ('1', '1'), ('1', '2'), ('2', '3'), ('2', '4'), ('2', '5'), ('2', '6'), ('2', '12'), ('3', '7'), ('3', '8'), ('3', '9'), ('3', '10'), ('3', '11'), ('4', '13');
COMMIT;



/*
Table structure for inBoundOrder
*/
CREATE TABLE `inBoundOrder`(
	`id` INT unsigned NOT NULL auto_increment,
    `serialNumber` varchar(100) NOT NULL,
    `status` tinyint NOT NULL DEFAULT 0,
    `totalAmount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `inBoundDate` datetime NOT NULL,
    `auditDate` datetime DEFAULT NULL,
    `inputUser_id` INT unsigned NOT NULL,
    `auditor_id` INT unsigned DEFAULT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_inBoundOrder_INPUTUSER` FOREIGN KEY(`inputUser_id`) REFERENCES `employee`(`id`),
    CONSTRAINT `FK_inBoundOrder_AUDITOR` FOREIGN KEY(`auditor_id`) REFERENCES `employee`(`id`)
);
/*
Records for inBoundOrder
*/
BEGIN;
INSERT INTO `inBoundOrder` VALUES('1', '001', '0', '20.00', '10575.00', '2023-08-27', null, '6', null), ('2', '002', '0', '5.00', '3500.00', '2023-08-28', null, '6', null), ('3', '003', '1', '115.00', '56500.00', '2023-08-29', '2023-08-29', '7', '4'), ('4', '004', '1', '1500.00', '60000.00', '2023-09-28', '2023-09-28', '7', '4'), ('5', '005', '1', '59.00', '5770.00', '2023-08-20', '2023-08-20', '6', '4'), ('6', '006', '1', '20.00', '20000.00', '2023-08-28', '2023-08-28', '6', '4'), ('7', '007', '1', '5.00', '2000.00', '2023-09-01', '2023-09-01', '6', '4');
COMMIT;



/*
Table structure for inBoundOrderItem
*/
CREATE TABLE `inBoundOrderItem`(
	`id` INT unsigned NOT NULL auto_increment,
    `costPrice` decimal(19,2) NOT NULL,
    `amount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `product_id` INT unsigned NOT NULL,
    `inBoundOrder_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_inBoundOrderItem_PRODUCT` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_inBoundOrderItem_INBOUNDORDER` FOREIGN KEY(`inBoundOrder_id`) REFERENCES `inBoundOrder`(`id`)
);
/*
Records for inBoundOrderItem
*/
BEGIN;
INSERT INTO `inBoundOrderItem` VALUES('1', '700.00', '15.00', '10500.00', '10', '1'), ('2', '15.00', '5.00', '75.00', '11', '1'), ('3', '700.00', '5.00', '3500.00', '10', '2'), ('4', '325.00', '80.00', '26000.00', '6', '3'), ('5', '3000.00', '5.00', '15000.00', '7', '3'), ('6', '500.00', '20.00', '10000.00', '12', '3'), ('7', '900.00', '5.00', '4500.00', '14', '3'), ('8', '200.00', '5.00', '1000.00', '8', '3'), ('9', '40.00', '1500.00', '60000.00', '13', '4'), ('10', '50.00', '5.00', '250.00', '2', '5'), ('11', '60.00', '22.00', '1320.00', '3', '5'), ('12', '300.00', '12.00', '3600.00', '4', '5'), ('13', '30.00', '20.00', '600.00', '5', '5'), ('14', '1000.00', '20.00', '20000.00', '1', '6'), ('15', '400.00', '5.00', '2000.00', '9', '7');
COMMIT;


/*
Table structure for inBoundOrder_items
*/
CREATE TABLE `inBoundOrder_items`(
	`inBoundOrder_id` INT unsigned NOT NULL,
    `items_id` INT unsigned NOT NULL,
    PRIMARY KEY(`inBoundOrder_id`,`items_id`),
    CONSTRAINT `FK_inBoundOrderItems_INBOUNDORDER` FOREIGN KEY(`inBoundOrder_id`) REFERENCES `inBoundOrder`(`id`),
    CONSTRAINT `FK_inBoundOrderItems_ITEMS` FOREIGN KEY(`items_id`) REFERENCES `inBoundOrderItem`(`id`)
);
/*
Records for inBoundOrder_items
*/
BEGIN;
INSERT INTO `inBoundOrder_items` VALUES('1', '1'), ('1', '2'), ('2', '3'), ('3', '4'), ('3', '5'), ('3', '6'), ('3', '7'), ('3', '8'), ('4', '9'), ('5', '10'), ('5', '11'), ('5', '12'), ('5', '13'), ('6', '14'), ('7', '15');
COMMIT;



/*
Table structure for inventory
*/
CREATE TABLE `inventory`(
	`id` INT unsigned NOT NULL auto_increment,
    `inventoryAmount` decimal(19,2) NOT NULL,
    `averagePrice` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `product_id` INT unsigned NOT NULL UNIQUE,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`product_id`) REFERENCES `product`(`id`)
);
/*
Records for inventory
*/
BEGIN;
INSERT INTO `inventory` VALUES ('1', '40.00', '325.00', '13000.00', '6'), ('2', '3.00', '3000.00', '9000.00', '7'), ('3', '12.00', '500.00', '6000.00', '12'), ('4', '3.00', '900.00', '2700.00', '14'), ('5', '3.00', '200.00', '600.00', '8'), ('6', '500.00', '40.00', '20000.00', '13'), ('7', '3.00', '50.00', '150.00', '2'), ('8', '22.00', '60.00', '1320.00', '3'), ('9', '12.00', '300.00', '3600.00', '4'), ('10', '20.00', '30.00', '600.00', '5'), ('11', '10.00', '1000.00', '10000.00', '1'), ('12', '5.00', '400.00', '2000.00', '9');
COMMIT;



/*
Table structure for outBoundOrder
*/
CREATE TABLE `outBoundOrder`(
	`id` INT unsigned NOT NULL auto_increment,
    `serialNumber` varchar(100) NOT NULL,
    `status` tinyint NOT NULL DEFAULT 0,
    `totalAmount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `outBoundDate` datetime NOT NULL,
    `auditDate` datetime DEFAULT NULL,
    `inputUser_id` INT unsigned NOT NULL,
    `auditor_id` INT unsigned DEFAULT NULL,
    `constructionSite_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_outBoundOrder_INPUTUSER` FOREIGN KEY(`inputUser_id`) REFERENCES `employee`(`id`),
    CONSTRAINT `FK_outBoundOrder_AUDITOR` FOREIGN KEY(`auditor_id`) REFERENCES `employee`(`id`),
    CONSTRAINT `FK_outBoundOrder_CONSTRUCTIONSITE` FOREIGN KEY(`constructionSite_id`) REFERENCES `constructionSite`(`id`)
);
/*
Records for outBoundOrder
*/
BEGIN;
INSERT INTO `outBoundOrder` VALUES ('1', '001', '1', '54.00', '25200.00', '2023-09-01', '2023-09-01', '2', '4', '1'), ('2', '002', '0', '22.00', '2620.00', '2023-08-23', null, '2', null, '1'), ('3', '003', '1', '12.00', '10100.00', '2023-08-29', '2023-08-29', '2', '4', '2'), ('4', '004', '0', '27.00', '12600.00', '2023-09-02', null, '2', null, '2'), ('5', '005', '1', '1000.00', '40000.00', '2023-09-29', '2023-09-29', '2', '4', '3');
COMMIT;



/*
Table structure for outBoundOrderItem
*/
CREATE TABLE `outBoundOrderItem`(
	`id` INT unsigned NOT NULL auto_increment,
    `costPrice` decimal(19,2) NOT NULL,
    `amount` decimal(19,2) NOT NULL,
    `totalPrice` decimal(19,2) NOT NULL,
    `product_id` INT unsigned NOT NULL,
    `outBoundOrder_id` INT unsigned NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `FK_outBoundOrderItem_PRODUCT` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_outBoundOrderItem_OUTBOUNDORDER` FOREIGN KEY(`outBoundOrder_id`) REFERENCES `outBoundOrder`(`id`)
);
/*
Records for outBoundOrderItem
*/
BEGIN;
INSERT INTO `outBoundOrderItem` VALUES ('1', '325.00', '40.00', '13000.00', '6', '1'), ('2', '3000.00', '2.00', '6000.00', '7', '1'), ('3', '500.00', '8.00', '4000.00', '12', '1'), ('4', '900.00', '2.00', '1800.00', '14', '1'), ('5', '200.00', '2.00', '400.00', '8', '1'), ('6', '50.00', '2.00', '100.00', '2', '2'), ('7', '60.00', '10.00', '600.00', '3', '2'), ('8', '300.00', '6.00', '1800.00', '4', '2'), ('9', '30.00', '4.00', '120.00', '5', '2'), ('10', '1000.00', '10.00', '10000.00', '1', '3'), ('11', '50.00', '2.00', '100.00', '2', '3'), ('12', '325.00', '20.00', '6500.00', '6', '4'), ('13', '3000.00', '1.00', '3000.00', '7', '4'), ('14', '500.00', '4.00', '2000.00', '12', '4'), ('15', '900.00', '1.00', '900.00', '14', '4'), ('16', '200.00', '1.00', '200.00', '8', '4'), ('17', '40.00', '1000.00', '40000.00', '13', '5');
COMMIT;



/*
Table structure for outBoundOrder_items
*/
CREATE TABLE `outBoundOrder_items`(
	`outBoundOrder_id` INT unsigned NOT NULL,
    `items_id` INT unsigned NOT NULL,
    PRIMARY KEY(`outBoundOrder_id`,`items_id`),
    CONSTRAINT `FK_outBoundOrderitems_OUTBOUNDORDER` FOREIGN KEY(`outBoundOrder_id`) REFERENCES `outBoundOrder`(`id`),
    CONSTRAINT `FK_outBoundOrderitems_ITEMS` FOREIGN KEY(`items_id`) REFERENCES `outBoundOrderItem`(`id`)
);
/*
Records for outBoundOrder_items
*/
BEGIN;
INSERT INTO `outBoundOrder_items` VALUES ('1', '1'), ('1', '2'), ('1', '3'), ('1', '4'), ('1', '5'), ('2', '6'), ('2', '7'), ('2', '8'), ('2', '9'), ('3', '10'), ('3', '11'), ('4', '12'), ('4', '13'), ('4', '14'), ('4', '15'), ('4', '16'), ('5', '17');
COMMIT;
