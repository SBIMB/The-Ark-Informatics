USE `pheno`;
--
-- Table structure for table `pheno_dataset_field_display`
--
DROP TABLE IF EXISTS `pheno_dataset_field_display`;
CREATE TABLE `pheno_dataset_field_display` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHENO_DATASET_FIELD_ID` int(11) NOT NULL,
  `PHENO_DATASET_FIELD_GROUP_ID` int(11) DEFAULT NULL,
  `SEQUENCE` int(11) DEFAULT NULL,
  `REQUIRED` int(11) DEFAULT NULL,
  `REQUIRED_MESSAGE` varchar(45) DEFAULT NULL,
  `ALLOW_MULTIPLE_SELECTION` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_PHENO_DATASET_FIELD_GROUP_ID` (`PHENO_DATASET_FIELD_GROUP_ID`),
  KEY `FK_PHENO_DATASET_FIELD_ID` (`PHENO_DATASET_FIELD_ID`),
  CONSTRAINT `FK_PHENO_DATASET_FIELD_ID` FOREIGN KEY (`PHENO_DATASET_FIELD_ID`) REFERENCES `pheno_dataset_field` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_PHENO_DATASET_FIELD_GROUP_ID` FOREIGN KEY (`PHENO_DATASET_FIELD_GROUP_ID`) REFERENCES `pheno_dataset_field_group` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1109 DEFAULT CHARSET=latin1;
