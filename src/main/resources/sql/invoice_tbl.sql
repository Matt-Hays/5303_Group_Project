CREATE TABLE `invoice` (
	`id`	INTEGER NOT NULL,
	`reservationId`	INTEGER NOT NULL,
	`taxRate`	REAL NOT NULL,
	`fees`	REAL NOT NULL,
	`subTotal`	REAL NOT NULL,
	`isPaid`	INTEGER NOT NULL,
	FOREIGN KEY(`reservationId`) REFERENCES `reservation` (`id`),
	PRIMARY KEY(`id`, `reservationId`)
);