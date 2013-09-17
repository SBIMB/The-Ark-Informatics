CREATE  TABLE `geno`.`command` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `LOCATION` VARCHAR(45) NULL ,
  `SERVER_URL` VARCHAR(45) NULL ,
  `INPUT_FILE_FORMAT` VARCHAR(45) NULL ,
  `OUTPUT_FILE_FORMAT` VARCHAR(45) NULL DEFAULT 'may need error output too i guess or alt output' ,
  PRIMARY KEY (`ID`) );

CREATE  TABLE `geno`.`process` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `DESCRIPTION` VARCHAR(45) NULL ,
  `INPUT_FILE_LOCATION` VARCHAR(45) NULL ,
  `INPUT_FILE_HASH` VARCHAR(45) NULL ,
  `INPUT_FILE_TYPE` VARCHAR(45) NULL ,
  `INPUT_KEPT` VARCHAR(45) NULL ,
  `INPUT_SERVER` VARCHAR(45) NULL ,
  `OUTPUT_FILE_LOCATION` VARCHAR(45) NULL ,
  `OUTPUT_FILE_HASH` VARCHAR(45) NULL ,
  `OUTPUT_FILE_TYPE` VARCHAR(45) NULL ,
  `OUTPUT_KEPT` VARCHAR(45) NULL ,
  `OUTPUT_SERVER` VARCHAR(45) NULL ,
  `COMMAND_ID` VARCHAR(45) NULL DEFAULT 'The command is the task/program that will perform the process/transform' ,
  PRIMARY KEY (`ID`) );

CREATE  TABLE `geno`.`transformation_template` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(255) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NULL ,
  PRIMARY KEY (`ID`) );

/**where do I link a person**/
CREATE  TABLE `geno`.`transformation` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(255) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NULL ,
  PRIMARY KEY (`ID`) );

CREATE  TABLE `geno`.`transformation_process` (
  `ID` INT NOT NULL ,
  `TRANSFORMATION_ID` INT NOT NULL,
  `PROCESS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`) );

/* really could be person*/
CREATE  TABLE `geno`.`lss_process` (
  `ID` INT NOT NULL ,
  `TRANSFORMATION_ID` INT NOT NULL,
  `LSS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`) );







