package au.org.theark.core.model.study.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import au.org.theark.core.Constants;

@Entity
@Table(name = "FAMILY_CUSTOM_FIELD_DATA", schema = Constants.STUDY_SCHEMA)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class FamilyCustomFieldData implements Serializable, ICustomFieldData {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String familyUid;
	private CustomFieldDisplay customFieldDisplay;
	private Study study;
	private String textDataValue;
	private Date dateDataValue;
	private String errorDataValue;
	private Double numberDataValue;
	
	public FamilyCustomFieldData() {
	
	}
	
	@Id
	@SequenceGenerator(name = "family_custom_field_data_generator", sequenceName = "FAMILY_CUSTOM_FIELD_DATA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "family_custom_field_data_generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "FAMILY_UID")
	public String getFamilyUid() {
		return familyUid;
	}

	public void setFamilyUid(String familyUid) {
		this.familyUid = familyUid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOM_FIELD_DISPLAY_ID")
	public CustomFieldDisplay getCustomFieldDisplay() {
		return customFieldDisplay;
	}

	public void setCustomFieldDisplay(CustomFieldDisplay customFieldDisplay) {
		this.customFieldDisplay = customFieldDisplay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDY_ID")
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DATA_VALUE")
	public Date getDateDataValue() {
		return dateDataValue;
	}

	public void setDateDataValue(Date dateDataValue) {
		this.dateDataValue = dateDataValue;
	}

	@Column(name = "ERROR_DATA_VALUE")
	public String getErrorDataValue() {
		return errorDataValue;
	}

	public void setErrorDataValue(String errorDataValue) {
		this.errorDataValue = errorDataValue;
	}

	@Column(name = "NUMBER_DATA_VALUE")
	public Double getNumberDataValue() {
		return numberDataValue;
	}

	public void setNumberDataValue(Double numberDataValue) {
		this.numberDataValue = numberDataValue;
	}

	@Column(name = "TEXT_DATA_VALUE")
	public String getTextDataValue() {
		return textDataValue;
	}

	public void setTextDataValue(String textDataValue) {
		this.textDataValue = textDataValue;
	}

}