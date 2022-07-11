CREATE TABLE `invoice` (
	`id`	TEXT NOT NULL,
	`taxRate`	REAL NOT NULL,
	`fees`	REAL NOT NULL,
	`subTotal`	REAL NOT NULL,
	`isPaid`	INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);