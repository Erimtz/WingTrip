CREATE DATABASE IF NOT EXISTS `account_db`;
USE `account_db`;

CREATE TABLE `account` (
  `accountId` bigint NOT NULL AUTO_INCREMENT,
  `document` varchar(255),
  `userId` bigint,
  `bookingId` bigint,
  PRIMARY KEY (`accountId`),
  FOREIGN KEY (userId) REFERENCES userdb.user(userId) ON DELETE SET NULL ON UPDATE CASCADE
);
