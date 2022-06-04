CREATE TABLE `room` (
    `id` INTEGER PRIMARY KEY,
    `bedType` TEXT NOT NULL,
    `numBeds` INTEGER NOT NULL,
    `smoking` INTEGER NOT NULL,
    `occupied` INTEGER NOT NULL
);