DROP database IF EXISTS scholarshipv1;
create database scholarshipv1;

-- Drop tables if they exist
DROP TABLE IF EXISTS scholarshipv1.scholarshiprecord;
DROP TABLE IF EXISTS scholarshipv1.entity;
DROP TABLE IF EXISTS scholarshipv1.user;
DROP TABLE IF EXISTS scholarshipv1.institution;


CREATE TABLE scholarshipv1.institution (
  `institutionId` int NOT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`institutionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE scholarshipv1.user (
  `userId` int NOT NULL AUTO_INCREMENT,
  `institutionId` int,
  `userName` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `fk_user_institution` FOREIGN KEY (`institutionId`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Create the 'entity' table
CREATE TABLE scholarshipv1.entity (
  `entityid` int NOT NULL AUTO_INCREMENT,
  `entity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`entityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE scholarshipv1.scholarshiprecord (
  `scholarshipId` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `institutionId` int DEFAULT NULL,
  `scholarshipName` varchar(255) DEFAULT NULL,
  `scholarshipAmount` int DEFAULT NULL,
  `entityid` int DEFAULT NULL,
  `updatedTime` timestamp NULL DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `isExpired` tinyint(1) DEFAULT NULL,
  `webUrl` varchar(255) DEFAULT NULL,
  `isUpdated` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`scholarshipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO scholarshipv1.institution (`institutionId`,`institutionName`, `contact`, `contactNumber`) VALUES ('25371548','行天宮', '李先生', '0912345678'), ('25371549','教育部', '陳小姐', '028922210'), ('25348848','勞動部', '張先生', '07998441');

INSERT INTO scholarshipv1.user (`institutionId`,`userName`, `password`) VALUES
('25371548', 'dave.wenyu@gmail.com', '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta'),
('25371549','lyndonyeh@gmail.com', '$2a$05$0ppuj4QdEyWAnlNf7IbWFObR9.NH1rSIgTaTJ1WyNvAyd9iWtR7uW'),
('25348848','alicelu@gmail.com', '$2a$05$wbUIDjEtLQ5J8mzwIiDE1Op5nz4N6XNclJxFEtehQg2Bodhj22G2K');

INSERT INTO scholarshipv1.entity (`entityid`, `entity`) VALUES
('1', 'kindergarten'),
('2', 'primary school'),
('3', 'middle school'),
('4', 'high school'),
('5', 'college/university'),
('6', 'graduate school');

INSERT INTO scholarshipv1.scholarshiprecord (`scholarshipName`, `scholarshipAmount`, `entityid`) VALUES
('Scholarship 1', 1000, 1),
('Scholarship 2', 1500, 2),
('Scholarship 3', 2000, 3);

use scholarshipv1;