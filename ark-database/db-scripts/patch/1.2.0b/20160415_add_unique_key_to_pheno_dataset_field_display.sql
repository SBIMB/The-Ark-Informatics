use `pheno`;
ALTER TABLE `pheno_dataset_field_display` 
ADD UNIQUE INDEX `UNIQUE_KEY_PHENO_FIELD` (`PHENO_DATASET_FIELD_GROUP_ID` ASC, `PHENO_DATASET_FIELD_ID` ASC, `PHENO_DATASET_CATEGORY_ID` ASC);

