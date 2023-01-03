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
    `DateOfBirth` date,
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

INSERT INTO librarydb.members
(FirstName,
 LastName,
 PersonalId,
 DateOfBirth)
VALUES ('Marcus', 'Salazar', '1', '1988-06-11 21:57:18'),
       ('Amanda', 'Cunningham', '2', '1988-09-10 07:51:56'),
       ('Michael', 'Jackson', '3', '2004-01-10 05:19:31'),
       ('Danny', 'Humphrey', '4', '1988-03-31 15:50:26'),
       ('Nicholas', 'Kim', '5', '1997-12-14 23:23:42'),
       ('Jacqueline', 'Dickson', '6', '2003-09-18 20:47:45'),
       ('Lisa', 'Brady', '7', '1994-07-15 05:32:26'),
       ('Matthew', 'Jensen', '8', '2003-09-06 13:31:06'),
       ('Christian', 'Gallegos', '9', '1992-07-25 11:45:41'),
       ('Donna', 'Russell', '10', '2004-08-10 23:21:17');


INSERT INTO `librarydb`.`book` (`Author`, `Name`, `AvailableCopies`, `NumberOfPages`, `ISBN`)
VALUES ('Harper Lee', 'To Kill a Mockingbird', 3, 500, '9781234567897'),
       ('Leo Tolstoy', 'War and Peace', 5, 620, 7754611466715),
       ('William Faulkner', 'The Sound and the Fury', 7, 205, 5905746069865),
       ('George Orwell', 'Nineteen Eighty-Four', 2, 320, 3371649728152),
       ('William Faulkner', 'As I Lay Dying', 1, 450, 0322988088678);

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

INSERT INTO `librarydb`.`title` (`Author`, `AvailableCopies`, `Discriminator`, `NumberOfPages`, `ISBN`,
                                 `NumberOfChapters`, `NumberOfMinutes`, `TotalAvailableCopies`, `Name`)
VALUES ('author', 2, 'Dvd', null, null, 3, 120, 2, 'rambo');

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
