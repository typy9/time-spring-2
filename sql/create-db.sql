-- -----------------------------------------------------
-- Schema time_db
-- -----------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
CREATE SCHEMA IF NOT EXISTS `time_db` DEFAULT CHARACTER SET utf8 ;
USE `time_db` ;

-- -----------------------------------------------------
-- Table `time_db`.`users_activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_db`.`users_activities` ;

CREATE TABLE IF NOT EXISTS `time_db`.`users_activities` (
                                                            `id` INT PRIMARY KEY AUTO_INCREMENT,
                                                            `user_id` INT,
                                                            `activity_id` INT,
                                                            `time` INT,
                                                            CONSTRAINT FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`activity_id`)
    REFERENCES activities (activity_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table `time_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_db`.`users` ;

CREATE TABLE IF NOT EXISTS `time_db`.`users` (
                                                `user_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                `username` VARCHAR(255) NOT NULL,
                                                `login` VARCHAR(255) NOT NULL,
                                                `password` VARCHAR(255) NOT NULL,
                                                `role` ENUM('admin', 'user') NOT NULL);

-- -----------------------------------------------------
-- Table `time_db`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_db`.`categories` ;

CREATE TABLE IF NOT EXISTS `time_db`.`categories` (
                                                      `category_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                      `name` VARCHAR(255) NOT NULL);

-- -----------------------------------------------------
-- Table `time_db`.`activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_db`.`activities` ;

CREATE TABLE IF NOT EXISTS `time_db`.`activities` (
                                                      `activity_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                      `name` VARCHAR(255),
    `activity_category_id` INT,

    CONSTRAINT FOREIGN KEY (activity_category_id)
    REFERENCES categories (category_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE );


-- -----------------------------------------------------
-- Table `time_db`.`activity_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_db`.`activity_request` ;

CREATE TABLE IF NOT EXISTS `time_db`.`activity_request` (
                                            `request_id` INT PRIMARY KEY AUTO_INCREMENT,
                                            `user_id` INT NOT NULL,
                                            `activity_id` INT NOT NULL,
                                            `status` ENUM('CREATED', 'APPROVED', 'DECLINED', 'TOBEDELETED') NOT NULL,

    CONSTRAINT FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (activity_id)
    REFERENCES activities (activity_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table time_db.users add data
-- -----------------------------------------------------

INSERT INTO users VALUES (DEFAULT, 'ivanov', 'a', '1', 'admin');
INSERT INTO users VALUES (DEFAULT, 'petrov', 'u', '2', 'user');

-- -----------------------------------------------------
-- Table time_db.categories add data
-- -----------------------------------------------------
INSERT INTO time_db.categories VALUES (DEFAULT, 'general');
INSERT INTO time_db.categories VALUES (DEFAULT, 'administration');
INSERT INTO time_db.categories VALUES (DEFAULT, 'break');

-- -----------------------------------------------------
-- Table time_db.activities add data
-- -----------------------------------------------------
INSERT INTO time_db.activities VALUES (DEFAULT, 'project management', 1);
INSERT INTO time_db.activities VALUES (DEFAULT, 'lunch', 3);
INSERT INTO time_db.activities VALUES (DEFAULT, 'admin', 2);

-- -----------------------------------------------------
-- Table time_db.users_activities add data
-- -----------------------------------------------------
INSERT INTO users_activities VALUES (DEFAULT, 1, 2, 60);
INSERT INTO users_activities VALUES (DEFAULT, 1, 3, 45);
INSERT INTO users_activities VALUES (DEFAULT, 2, 3, 75);
INSERT INTO users_activities VALUES (DEFAULT, 2, 1, 180);
