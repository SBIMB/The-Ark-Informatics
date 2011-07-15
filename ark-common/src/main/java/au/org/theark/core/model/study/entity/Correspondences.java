package au.org.theark.core.model.study.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

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

import au.org.theark.core.Constants;

@Entity
@Table(name = "correspondences", schema = Constants.STUDY_SCHEMA)
public class Correspondences implements Serializable {

	private Long								id;
	private Person								person;
	private Study								study;
	private CorrespondenceStatusType		correspondenceStatusType;
	private ArkUser							operator;
	private Date								date;
	private String								time;
	private String								reason;
	private CorrespondenceModeType		correspondenceModeType;
	private CorrespondenceDirectionType	correspondenceDirectionType;
	private CorrespondenceOutcomeType	correspondenceOutcomeType;
	private String								details;
	private String								comments;
	private String								attachmentFilename;
	private Blob								attachmentPayload;

	@Id
	@SequenceGenerator(name = "correspondences_generator", sequenceName = "CORRESPONDENCES_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "correspondences_generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDY_ID")
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS_TYPE_ID")
	public CorrespondenceStatusType getCorrespondenceStatusType() {
		return correspondenceStatusType;
	}

	public void setCorrespondenceStatusType(CorrespondenceStatusType correspondenceStatusType) {
		this.correspondenceStatusType = correspondenceStatusType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARK_USER_ID")
	public ArkUser getOperator() {
		return operator;
	}

	public void setOperator(ArkUser operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE", length = 7)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "TIME", length = 255)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "REASON", length = 4096)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODE_TYPE_ID")
	public CorrespondenceModeType getCorrespondenceModeType() {
		return correspondenceModeType;
	}

	public void setCorrespondenceModeType(CorrespondenceModeType correspondenceModeType) {
		this.correspondenceModeType = correspondenceModeType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTION_TYPE_ID")
	public CorrespondenceDirectionType getCorrespondenceDirectionType() {
		return correspondenceDirectionType;
	}

	public void setCorrespondenceDirectionType(CorrespondenceDirectionType correspondenceDirectionType) {
		this.correspondenceDirectionType = correspondenceDirectionType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OUTCOME_TYPE_ID")
	public CorrespondenceOutcomeType getCorrespondenceOutcomeType() {
		return correspondenceOutcomeType;
	}

	public void setCorrespondenceOutcomeType(CorrespondenceOutcomeType correspondenceOutcomeType) {
		this.correspondenceOutcomeType = correspondenceOutcomeType;
	}

	@Column(name = "DETAILS", length = 4096)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Column(name = "COMMENTS", length = 4096)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "ATTACHMENT_FILENAME", length = 255)
	public String getAttachmentFilename() {
		return attachmentFilename;
	}

	public void setAttachmentFilename(String attachmentFilename) {
		this.attachmentFilename = attachmentFilename;
	}

	@Column(name = "ATTACHMENT_PAYLOAD")
	public Blob getAttachmentPayload() {
		return attachmentPayload;
	}

	public void setAttachmentPayload(Blob attachmentPayload) {
		this.attachmentPayload = attachmentPayload;
	}

}
