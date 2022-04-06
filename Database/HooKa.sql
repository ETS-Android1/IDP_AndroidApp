/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- database creation with name `HooKa`
create database if not exists hooka;
use hooka;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userType` varchar(128) NOT NULL,
  `fullname` varchar(128) NOT NULL,
  `mobile` varchar(8) NOT NULL,
  `password` varchar(128) NOT NULL,
  `joinedSession` int(11) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userType`, `fullname`, `mobile`,`password`,`joinedSession`) 
					VALUES ('Instructor','admin','98765432','3700aa0cb7feaba7b2b4baf8afba0d83140caa244b0fb011ab254ea40ca4fab7',0),
                  ('Student','Amy','91234567','password',1),
                  ('Student','Mark','91234567','password',1),
                  ('Student','Helly','91234567','password',1),
                  ('Student','Zhao Yi','91234567','password',1),
                  ('Student','Zhi Hui','91234567','password',1);
                                                  /* password: chowi */
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `sessionId` int(11) NOT NULL AUTO_INCREMENT,
  `sessionPin` int(6) NOT NULL,
  `sessionName` varchar(256) NOT NULL,
  `userId` int(11) NOT NULL,
  `sessionRunningStatus` int(2) DEFAULT -1 NOT NULL,
  `totalQns` int(2) NOT NULL,
  PRIMARY KEY (`sessionId`),
  KEY `UserId_idx` (`userId`),
  CONSTRAINT `UserId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` (`sessionName`, `sessionPin`, `userId`, `totalQns`) 
					VALUES ('wazzup', 101010, 1, 3);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `qnId` int(11) NOT NULL AUTO_INCREMENT,
  `sessionId` int(11) NOT NULL,
  `qnNumber` int(2) NOT NULL,
  `qnDesc` varchar(256) NOT NULL,
  `answer` varchar(8) NOT NULL,
  `accessible` int(2) DEFAULT -1 NOT NULL,
  PRIMARY KEY (`qnId`),
  KEY `SessionId_idx` (`sessionId`),
  CONSTRAINT `SessionIdQns` FOREIGN KEY (`sessionId`) REFERENCES `session` (`sessionId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` (`sessionId`, `qnNumber`, `qnDesc`, `answer`) 
					VALUES (1,1,'What is Zhao Yi converse shoe size?','A'),
						   (1,2,'How much is Fine Food Duck Rice (Upsize meat and rice)?','C'),
						   (1,3,'When is Singapore National Day?','B');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `options` (
  `optionId` int(11) NOT NULL AUTO_INCREMENT,
  `qnId` int(11) NOT NULL,
  `sessionId` int(11) NOT NULL,
  `optionLetter` varchar(8) NOT NULL,
  `optionDesc` varchar(256) NOT NULL,
  PRIMARY KEY (`optionId`),
  KEY `QnId_idx` (`qnId`),
  KEY `SessionId_idx` (`sessionId`),
  CONSTRAINT `QnIdOptions` FOREIGN KEY (`qnId`) REFERENCES `questions` (`qnId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SessionIdOptions` FOREIGN KEY (`sessionId`) REFERENCES `session` (`sessionId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` (`qnId`, `sessionId`, `optionLetter`, `optionDesc`) 
					VALUES (1,1,'A','UK8'),
				  (1,1,'B','UK6'),
                  (1,1,'C','UK7'),
                  (1,1,'D','UK9'),
				  (2,1,'A','$3.00'),
                  (2,1,'B','$4.00'),
                  (2,1,'C','$5.50'),
                  (2,1,'D','$6.50'),
                  (3,1,'A','9 September'),
                  (3,1,'B','9 August'),
                  (3,1,'C','10 August'),
                  (3,1,'D','10 September');
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `response` (
  `responseId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `qnId` int(11) NOT NULL,
  `sessionId` int(11) NOT NULL,
  `choice` varchar(8) NOT NULL,
  `points` int(11) NULL,
  PRIMARY KEY (`responseId`),
  KEY `QnId_idx` (`qnId`),
  KEY `ResponseUserId_idx` (`userId`),
  KEY `SessionId_idx` (`sessionId`),
  CONSTRAINT `QnIdResponse` FOREIGN KEY (`qnId`) REFERENCES `questions` (`qnId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ResponseUserId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SessionIdResponse` FOREIGN KEY (`sessionId`) REFERENCES `session` (`sessionId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
INSERT INTO `response` (`userId`, `qnId`, `sessionId`, `choice`) 
					VALUES (2,1,1,'A'),
						      (3,1,1,'A'),
                  (4,1,1,'A'),
                  (5,1,1,'B'),
                  (6,1,1,'B');
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;