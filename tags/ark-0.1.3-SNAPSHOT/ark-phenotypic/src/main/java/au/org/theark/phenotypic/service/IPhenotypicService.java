package au.org.theark.phenotypic.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import au.org.theark.core.exception.ArkSystemException;
import au.org.theark.core.exception.EntityCannotBeRemoved;
import au.org.theark.core.exception.EntityNotFoundException;
import au.org.theark.core.model.pheno.entity.DelimiterType;
import au.org.theark.core.model.pheno.entity.Field;
import au.org.theark.core.model.pheno.entity.FieldData;
import au.org.theark.core.model.pheno.entity.FieldPhenoCollection;
import au.org.theark.core.model.pheno.entity.FieldType;
import au.org.theark.core.model.pheno.entity.FileFormat;
import au.org.theark.core.model.pheno.entity.PhenoCollection;
import au.org.theark.core.model.pheno.entity.PhenoCollectionUpload;
import au.org.theark.core.model.pheno.entity.PhenoUpload;
import au.org.theark.core.model.pheno.entity.Status;
import au.org.theark.core.model.study.entity.LinkSubjectStudy;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.util.BarChartResult;
import au.org.theark.phenotypic.model.vo.PhenoCollectionVO;
import au.org.theark.phenotypic.model.vo.UploadVO;

public interface IPhenotypicService {
	
	// PhenoCollection
	public PhenoCollection getPhenoCollection(Long id);
	public java.util.Collection<PhenoCollection> getPhenoCollectionByStudy(Study study);
	public java.util.Collection<PhenoCollection> searchPhenotypicCollection(PhenoCollection phenotypicCollection);
	public PhenoCollectionVO getPhenoCollectionAndFields(Long id);
	public PhenoCollection getPhenoCollectionByUpload(PhenoUpload upload);
	public void createCollection(PhenoCollection col);
	public void createCollection(PhenoCollectionVO colVo);
	public void updateCollection(PhenoCollection col);
	public void updateCollection(PhenoCollectionVO colVo);
	public void deleteCollection(PhenoCollection col) throws ArkSystemException, EntityCannotBeRemoved;
	public void deleteCollection(PhenoCollectionVO colVo);
	public int clearPhenoCollection(PhenoCollection phenoCollection);
	public boolean phenoCollectionHasData(PhenoCollection phenoCollection);

	// Field
	public Field getField(Long fieldId);
	public java.util.Collection<Field> searchField(Field field);
	public Field getFieldByNameAndStudy(String fieldName, Study study) throws EntityNotFoundException;
	public boolean fieldHasData(Field field);
	public void createField(Field field);
	public void updateField(Field field);
	public void deleteField(Field field) throws ArkSystemException, EntityCannotBeRemoved;
	
	// Field_collections
	public FieldPhenoCollection getFieldPhenoCollection(FieldPhenoCollection fieldPhenoCollection);
	public void createFieldPhenoCollection(FieldPhenoCollection fieldPhenoCollection);
	public void updateFieldPhenoCollection(FieldPhenoCollection fieldPhenoCollection);
	
	// FieldType
	public FieldType getFieldType(Long id);
	public FieldType getFieldTypeByName(String fieldTypeName);
	public java.util.Collection<FieldType> getFieldTypes();
	
	// FieldData
	public FieldData getFieldData(FieldData fieldData);
	public Collection<FieldData> searchFieldDataByField(Field field);
	public Collection<FieldData> searchFieldData(FieldData fieldData);
	public Collection<FieldData> searchFieldDataBySubjectAndDateCollected(LinkSubjectStudy linkSubjectStudy, java.util.Date dateCollected);
	public void createFieldData(FieldData fieldData);
	public void updateFieldData(FieldData fieldData);
	public void deleteFieldData(FieldData fieldData);
	
	// Status
	public Status getStatusByName(String statusName);
	public java.util.Collection<Status> getStatus();
	
	// Upload phenotypic data file
	public void uploadPhenotypicDataFile(org.apache.wicket.util.file.File file, String fileFormat, char delimiterChar);
	public StringBuffer uploadAndReportPhenotypicDataFile(org.apache.wicket.util.file.File file, String fileFormat, char delimiterChar);
	public void uploadPhenotypicDataFile(InputStream inputStream, String fileFormat, char delimiterChar);
	public StringBuffer uploadAndReportPhenotypicDataFile(InputStream inputStream, String fileFormat, char delimiterChar);
	public StringBuffer uploadAndReportPhenotypicDataFile(UploadVO uploadVo);
	
	// File Format
	public java.util.Collection<FileFormat> getFileFormats();
	
	// Upload
	public java.util.Collection<PhenoUpload> searchUpload(PhenoUpload upload);
	public java.util.Collection<PhenoUpload> searchUploadByCollection(PhenoCollection phenoCollection);
	public PhenoCollectionVO getPhenoCollectionAndUploads(Long id);
	public PhenoCollectionVO getPhenoCollectionAndUploads(PhenoCollection phenoCollection);
	public PhenoUpload getUpload(Long id);
	public void createUpload(PhenoUpload upload);
	public void createUpload(UploadVO uploadVo);
	public void updateUpload(PhenoUpload upload);
	public void deleteUpload(PhenoUpload upload) throws ArkSystemException, EntityCannotBeRemoved;
	
	// CollectionUpload
	public PhenoCollectionUpload getPhenoCollectionUpload(Long id);
	public java.util.Collection<PhenoCollectionUpload> searchPhenoCollectionUpload(PhenoCollectionUpload phenoCollectionUpload);
	public void createPhenoCollectionUpload(PhenoCollectionUpload phenoCollectionUpload);
	public void updatePhenoCollectionUpload(PhenoCollectionUpload phenoCollectionUpload);
	public void deletePhenoCollectionUpload(PhenoCollectionUpload phenoCollectionUpload);
	
	// FieldUpload
	public java.util.Collection<PhenoUpload> searchFieldUpload(PhenoUpload phenoUpload);
	
	// Delimiter Type
	public Collection<DelimiterType> getDelimiterTypes();
	
	public int getCountOfFieldsInStudy(Study study);
	public int getCountOfFieldsWithDataInStudy(Study study);
	
	public java.util.Collection<FieldPhenoCollection> getFieldPhenoCollection(PhenoCollection phenoCollection);
	public int getCountOfCollectionsInStudy(Study study);
	public int getCountOfCollectionsWithDataInStudy(Study study);
	
	/**
	 * A generic interface that will return a list PhenoCollectionVO specified by a particular criteria, and a paginated reference point
	 * @return Collection of PhenoCollectionVO
	 */
	public List<PhenoCollectionVO> searchPageableFieldData(PhenoCollectionVO phenoCollectionVoCriteria, int first, int count);
	
	/**
	 * A generic interface that will return count of the fieldData's in the study
	 * @return int
	 */
	public int getStudyFieldDataCount(PhenoCollectionVO phenoCollectionVoCriteria);
	public DelimiterType getDelimiterType(Long id);
	
	public java.util.List<BarChartResult> getFieldsWithDataResults(Study study);
	public String getDelimiterTypeByDelimiterChar(char phenotypicDelimChr);
	public FileFormat getFileFormatByName(String name);
}