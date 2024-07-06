CREATE DATABASE IF NOT EXISTS `account_db`;
USE `account_db`;

CREATE TABLE `account` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `document` varchar(255),
  `user_id` bigint,
  `booking_id` bigint,
  PRIMARY KEY (`account_id`),
  FOREIGN KEY (user_id) REFERENCES user_db.user(user_id) ON DELETE SET NULL ON UPDATE CASCADE
);
