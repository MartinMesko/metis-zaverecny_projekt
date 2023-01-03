CREATE TABLE `librarydb`.`dvd`
(
    `Id`               INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Author`           VARCHAR(45) NULL,
    `Name`             VARCHAR(45) NULL,
    `AvailableCopies`  INT NULL,
    `NumberOfChapters` INT NULL,
    `NumberOfMinutes`  INT NULL,
    PRIMARY KEY (`Id`)
);

CREATE TABLE `librarydb`.`book`
(
    `Id`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Author`          VARCHAR(45) NULL,
    `Name`            VARCHAR(45) NULL,
    `AvailableCopies` INT NULL,
    `NumberOfPages`   INT NULL,
    `ISBN`            VARCHAR(45) NULL,
    PRIMARY KEY (`Id`)
);

CREATE TABLE `librarydb`.`members`
(
    `Id`          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `FirstName`   VARCHAR(45) NULL,
    `LastName`    VARCHAR(45) NULL,
    `PersonalId`  VARCHAR(45) NULL,
    `DateOfBirth` VARCHAR(45) NULL,
    PRIMARY KEY (`Id`)
);

CREATE TABLE `librarydb`.`title`
(
    `Id`                   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Author`               VARCHAR(45) NULL,
    `Name`                 VARCHAR(45) NULL,
    `AvailableCopies`      INT NULL,
    `Discriminator`        VARCHAR(45) NULL,
    `NumberOfPages`        INT NULL,
    `ISBN`                 VARCHAR(45) NULL,
    `NumberOfChapters`     INT NULL,
    `NumberOfMinutes`      INT NULL,
    `TotalAvailableCopies` INT NULL,
    PRIMARY KEY (`Id`)
);

CREATE TABLE `librarydb`.`rental_entries`
(
    `Id`             INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `MemberId`       INT UNSIGNED NULL,
    `RentedDate`     VARCHAR(45) NULL,
    `ReturnDate`     VARCHAR(45) NULL,
    `TitleId`        INT UNSIGNED NULL,
    `TimesProlonged` INT NULL,
    `TitleType`      INT NULL,
    PRIMARY KEY (`Id`),
    FOREIGN KEY (TitleId) REFERENCES title (Id),
    FOREIGN KEY (MemberId) REFERENCES members (Id)
);

CREATE TABLE `librarydb`.`queue_items`
(
    `Id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `MemberId`   INT UNSIGNED NULL,
    `TimeAdded`  VARCHAR(45) NULL,
    `TitleId`    INT UNSIGNED NULL,
    `isResolved` TINYINT NULL,
    PRIMARY KEY (`Id`),
    FOREIGN KEY (TitleId) REFERENCES title (Id),
    FOREIGN KEY (MemberId) REFERENCES members (Id)
);

CREATE TABLE `librarydb`.`message`
(
    `Id`             INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `MemberId`       INT UNSIGNED NULL,
    `MessageContext` VARCHAR(45) NULL,
    `MessageSubject` VARCHAR(45) NULL,
    `SendData`       VARCHAR(45) NULL,
    PRIMARY KEY (`Id`),
    FOREIGN KEY (MemberId) REFERENCES members (Id)
);

INSERT INTO librarydb.members
(FirstName,
 LastName,
 PersonalId,
 DateOfBirth)
VALUES ('Marcus', 'Salazar', '1', '11.06.1988'),
       ('Amanda', 'Cunningham', '2', '11.06.1988'),
       ('Michael', 'Jackson', '3', '11.06.1988'),
       ('Danny', 'Humphrey', '4', '11.06.1988'),
       ('Nicholas', 'Kim', '5', '11.06.1988'),
       ('Jacqueline', 'Dickson', '6', '11.06.1988'),
       ('Lisa', 'Brady', '7', '11.06.1988'),
       ('Matthew', 'Jensen', '8', '11.06.1988'),
       ('Christian', 'Gallegos', '9', '11.06.1988'),
       ('Donna', 'Russell', '10', '11.06.1988');


INSERT INTO `librarydb`.`book` (`Author`, `Name`, `AvailableCopies`, `NumberOfPages`, `ISBN`)
VALUES ('Harper Lee', 'To Kill a Mockingbird', 3, 500, '9781234567897'),
       ('Leo Tolstoy', 'War and Peace', 5, 620, '7754611466715'),
       ('William Faulkner', 'The Sound and the Fury', 7, 205, '5905746069865'),
       ('George Orwell', 'Nineteen Eighty-Four', 2, 320, '3371649728152'),
       ('William Faulkner', 'As I Lay Dying', 1, 450, '0322988088678');

INSERT INTO `librarydb`.`dvd` (`Author`, `Name`, `AvailableCopies`, `NumberOfChapters`, `NumberOfMinutes`)
VALUES ('Enrico Casarosa', 'Luca', 5, 6, 110),
       ('Clint Eastwood', 'The Mule', 5, 6, 115),
       ('Scott Mosier and Yarrow Cheney', 'Dr. Seuss’ The Grinch', 5, 6, 90),
       ('Shawn Levy', 'Free Guy', 5, 6, 130),
       ('Roger Allers and Rob Minkoff', 'The Lion King', 5, 6, 140);

INSERT INTO `librarydb`.`rental_entries`
(`MemberId`, `RentedDate`, `ReturnDate`, `TitleId`, `TimesProlonged`, `TitleType`)
VALUES (1, 'day1', 'day2', 1, 0, 1);

INSERT INTO `librarydb`.`rental_entries`
(`MemberId`, `RentedDate`, `ReturnDate`, `TitleId`, `TimesProlonged`, `TitleType`)
VALUES (2, '31.12.2000', null, 1, 0, 0);

INSERT INTO librarydb.title(Author, Name, AvailableCopies, Discriminator, NumberOfPages, ISBN, NumberOfChapters, NumberOfMinutes, TotalAvailableCopies)
VALUES
    ('Harper Lee','To Kill a Mockingbird', 3, 'Book', 500, '9781234567897', NULL, NULL, 0),
    ('Leo Tolstoy','War and Peace', 5, 'Book', 620, '7754611466715', NULL, NULL, 0),
    ('William Faulkner','The Sound and the Fury', 7, 'Book', 205, '5905746069865', NULL, NULL, 0),
    ('George Orwell','Nineteen Eighty-Four', 2, 'Book', 320, '3371649728152', NULL, NULL, 0),
    ('William Faulkner','As I Lay Dying', 1, 'Book', 450, '0322988088678', NULL, NULL, 0),
    ('Enrico Casarosa','Luca', 5, 'Dvd', NULL, NULL, 6, 110, 0),
    ('Clint Eastwood','The Mule', 5, 'Dvd', NULL, NULL,6, 115, 0),
    ('Scott Mosier and Yarrow Cheney','Dr. Seuss’ The Grinch', 5, 'Dvd', NULL, NULL, 6, 90, 0),
    ('Shawn Levy','Free Guy', 5, 'Dvd', NULL, NULL, 6, 130, 0),
    ('Roger Allers and Rob Minkoff','The Lion King', 5,'Dvd', NULL, NULL, 6, 140, 0);


SELECT *
FROM librarydb.rental_entries
         JOIN librarydb.members ON rental_entries.MemberId = members.Id
         JOIN librarydb.title ON rental_entries.TitleId = title.Id;

SELECT *
FROM librarydb.rental_entries;
SELECT *
FROM librarydb.members;
SELECT *
FROM librarydb.title;
