SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
DROP SCHEMA IF EXISTS `NextGifts` ;

CREATE SCHEMA IF NOT EXISTS `NextGifts` DEFAULT CHARACTER SET utf8 ;
USE `NextGifts` ;

-- -----------------------------------------------------
-- Table `NextGifts`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `NextGifts`.`user` ;

CREATE TABLE IF NOT EXISTS `NextGifts`.`user` (
  `firstName` VARCHAR(100) NOT NULL,
  `lastNames` VARCHAR(200) NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `startDate` TIMESTAMP NOT NULL,
  `endDate` TIMESTAMP NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `NextGifts`.`gift`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `NextGifts`.`gift` ;

CREATE TABLE IF NOT EXISTS `NextGifts`.`gift` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `image` VARCHAR(150) NULL,
  `description` VARCHAR(2000) NULL,
  `urls` VARCHAR(1000) NULL,
  `shop` VARCHAR(150) NULL,
  `price` DOUBLE NULL,
  `publicHashtags` VARCHAR(1000) NULL,
  `privateNotes` VARCHAR(2000) NULL,
  `privateHashtags` VARCHAR(1000) NULL,
  `gotIt` TINYINT(1) NOT NULL DEFAULT 0,
  `delivered` TINYINT(1) NOT NULL DEFAULT 0,
  `spread` TINYINT(1) NOT NULL DEFAULT 0,
  `anonymous` TINYINT(1) NOT NULL DEFAULT 0,
  `startDate` TIMESTAMP NOT NULL,
  `endDate` TIMESTAMP NULL,
  `spreadDate` TIMESTAMP NULL,
  `giftId` INT(11) NULL,
  `username` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `giftId` (`giftId` ASC),
  INDEX `username` (`username` ASC),
  CONSTRAINT `giftIdGift`
    FOREIGN KEY (`giftId`)
    REFERENCES `NextGifts`.`gift` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `usernameGift`
    FOREIGN KEY (`username`)
    REFERENCES `NextGifts`.`user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
