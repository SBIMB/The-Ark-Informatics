INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Administrator'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='CREATE'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Administrator'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='READ'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Administrator'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='UPDATE'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Administrator'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='DELETE'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Data Manager'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='CREATE'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Data Manager'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='READ'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Data Manager'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='UPDATE'));

INSERT INTO `study`.`ark_role_policy_template` (`ARK_ROLE_ID`,`ARK_MODULE_ID`, `ARK_FUNCTION_ID`, `ARK_PERMISSION_ID`) 
VALUES ((SELECT `ID` FROM `STUDY`.`ARK_ROLE` WHERE `NAME` ='Genomics Read-Only user'), (SELECT `ID` FROM `STUDY`.`ark_module` WHERE `NAME` ='Genomics'),(SELECT `ID` FROM `STUDY`.`ARK_FUNCTION` WHERE `NAME` ='ANALYSIS'), (SELECT `ID` FROM `STUDY`.`ark_permission` WHERE `NAME` ='READ'));


