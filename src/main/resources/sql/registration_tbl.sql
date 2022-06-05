CREATE TABLE `registration` (
    `id` INTEGER PRIMARY KEY,
    `userId` INTEGER NOT NULL,
    `roomId` INTEGER NOT NULL,
    `bookTime` TEXT NOT NULL,
    `startTime` TEXT NOT NULL,
    `endTime` TEXT NOT NULL,
    `checkIn` TEXT,
    `checkOut` TEXT,
    FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
    FOREIGN KEY (`roomId`) REFERENCES `room` (`id`)
);