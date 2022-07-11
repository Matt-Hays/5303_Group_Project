CREATE TABLE `reservation` (
    `id` TEXT NOT NULL,
    `userId` TEXT NOT NULL,
    `roomId` INTEGER NOT NULL,
    `invoiceId` TEXT,
    `bookTime` TEXT NOT NULL,
    `arrival` TEXT NOT NULL,
    `departure` TEXT NOT NULL,
    `status` TEXT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
    FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),
    FOREIGN KEY (`invoiceId`) REFERENCES `invoice` (`id`),
    PRIMARY KEY(`id`)
);