CREATE DATABASE IF NOT EXISTS `userdb`;
USE `userdb`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`userId` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `lastname` varchar(255) NOT NULL,
    `address` varchar(255) DEFAULT NULL,
    `email` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`userId`)
);
