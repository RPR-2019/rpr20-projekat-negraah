BEGIN TRANSACTION;
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
INSERT INTO `owner` VALUES (0,'New','Customer', NULL, 0, '', '');
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
INSERT INTO `vehicle` VALUES (1,'E22-022','Range Rover', 'Land Rover', 'B', 1);
INSERT INTO `vehicle` VALUES (2,'B11-555','Q7', 'Audi', 'B', 1);
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