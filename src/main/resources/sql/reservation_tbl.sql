CREATE TABLE `reservation` (
    `id` TEXT NOT NULL,
    `customerId` TEXT NOT NULL,
    `roomId` INTEGER NOT NULL,
    `invoiceId` TEXT,
    `createdAt` TEXT NOT NULL,
    `arrival` TEXT NOT NULL,
    `departure` TEXT NOT NULL,
    `status` TEXT NOT NULL,
    FOREIGN KEY (`customerId`) REFERENCES `user` (`id`),
    FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),
    FOREIGN KEY (`invoiceId`) REFERENCES `invoice` (`id`),
    PRIMARY KEY(`id`)
);