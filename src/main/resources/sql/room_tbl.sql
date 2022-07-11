CREATE TABLE `room` (
	`id` INTEGER NOT NULL,
    `bedType` TEXT NOT NULL,
    `numBeds` INTEGER NOT NULL,
    `smoking` INTEGER NOT NULL,
    `occupied` INTEGER NOT NULL,
    `nightlyRate` REAL NOT NULL,
	PRIMARY KEY(`id`)
);