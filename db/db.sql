CREATE SCHEMA IF NOT EXISTS `floristicboom` DEFAULT CHARACTER SET utf8mb3 ;
USE `floristicboom` ;

CREATE TABLE IF NOT EXISTS `floristicboom`.`address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(40) NOT NULL,
  `street` VARCHAR(40) NOT NULL,
  `house` VARCHAR(5) NOT NULL,
  `apartment` VARCHAR(5) NOT NULL,
  `postal_code` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bonus` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `promocode` VARCHAR(45) NOT NULL,
  `discount` FLOAT NOT NULL,
  `duration_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`credentials` (
  `id` INT NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `role` ENUM('ADMIN', 'WORKER', 'CLIENT', 'CURIER') NOT NULL,
  `is_banned` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(12) NOT NULL,
  `image_uri` VARCHAR(255),
  `credentials_id` INT NOT NULL,
  PRIMARY KEY (`id`, `credentials_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `telefon_UNIQUE` (`phone` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_user_credentials` (`credentials_id` ASC),
  CONSTRAINT `fk_credentials_user`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `floristicboom`.`credentials` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bouquet` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `image_uri` VARCHAR(255),
  `wrapper_color` ENUM('RED', 'GREEN', 'BLUE', 'YELLOW', 'BLACK', 'WHITE', 'ORANGE', 'PURPLE', 'PINK', 'BROWN', 'GRAY', 'CYAN', 'MAGENTA', 'TEAL', 'LIME', 'OLIVE', 'NAVY', 'MAROON') NOT NULL,
  `is_custom` TINYINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_bouquet_user` (`user_id` ASC),
  CONSTRAINT `fk_user_bouquet`
    FOREIGN KEY (`user_id`)
    REFERENCES `floristicboom`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`delivery_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`delivery` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `delivery_date` DATETIME NOT NULL,
  `price` FLOAT NOT NULL,
  `curier_id` BIGINT NOT NULL,
  `delivery_type_id` BIGINT NOT NULL,
  `address_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_curier_delivery` (`curier_id` ASC),
  INDEX `fk_delivery_type_delivery` (`delivery_type_id` ASC),
  INDEX `fk_address_delivery` (`address_id` ASC),
  CONSTRAINT `fk_address_delivery`
    FOREIGN KEY (`address_id`)
    REFERENCES `floristicboom`.`address` (`id`),
  CONSTRAINT `fk_delivery_type_delivery`
    FOREIGN KEY (`delivery_type_id`)
    REFERENCES `floristicboom`.`delivery_type` (`id`),
  CONSTRAINT `fk_delivery_curier`
    FOREIGN KEY (`curier_id`)
    REFERENCES `floristicboom`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`purchase` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `payment_date` DATETIME NULL DEFAULT NULL,
  `price` FLOAT NOT NULL,
  `status` ENUM('COMPLETED', 'PLACED', 'DELIVERING') NOT NULL,
  `payment_type` ENUM('TRANSFER', 'ONLINE', 'OFFLINE') NULL DEFAULT NULL,
  `employee_id` BIGINT NOT NULL,
  `client_id` BIGINT NOT NULL,
  `delivery_id` BIGINT NULL,
  `bonus_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_client_purchase` (`client_id` ASC),
  INDEX `fk_employee_purchase` (`employee_id` ASC),
  INDEX `fk_delivery_purchase` (`delivery_id` ASC),
  INDEX `fk_bonus_purchase` (`bonus_id` ASC),
  CONSTRAINT `fk_bonus_purchase`
    FOREIGN KEY (`bonus_id`)
    REFERENCES `floristicboom`.`bonus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_delivery_purchase`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `floristicboom`.`delivery` (`id`),
  CONSTRAINT `fk_purchase_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `floristicboom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_employee`
    FOREIGN KEY (`employee_id`)
    REFERENCES `floristicboom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`purchase_bouquet` (
  `purchase_id` BIGINT NOT NULL,
  `bouquet_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`purchase_id`, `bouquet_id`),
  INDEX `fk_purchase_bouquet` (`purchase_id` ASC),
  INDEX `fk_bouquet_purchase` (`bouquet_id` ASC),
  CONSTRAINT `fk_bouquet_purchase`
    FOREIGN KEY (`bouquet_id`)
    REFERENCES `floristicboom`.`bouquet` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_purchase_bouquet`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `floristicboom`.`purchase` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`flower` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `image_uri` VARCHAR(255),
  `color` ENUM('RED', 'GREEN', 'BLUE', 'YELLOW', 'BLACK', 'WHITE', 'ORANGE', 'PURPLE', 'PINK', 'BROWN', 'GRAY', 'CYAN', 'MAGENTA', 'TEAL', 'LIME', 'OLIVE', 'NAVY', 'MAROON') NOT NULL,
  `available_quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bouquet_flower` (
  `bouquet_id` BIGINT NOT NULL,
  `flower_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`bouquet_id`, `flower_id`),
  INDEX `fk_flower_bouquet` (`flower_id` ASC),
  INDEX `fk_bouquet_flower` (`bouquet_id` ASC),
  CONSTRAINT `fk_bouquet_flower`
    FOREIGN KEY (`bouquet_id`)
    REFERENCES `floristicboom`.`bouquet` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_flower_bouquet`
    FOREIGN KEY (`flower_id`)
    REFERENCES `floristicboom`.`flower` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`complaint` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `status` ENUM('ACCEPTED', 'REJECTED', 'COMPLETED') NOT NULL,
  `purchase_id` BIGINT NOT NULL,
  `employee_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_purchase_complaint` (`purchase_id` ASC),
  INDEX `fk_employee_complaint` (`employee_id` ASC),
  CONSTRAINT `fk_employee_comlaint`
    FOREIGN KEY (`employee_id`)
    REFERENCES `floristicboom`.`user` (`id`),
  CONSTRAINT `fk_purchase_complaint`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `floristicboom`.`purchase` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `floristicboom`.`user_address` (
  `address_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`address_id`, `user_id`),
  INDEX `fk_user_address` (`user_id` ASC),
  INDEX `fk_address_user` (`address_id` ASC),
  CONSTRAINT `fk_address_user`
    FOREIGN KEY (`address_id`)
    REFERENCES `floristicboom`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_address`
    FOREIGN KEY (`user_id`)
    REFERENCES `floristicboom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE INDEX idx_pcode
ON address (postal_code);

CREATE INDEX idx_promocode
ON bonus (promocode);

CREATE INDEX idx_fname
ON flower (name);