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
('04130624','行天宮', '李先生', '0912345678'), 
('03458902','新光人壽', '陳小姐', '028922210'), 
('04541302','鴻海有限公司', '張先生', '07998441');

INSERT INTO scholarshipv1.user (`institutionId`,`userName`, `password`) VALUES
('04130624', 'dave.wenyu@gmail.com', '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta'),
('03458902','lyndonyeh@gmail.com', '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta'),
('04541302','alicelu@gmail.com', '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta');

INSERT INTO scholarshipv1.scholarshiprecord (`institutionId`,`scholarshipName`, `scholarshipAmount`, `entityid`,startDate,endDate,webUrl,contact,contactNumber, isUpdated) VALUES
('03458902','新光人壽獎學金', 3000, 6,'2024-03-01','2024-03-31','https://www.sklsf.org/','李小姐','0222886655',1),
('04541302','鴻海獎學金', 100000, 2,'2024-09-01','2024-09-25','https://www.foxconnfoundation.org/plan/scholar/ejsschool/questions/class_a','陸小姐','0800860880',1),
('04130624','行天宮國中獎助學金', 6000, 3,'2024-01-01','2024-03-10','https://www.ht.org.tw/religion153.htm','李先生','0987878787',0),
('04130624','行天宮高中獎助學金', 8000, 4,'2024-01-01','2024-03-10','https://www.ht.org.tw/religion153.htm','李先生','0987878787',0),
('04130624','行天宮大專獎助學金', 10000, 5,'2024-01-01','2024-03-10','https://www.ht.org.tw/religion153.htm','李先生','0987878787',0)
;

INSERT INTO scholarshipv1.entity (`entityid`, `entityName`) VALUES
('1', '幼稚園'),
('2', '國小'),
('3', '國中'),
('4', '高中'),
('5', '大學'),
('6', '研究所');


use scholarshipv1;