-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dogeverse
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dogeverse
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dogeverse` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `dogeverse` ;

-- -----------------------------------------------------
-- Table `dogeverse`.`achievement_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`achievement_entity` (
  `id` BINARY(16) NOT NULL,
  `advanced` INT NOT NULL,
  `basic` INT NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `intermediate` INT NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `successes` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`audit_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`audit_log` (
  `audit_id` INT NOT NULL AUTO_INCREMENT,
  `table_name` VARCHAR(255) NULL DEFAULT NULL,
  `operation` VARCHAR(10) NULL DEFAULT NULL,
  `changed_id` BLOB NULL DEFAULT NULL,
  `change_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`audit_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 85
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`character_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`character_entity` (
  `id` BINARY(16) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`stat_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`stat_entity` (
  `id` BINARY(16) NOT NULL,
  `stat_type` ENUM('DEXTERITY', 'INTELLIGENCE', 'STRENGTH') NULL DEFAULT NULL,
  `stat_value` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`character_stat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`character_stat` (
  `character_entity_id` BINARY(16) NOT NULL,
  `stat_entity_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`character_entity_id`, `stat_entity_id`),
  UNIQUE INDEX `UKrdxjhrn3aevdj1u99g5lv338r` (`stat_entity_id` ASC) VISIBLE,
  CONSTRAINT `FK2m7t3cxh7km3pyfpop6bjswem`
    FOREIGN KEY (`stat_entity_id`)
    REFERENCES `dogeverse`.`stat_entity` (`id`),
  CONSTRAINT `FKt4eyxrjxpt7pdsu9mk2aio0ex`
    FOREIGN KEY (`character_entity_id`)
    REFERENCES `dogeverse`.`character_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`dog_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`dog_entity` (
  `breed` VARCHAR(255) NULL DEFAULT NULL,
  `id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKoopc09hm5cx4t0top54swqm9a`
    FOREIGN KEY (`id`)
    REFERENCES `dogeverse`.`character_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`skill_base_data_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`skill_base_data_entity` (
  `id` BINARY(16) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `stat_type` ENUM('DEXTERITY', 'INTELLIGENCE', 'STRENGTH') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`skill_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`skill_entity` (
  `id` BINARY(16) NOT NULL,
  `base_value` INT NOT NULL,
  `successes` INT NOT NULL,
  `total_value` INT NOT NULL,
  `skill_base_data_id` BINARY(16) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK2rbkxmm4m4stjxlbbm9m8sh0f` (`skill_base_data_id` ASC) VISIBLE,
  CONSTRAINT `FK2rbkxmm4m4stjxlbbm9m8sh0f`
    FOREIGN KEY (`skill_base_data_id`)
    REFERENCES `dogeverse`.`skill_base_data_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`dog_skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`dog_skill` (
  `dog_entity_id` BINARY(16) NULL DEFAULT NULL,
  `skill_entity_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`skill_entity_id`),
  INDEX `FKfr32sqvojuw0qvte4l8q07viy` (`dog_entity_id` ASC) VISIBLE,
  CONSTRAINT `FK4mcy7ook0p6ejurpl2k3r6khr`
    FOREIGN KEY (`skill_entity_id`)
    REFERENCES `dogeverse`.`skill_entity` (`id`),
  CONSTRAINT `FKfr32sqvojuw0qvte4l8q07viy`
    FOREIGN KEY (`dog_entity_id`)
    REFERENCES `dogeverse`.`dog_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`trainer_entity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`trainer_entity` (
  `id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKc2k93eoxs3qog8vn7h3ea7rp7`
    FOREIGN KEY (`id`)
    REFERENCES `dogeverse`.`character_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dogeverse`.`trainer_dogs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dogeverse`.`trainer_dogs` (
  `dog_entity_id` BINARY(16) NULL DEFAULT NULL,
  `trainer_entity_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`trainer_entity_id`),
  INDEX `FK964cnm6uxar4crafg20028stq` (`dog_entity_id` ASC) VISIBLE,
  CONSTRAINT `FK8ohkbdy2crdtuk7ayqgp3mqmx`
    FOREIGN KEY (`trainer_entity_id`)
    REFERENCES `dogeverse`.`dog_entity` (`id`),
  CONSTRAINT `FK964cnm6uxar4crafg20028stq`
    FOREIGN KEY (`dog_entity_id`)
    REFERENCES `dogeverse`.`trainer_entity` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `dogeverse`;

DELIMITER $$
USE `dogeverse`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `dogeverse`.`log_delete_dog_entity`
AFTER DELETE ON `dogeverse`.`dog_entity`
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, operation, changed_id)
    VALUES ('dog_entity', 'DELETE', OLD.id);
END$$

USE `dogeverse`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `dogeverse`.`log_insert_dog_entity`
AFTER INSERT ON `dogeverse`.`dog_entity`
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, operation, changed_id)
    VALUES ('dog_entity', 'INSERT', NEW.id);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
