package au.org.theark.core.model.lims.entity;

// Generated 15/06/2011 1:22:58 PM by Hibernate Tools 3.3.0.GA

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
import javax.persistence.Version;

import au.org.theark.core.model.Constants;

/**
 * InvCell generated by hbm2java
 */
@Entity
@Table(name = "inv_cell", schema = Constants.LIMS_TABLE_SCHEMA)
public class InvCell implements java.io.Serializable
{

	private int				id;
	private String			timestamp;
	private InvTray		invTray;
	private Biospecimen	biospecimen;
	private Integer		patientId;
	private Integer		deleted;
	private Integer		rowno;
	private Integer		colno;
	private String			status;

	public InvCell()
	{
	}

	public InvCell(int id, InvTray invTray)
	{
		this.id = id;
		this.invTray = invTray;
	}

	public InvCell(int id, InvTray invTray, Biospecimen biospecimen, Integer patientId, Integer deleted, Integer rowno, Integer colno, String status)
	{
		this.id = id;
		this.invTray = invTray;
		this.biospecimen = biospecimen;
		this.patientId = patientId;
		this.deleted = deleted;
		this.rowno = rowno;
		this.colno = colno;
		this.status = status;
	}

	@Id
	@SequenceGenerator(name="invcell_generator", sequenceName="INVCELL_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "invcell_generator")
	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Version
	@Column(name = "TIMESTAMP", length = 55)
	public String getTimestamp()
	{
		return this.timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAY_ID", nullable = false)
	public InvTray getInvTray()
	{
		return this.invTray;
	}

	public void setInvTray(InvTray invTray)
	{
		this.invTray = invTray;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BIOSPECIMEN_ID")
	public Biospecimen getBiospecimen()
	{
		return this.biospecimen;
	}

	public void setBiospecimen(Biospecimen biospecimen)
	{
		this.biospecimen = biospecimen;
	}

	@Column(name = "PATIENT_ID")
	public Integer getPatientId()
	{
		return this.patientId;
	}

	public void setPatientId(Integer patientId)
	{
		this.patientId = patientId;
	}

	@Column(name = "DELETED")
	public Integer getDeleted()
	{
		return this.deleted;
	}

	public void setDeleted(Integer deleted)
	{
		this.deleted = deleted;
	}

	@Column(name = "ROWNO")
	public Integer getRowno()
	{
		return this.rowno;
	}

	public void setRowno(Integer rowno)
	{
		this.rowno = rowno;
	}

	@Column(name = "COLNO")
	public Integer getColno()
	{
		return this.colno;
	}

	public void setColno(Integer colno)
	{
		this.colno = colno;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
