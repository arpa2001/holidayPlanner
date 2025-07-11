CREATE DATABASE IF NOT EXISTS HOLIDAYPLANNER;
USE HOLIDAYPLANNER;

-- Table for maintaining main table ID
CREATE TABLE HOLIDAY_SEQ (
  ID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
);

-- Function to generate alphanumeric ID
DELIMITER $$

CREATE FUNCTION GENERATE_HOLIDAY_ID()
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
  INSERT INTO HOLIDAY_SEQ VALUES (NULL);
  SET @NEXT = LAST_INSERT_ID();
  RETURN CONCAT('HOL', LPAD(@NEXT, 5, '0'));
END $$

DELIMITER ;

-- Main table
CREATE TABLE HOLIDAYS (
  HOL_ID VARCHAR(10) PRIMARY KEY,
  HOL_DT DATE NOT NULL,
  HOL_NAME VARCHAR(20) NOT NULL,
  HOL_TYPE VARCHAR(20),
  HOL_SOURCE VARCHAR(20) NOT NULL,
  CREATED_DT DATETIME NOT NULL,
  MODIFIED_DT DATETIME,
  CREATED_BY VARCHAR(20) NOT NULL,
  MODIFIED_BY VARCHAR(20),
  MOD_REMARKS VARCHAR(50)
);

-- Trigger to add new record with audit
DELIMITER $$

CREATE TRIGGER TRG_BEFORE_INSERT_HOLIDAYS
BEFORE INSERT ON HOLIDAYS
FOR EACH ROW
BEGIN
  IF NEW.HOL_ID IS NULL THEN
    SET NEW.HOL_ID = GENERATE_HOLIDAY_ID();
  END IF;
  
  SET NEW.CREATED_DT = NOW();
  SET NEW.CREATED_BY = CURRENT_USER();
END $$

DELIMITER ;

-- Trigger to update new record with audit
DELIMITER $$

CREATE TRIGGER TRG_BEFORE_UPDATE_HOLIDAYS
BEFORE UPDATE ON HOLIDAYS
FOR EACH ROW
BEGIN
  SET NEW.MODIFIED_DT = NOW();
  SET NEW.MODIFIED_BY = CURRENT_USER();
END $$

DELIMITER ;

-- Sample data
INSERT INTO HOLIDAYS (HOL_DT, HOL_NAME, HOL_TYPE, HOL_SOURCE, MOD_REMARKS)
VALUES 
('2025-01-01','New Years Day','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-01-14','Makar Sankranti/ Pongal','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-02-26','Mahashivratri','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-03-31','Ramadan','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-04-18','Good Friday','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-05-01','May Day / Labour Day','State','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-08-15','Independence Day','National','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-08-27','Ganesh Chaturthi','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-09-05','Onam','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-10-02','Mahatma Gandhi Jayanti /Dussehra','National/ Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-10-20','Diwali / Narakchaturdashi/Laxmi Puja','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-10-23','Bhai Duj','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-11-05','Guru Nanak Jayanti/ Karthik Pournima','Festival','Tata Play','Adding 2025 Tata Play Holiday'),
('2025-12-25','Christmas','Festival','Tata Play','Adding 2025 Tata Play Holiday');

CREATE USER 'systemuser'@'localhost' IDENTIFIED BY 'Systemuser@01';
GRANT SELECT, INSERT, UPDATE ON holidayplanner.* TO 'systemuser'@'localhost';
FLUSH PRIVILEGES;