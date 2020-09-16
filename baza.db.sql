BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `grad` (
	`id`	INTEGER,
	`naziv`	TEXT,
	`broj_stanovnika`	INTEGER,
	`drzava`	INTEGER,
	FOREIGN KEY(`drzava`) REFERENCES `drzava`,
	PRIMARY KEY(`id`)
);
INSERT INTO `grad` VALUES (1,'Pariz',2206488,1);
INSERT INTO `grad` VALUES (2,'London',8825000,2);
INSERT INTO `grad` VALUES (3,'Beč',1899055,3);
INSERT INTO `grad` VALUES (4,'Mančester',545500,2);
INSERT INTO `grad` VALUES (5,'Grac',280200,3);
CREATE TABLE IF NOT EXISTS `drzava` (
	`id`	INTEGER,
	`naziv`	TEXT,
	`glavni_grad`	INTEGER,
	PRIMARY KEY(`id`)
);
INSERT INTO `drzava` VALUES (1,'Francuska',1);
INSERT INTO `drzava` VALUES (2,'Velika Britanija',2);
INSERT INTO `drzava` VALUES (3,'Austrija',3);
CREATE TABLE IF NOT EXISTS `pobratimi` (
	`id`	INTEGER,
	`idA`	INTEGER,
	`idB`	INTEGER,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `owner` (
	`id`	INTEGER,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`date_of_birth` DATE,
	`upin` INTEGER,
	`adress` TEXT,
	`phone_number` TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `owner` VALUES (1,'Negra','Ahmetspahić', NULL, 123, 'Kovaci 21', '062357244');
INSERT INTO `owner` VALUES (2,'Adnan','Šabanović', NULL, 567, 'Ciglane 24', '062813814');
CREATE TABLE IF NOT EXISTS `vehicle` (
	`id`	INTEGER,
	`plates`	TEXT,
	`model`	TEXT,
	`manufacturer` TEXT,
	`category` TEXT,
	`owner_id` INTEGER,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `checkup` (
	`id`	INTEGER,
	`vehicle_id` INTEGER,
	`checkup_time`	DATE,
	`passed_engine` BOOLEAN,
	`passed_brakes` BOOLEAN,
	`passed_emissions` BOOLEAN,
	`passed_accumulator` BOOLEAN,
	`passed_electronics` BOOLEAN,
	`passed_lighting` BOOLEAN,
	PRIMARY KEY(`id`)
);
COMMIT;