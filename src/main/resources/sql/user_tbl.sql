CREATE TABLE `user` (
	`id` INTEGER PRIMARY KEY AUTOINCREMENT,
	`type` TEXT NOT NULL,
	`username` TEXT NOT NULL UNIQUE,
	`password` TEXT NOT NULL,
	`firstName` TEXT,
	`lastName` TEXT,
	`email` TEXT,
	`street` TEXT,
	`state` TEXT,
	`zip` TEXT,
	`country` TEXT,
	`phone` TEXT,
	`billingStreet` TEXT,
	`billingState` TEXT,
	`billingZip` TEXT,
	`billingCountry` TEXT,
	`active` INTEGER NOT NULL
);