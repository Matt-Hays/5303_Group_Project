DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`userid` INTEGER PRIMARY KEY AUTOINCREMENT,
	`type` INTEGER NOT NULL,
	`username` TEXT NOT NULL UNIQUE,
	`password` TEXT NOT NULL,
	`firstName` TEXT,
	`lastName` TEXT,
	`active` INTEGER NOT NULL
);

INSERT INTO `user` (`type`, `username`, `password`, `active`) VALUES (4, 'admin', 'password123$', 1);