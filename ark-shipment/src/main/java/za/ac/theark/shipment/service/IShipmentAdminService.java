package za.ac.theark.shipment.service;

import java.util.List;

import au.org.theark.core.model.lims.entity.BarcodeLabel;
import au.org.theark.core.model.lims.entity.BarcodeLabelData;
import au.org.theark.core.model.lims.entity.BioCollection;
import au.org.theark.core.model.shipment.entity.ShipmentLabel;
import au.org.theark.core.model.shipment.entity.ShipmentLabelData;
import au.org.theark.core.model.lims.entity.Biospecimen;
import au.org.theark.core.model.lims.entity.BiospecimenUidPadChar;
import au.org.theark.core.model.lims.entity.BiospecimenUidTemplate;
import au.org.theark.core.model.lims.entity.BiospecimenUidToken;
import au.org.theark.core.model.study.entity.Study;

public interface IShipmentAdminService {
	
	/**
	 * Gets the barcodeLabel from the database
	 * @param barcodeLabel
	 * @return
	 */
	public BarcodeLabel searchBarcodeLabel(BarcodeLabel barcodeLabel);
	
	/**
	 * Gets the barcodeLabelData from the database
	 * @param barcodeLabelData
	 * @return
	 */
	public BarcodeLabelData searchBarcodeLabelData(BarcodeLabelData barcodeLabelData);
	
	/**
	 * Create a barcode Label based on the supplied barcodeLabel
	 * 
	 * @param barcodeLabel
	 *           the BarcodeLabel object
	 */
	public void createBarcodeLabel(BarcodeLabel barcodeLabel);
	
	/**
	 * Update a barcode label based on the supplied BarcodeLabel
	 * 
	 * @param BarcodeLabel
	 *           the BarcodeLabel object
	 */
	public void updateBarcodeLabel(BarcodeLabel BarcodeLabel);
	
	/**
	 * Delete a barcode label based on the supplied BarcodeLabel
	 * 
	 * @param barcodeLabel
	 *           the BarcodeLabel object
	 */
	public void deleteBarcodeLabel(BarcodeLabel barcodeLabel);
	
	/**
	 * Create a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void createBarcodeLabelData(BarcodeLabelData barcodeLabelData);
	
	/**
	 * Update a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void updateBarcodeLabelData(BarcodeLabelData barcodeLabelData);
	
	/**
	 * Delete a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void deleteBarcodeLabelData(BarcodeLabelData barcodeLabelData);
	
	/**
	 * Gets the barcodeLabel from the database
	 * @param barcodeLabel
	 * @return
	 */
	public BioShipmentLabel searchBioShipmentLabel(BioShipmentLabel bioShipmentLabel);
	
	/**
	 * Gets the barcodeLabelData from the database
	 * @param barcodeLabelData
	 * @return
	 */
	public BioShipmentLabelData searchBioShipmentLabelData(BioShipmentLabelData bioShipmentLabelData);
	
	/**
	 * Create a barcode Label based on the supplied barcodeLabel
	 * 
	 * @param barcodeLabel
	 *           the BarcodeLabel object
	 */
	public void createBioShipmentLabel(BioShipmentLabel bioShipmentLabel);
	
	/**
	 * Update a barcode label based on the supplied BarcodeLabel
	 * 
	 * @param BarcodeLabel
	 *           the BarcodeLabel object
	 */
	public void updateBioShipmentLabel(BioShipmentLabel bioShipmentLabel);
	
	/**
	 * Delete a barcode label based on the supplied BarcodeLabel
	 * 
	 * @param barcodeLabel
	 *           the BarcodeLabel object
	 */
	public void deleteBioShipmentLabel(BioShipmentLabel bioShipmentLabel);
	
	/**
	 * Create a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void createBioShipmentLabelData(BioShipmentLabelData bioShipmentLabelData);
	
	/**
	 * Update a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void updateBioShipmentLabelData(BioShipmentLabelData bioShipmentLabelData);
	
	/**
	 * Delete a barcode label data based on the supplied BarcodeLabelData
	 * 
	 * @param barcodeLabelData
	 *           the BarcodeLabelData object
	 */
	public void deleteBioShipmentLabelData(BioShipmentLabelData barcodeLabelData);
	/**
	 * Create the template string to send to the barcode printer for the specified bioCollection and barcodeLabel
	 * @param bioCollection
	 * @param barcodeLabel
	 * @return
	 */
	public String createBioCollectionLabelTemplate(BioCollection bioCollection, BarcodeLabel barcodeLabel);

	/**
	 * Create the template string to send to the barcode printer for the specified biospecimen and barcodeLabel
	 * @param biospecimen
	 * @param barcodeLabel
	 * @return
	 */
	public String createBiospecimenLabelTemplate(Biospecimen biospecimen, BarcodeLabel barcodeLabel);

	/**
	 * Get the total count of the entities
	 * @param object
	 * @return
	 */
	public long getBarcodeLabelCount(BarcodeLabel object);

	/**
	 * Get the total count of the entities
	 * @param object
	 * @return
	 */
	public long getBioShipmentLabelCount(BioShipmentLabel object);
	
	/**
	 * Search the entities, restricted by a pageable count
	 * @param object
	 * @param first
	 * @param count
	 * @return
	 */
	public List<BarcodeLabel> searchPageableBarcodeLabels(BarcodeLabel object, int first, int count);

	/**
	 * Get the BiospeciemenUidTemplate for the specifies Study
	 * @param study
	 * @return
	 */
	public BiospecimenUidTemplate getBiospecimenUidTemplate(Study study);
	
	/**
	 * Create a biospecimenUid template based on the supplied BiospecimenUidTemplate
	 * 
	 * @param BiospecimenUidTemplate
	 *           the BiospecimenUidTemplate object
	 */
	public void createBiospecimenUidTemplate(BiospecimenUidTemplate biospecimenUidTemplate);
	
	/**
	 * Update a biospecimenUid template based on the supplied BiospecimenUidTemplate
	 * 
	 * @param BiospecimenUidTemplate
	 *           the BiospecimenUidTemplate object
	 */
	public void updateBiospecimenUidTemplate(BiospecimenUidTemplate biospecimenUidTemplate);
	
	/**
	 * Delete a biospecimenUid template based on the supplied BiospecimenUidTemplate
	 * 
	 * @param BiospecimenUidTemplate
	 *           the BiospecimenUidTemplate object
	 */
	public void deleteBiospecimenUidTemplate(BiospecimenUidTemplate biospecimenUidTemplate);

	public long getBiospecimenUidTemplateCount(BiospecimenUidTemplate modelObject);

	public List<BiospecimenUidToken> getBiospecimenUidTokens();

	public List<BiospecimenUidPadChar> getBiospecimenUidPadChars();

	public List<BiospecimenUidTemplate> searchPageableBiospecimenUidTemplates(BiospecimenUidTemplate object, int first, int count);

	public BiospecimenUidTemplate searchBiospecimenUidTemplate(BiospecimenUidTemplate biospecimenUidTemplate);
	
	/**
	 * Gets a list of Studys that are already assigned to barcodeLabels
	 * @return
	 */
	public List<Study> getStudyListAssignedToBarcodeLabel();

	/**
	 * Gets a list of Studys that are already assigned to biospeicmenUidTemplates
	 * @return
	 */
	public List<Study> getStudyListAssignedToBiospecimenUidTemplate();
	
	public boolean studyHasBiospecimens(Study study);
	
	/**
	 * Gets the list of barcodeLabelData for the specified barcodeLabel
	 * @param barcodeLabel
	 * @return
	 */
	public List<BarcodeLabelData> getBarcodeLabelDataByBarcodeLabel(BarcodeLabel barcodeLabel);
	
	/**
	 * Gets the list of barcodeLabelData for the specified barcodeLabel
	 * @param barcodeLabel
	 * @return
	 */
	public List<BioShipmentLabelData> getBioShipmentLabelDataByBioShipmentLabel(BioShipmentLabel bioShipmentLabel);

	/**
	 * Get the barcode label template for the specified barcodeLabel
	 * @param barcodeLabel
	 * @return
	 */
	public String getBarcodeLabelTemplate(BarcodeLabel barcodeLabel);
	
	/**
	 * Get the barcode label template for the specified barcodeLabel
	 * @param barcodeLabel
	 * @return
	 */
	public String getBioShipmentLabelTemplate(BioShipmentLabel bioShipmentLabel);

	/**
	 * Get the barcode labels for the specified study
	 * @param study
	 * @return
	 */
	public List<BarcodeLabel> getBarcodeLabelsByStudy(Study study);
	
	/**
	 * Get a list of BarcodeLabel templates
	 * @return
	 */
	public List<BarcodeLabel> getBarcodeLabelTemplates();
	
	/**
	 * Get a list of BarcodeLabel templates
	 * @return
	 */
	public List<BioShipmentLabel> getBioShipmentLabelTemplates();

	public Long getBarcodeLabelCount(BarcodeLabel object, List<Study> studyListForUser);
	
	public Long getBioShipmentLabelCount(BioShipmentLabel object, List<Study> studyListForUser);

	public List<BarcodeLabel> searchPageableBarcodeLabels(BarcodeLabel object, int first, int count, List<Study> studyListForUser);

	public List<BioShipmentLabel> searchPageableBioShipmentLabels(BioShipmentLabel object, int first, int count, List<Study> studyListForUser);
}