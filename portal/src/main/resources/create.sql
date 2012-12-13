CREATE TABLE `users` (
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL DEFAULT '0',
    `password` VARCHAR(50) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`)
)
COLLATE='utf8_slovenian_ci'
ENGINE=InnoDB;

CREATE TABLE `log` (
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `message` VARCHAR(50) NOT NULL DEFAULT '0',
    `timestamp` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
)
COLLATE='utf8_slovenian_ci'
ENGINE=InnoDB;