CREATE DATABASE IF NOT EXISTS petclinicvisit;

ALTER DATABASE petclinicvisit
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON petclinicvisit.* TO pc@localhost IDENTIFIED BY 'pc';

USE petclinicvisit;

CREATE TABLE IF NOT EXISTS visits (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pet_id INT(4) UNSIGNED NOT NULL,
  vet_id INT(4) UNSIGNED NOT NULL,
  visit_date DATE,
  visit_slot INT(4) UNSIGNED NOT NULL,
  description VARCHAR(255),
  UNIQUE (vet_id, visit_date, visit_slot),
  INDEX(vet_id, visit_date, visit_slot)
) engine=InnoDB;
