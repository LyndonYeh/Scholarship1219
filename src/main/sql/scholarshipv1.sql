DROP database IF EXISTS scholarshipv1;
create database scholarshipv1;

-- Drop tables if they exist
DROP TABLE IF EXISTS scholarshipv1.scholarshiprecord;
DROP TABLE IF EXISTS scholarshipv1.garbageCollection;
DROP TABLE IF EXISTS scholarshipv1.entity;
DROP TABLE IF EXISTS scholarshipv1.user;
DROP TABLE IF EXISTS scholarshipv1.institution;


CREATE TABLE scholarshipv1.institution (
  `institutionId` varchar(50) NOT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`institutionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE scholarshipv1.user (
  `userId` int NOT NULL AUTO_INCREMENT,
  `institutionId` varchar(50),
  `userName` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `fk_user_institution` FOREIGN KEY (`institutionId`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Create the 'entity' table
CREATE TABLE scholarshipv1.entity (
  `entityid` int NOT NULL AUTO_INCREMENT,
  `entityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`entityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE scholarshipv1.scholarshiprecord (
  `scholarshipId` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `institutionId` varchar(50) DEFAULT NULL,
  `scholarshipName` varchar(255) DEFAULT NULL,
  `scholarshipAmount` int DEFAULT NULL,
  `entityid` int DEFAULT NULL,
  `updatedTime` timestamp NULL DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `isExpired` boolean DEFAULT 0,
  `webUrl` varchar(255) DEFAULT NULL,
  `isUpdated` boolean DEFAULT 0,
  `contact` varchar(50) DEFAULT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`scholarshipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci,AUTO_INCREMENT = 100;

CREATE TABLE scholarshipv1.garbageCollection (
  `scholarshipId` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `institutionId` varchar(50) DEFAULT NULL,
  `scholarshipName` varchar(255) DEFAULT NULL,
  `scholarshipAmount` int DEFAULT NULL,
  `entityid` int DEFAULT NULL,
  `updatedTime` timestamp NULL DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `isExpired` boolean DEFAULT 0,
  `webUrl` varchar(255) DEFAULT NULL,
  `isUpdated` boolean DEFAULT 0,
  `contact` varchar(50) DEFAULT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`scholarshipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci,AUTO_INCREMENT = 900;


INSERT INTO scholarshipv1.institution (`institutionId`,`institutionName`, `contact`, `contactNumber`) VALUES 
('52628812','家扶基金會', '李先生', '0912345678'), 
('25371549','教育部', '陳小姐', '028922210'), 
('25348848','勞動部', '張先生', '07998441');

INSERT INTO scholarshipv1.user (`institutionId`,`userName`, `password`) VALUES
('52628812', 'dave.wenyu@gmail.com', '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta'),
('25371549','lyndonyeh@gmail.com', '$2a$05$0ppuj4QdEyWAnlNf7IbWFObR9.NH1rSIgTaTJ1WyNvAyd9iWtR7uW'),
('25348848','alicelu@gmail.com', '$2a$05$wbUIDjEtLQ5J8mzwIiDE1Op5nz4N6XNclJxFEtehQg2Bodhj22G2K');

INSERT INTO scholarshipv1.scholarshiprecord (`institutionId`,`scholarshipName`, `scholarshipAmount`, `entityid`,startDate,endDate,webUrl,contact,contactNumber) VALUES
('52628812','新光人壽獎學金', 3000, 4,'2024-03-01','2024-03-31','https://www.sklsf.org/','李小姐','0222886655'),
('52628812','鴻海獎學金', 100000, 2,'2024-09-01','2024-09-25','https://www.foxconnfoundation.org/plan/scholar/ejsschool/questions/class_a','陸小姐','0800860880'),
('52628812','行天宮資優生獎助學金', 200000, 5,'2024-01-01','2024-03-10','https://www.ht.org.tw/religion177.htm','陳先生','0987878787');

INSERT INTO scholarshipv1.entity (`entityid`, `entityName`) VALUES
('1', '幼稚園'),
('2', '國小'),
('3', '國中'),
('4', '高中'),
('5', '大學'),
('6', '研究所');


use scholarshipv1;