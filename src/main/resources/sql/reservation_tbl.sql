CREATE TABLE `reservation` (
    `id` INTEGER PRIMARY KEY,
    `userId` INTEGER NOT NULL,
    `roomId` INTEGER NOT NULL,
    `bookTime` TEXT NOT NULL,
    `arrival` TEXT NOT NULL,
    `departure` TEXT NOT NULL,
    `status` TEXT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
    FOREIGN KEY (`roomId`) REFERENCES `room` (`id`)
);