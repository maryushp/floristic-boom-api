CREATE SCHEMA IF NOT EXISTS `floristicboom` ;
USE `floristicboom` ;

CREATE TABLE IF NOT EXISTS `floristicboom`.`credentials` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `role` ENUM('ADMIN', 'EMPLOYEE', 'CLIENT', 'CURIER') NOT NULL,
  `is_banned` BIT(1) NOT NULL,
  `is_enabled` BIT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(12) NOT NULL,
  `image_uri` VARCHAR(255) NOT NULL,
  `credentials_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `credentials_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `telefon_UNIQUE` (`phone` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_credentials` (`credentials_id` ASC) VISIBLE,
  CONSTRAINT `fk_credentials_user`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `floristicboom`.`credentials` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(40) NOT NULL,
  `street` VARCHAR(40) NOT NULL,
  `house` VARCHAR(5) NOT NULL,
  `apartment` VARCHAR(5) NOT NULL,
  `postal_code` VARCHAR(6) NOT NULL,
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idx_pcode` (`postal_code` ASC) VISIBLE,
  INDEX `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi`
    FOREIGN KEY (`user_id`)
    REFERENCES `floristicboom`.`user` (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bonus` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `promo_code` VARCHAR(45) NOT NULL,
  `discount` DECIMAL(38,2) NOT NULL,
  `duration_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idx_promo_code` (`promo_code` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bouquet` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(38,2) NULL DEFAULT NULL,
  `description` VARCHAR(255) NOT NULL,
  `image_uri` VARCHAR(255) NOT NULL,
  `wrapper_color` ENUM('RED', 'GREEN', 'BLUE', 'YELLOW', 'BLACK', 'WHITE', 'ORANGE', 'PURPLE', 'PINK', 'BROWN', 'GRAY', 'CYAN', 'MAGENTA', 'TEAL', 'LIME', 'OLIVE', 'NAVY', 'MAROON') NOT NULL,
  `is_custom` BIT(1) NOT NULL,
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_bouquet_user` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_bouquet`
    FOREIGN KEY (`user_id`)
    REFERENCES `floristicboom`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`flower` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(38,2) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `image_uri` VARCHAR(255) NOT NULL,
  `color` ENUM('RED', 'GREEN', 'BLUE', 'YELLOW', 'BLACK', 'WHITE', 'ORANGE', 'PURPLE', 'PINK', 'BROWN', 'GRAY', 'CYAN', 'MAGENTA', 'TEAL', 'LIME', 'OLIVE', 'NAVY', 'MAROON') NOT NULL,
  `available_quantity` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idx_fname` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`bouquet_flower` (
  `bouquet_id` BIGINT(20) NOT NULL,
  `flower_id` BIGINT(20) NOT NULL,
  `quantity` INT(11) NOT NULL,
  PRIMARY KEY (`bouquet_id`, `flower_id`),
  INDEX `fk_flower_bouquet` (`flower_id` ASC) VISIBLE,
  INDEX `fk_bouquet_flower` (`bouquet_id` ASC) VISIBLE,
  CONSTRAINT `fk_bouquet_flower`
    FOREIGN KEY (`bouquet_id`)
    REFERENCES `floristicboom`.`bouquet` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_flower_bouquet`
    FOREIGN KEY (`flower_id`)
    REFERENCES `floristicboom`.`flower` (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`delivery_type` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`delivery` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `delivery_date` DATETIME NULL DEFAULT NULL,
  `courier_id` BIGINT(20) NULL DEFAULT NULL,
  `delivery_type_id` BIGINT(20) NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_curier_delivery` (`courier_id` ASC) VISIBLE,
  INDEX `fk_delivery_type_delivery` (`delivery_type_id` ASC) VISIBLE,
  INDEX `fk_address_delivery` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_delivery`
    FOREIGN KEY (`address_id`)
    REFERENCES `floristicboom`.`address` (`id`),
  CONSTRAINT `fk_delivery_curier`
    FOREIGN KEY (`courier_id`)
    REFERENCES `floristicboom`.`user` (`id`),
  CONSTRAINT `fk_delivery_type_delivery`
    FOREIGN KEY (`delivery_type_id`)
    REFERENCES `floristicboom`.`delivery_type` (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`purchase` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `payment_date` DATETIME NULL DEFAULT NULL,
  `price` DECIMAL(38,2) NULL DEFAULT NULL,
  `status` ENUM('COMPLETED', 'PLACED', 'DELIVERING') NOT NULL,
  `payment_type` ENUM('TRANSFER', 'ONLINE', 'OFFLINE') NULL DEFAULT NULL,
  `employee_id` BIGINT(20) NULL DEFAULT NULL,
  `client_id` BIGINT(20) NOT NULL,
  `delivery_id` BIGINT(20) NULL DEFAULT NULL,
  `bonus_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_client_purchase` (`client_id` ASC) VISIBLE,
  INDEX `fk_employee_purchase` (`employee_id` ASC) VISIBLE,
  INDEX `fk_delivery_purchase` (`delivery_id` ASC) VISIBLE,
  INDEX `fk_bonus_purchase` (`bonus_id` ASC) VISIBLE,
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
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`complaint` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `status` ENUM('ACCEPTED', 'REJECTED', 'COMPLETED') NOT NULL,
  `purchase_id` BIGINT(20) NOT NULL,
  `employee_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_purchase_complaint` (`purchase_id` ASC) VISIBLE,
  INDEX `fk_employee_complaint` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_comlaint`
    FOREIGN KEY (`employee_id`)
    REFERENCES `floristicboom`.`user` (`id`),
  CONSTRAINT `fk_purchase_complaint`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `floristicboom`.`purchase` (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `floristicboom`.`purchase_bouquet` (
  `purchase_id` BIGINT(20) NOT NULL,
  `bouquet_id` BIGINT(20) NOT NULL,
  `quantity` INT(11) NOT NULL,
  PRIMARY KEY (`purchase_id`, `bouquet_id`),
  INDEX `fk_purchase_bouquet` (`purchase_id` ASC) VISIBLE,
  INDEX `fk_bouquet_purchase` (`bouquet_id` ASC) VISIBLE,
  CONSTRAINT `fk_bouquet_purchase`
    FOREIGN KEY (`bouquet_id`)
    REFERENCES `floristicboom`.`bouquet` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_purchase_bouquet`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `floristicboom`.`purchase` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;