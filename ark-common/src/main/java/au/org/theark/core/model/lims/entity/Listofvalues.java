package au.org.theark.core.model.lims.entity;

// Generated 14/06/2011 1:11:20 PM by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Listofvalues generated by hbm2java
 */
@Entity
@Table(name = "listofvalues", catalog = "lims")
public class Listofvalues implements java.io.Serializable
{

	private int			id;
	private String		timestamp;
	private Integer	studyId;
	private Integer	deleted;
	private String		type;
	private String		value;
	private Integer	sortorder;
	private Integer	groupId;
	private String		description;
	private String		parenttype;
	private String		parentvalue;
	private int			iseditable;
	private String		language;

	public Listofvalues()
	{
	}

	public Listofvalues(int id, int iseditable, String language)
	{
		this.id = id;
		this.iseditable = iseditable;
		this.language = language;
	}

	public Listofvalues(int id, Integer studyId, Integer deleted, String type, String value, Integer sortorder, Integer groupId, String description, String parenttype, String parentvalue,
			int iseditable, String language)
	{
		this.id = id;
		this.studyId = studyId;
		this.deleted = deleted;
		this.type = type;
		this.value = value;
		this.sortorder = sortorder;
		this.groupId = groupId;
		this.description = description;
		this.parenttype = parenttype;
		this.parentvalue = parentvalue;
		this.iseditable = iseditable;
		this.language = language;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
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

	@Column(name = "STUDY_ID")
	public Integer getStudyId()
	{
		return this.studyId;
	}

	public void setStudyId(Integer studyId)
	{
		this.studyId = studyId;
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

	@Column(name = "TYPE", length = 100)
	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "VALUE", length = 100)
	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Column(name = "SORTORDER")
	public Integer getSortorder()
	{
		return this.sortorder;
	}

	public void setSortorder(Integer sortorder)
	{
		this.sortorder = sortorder;
	}

	@Column(name = "GROUP_ID")
	public Integer getGroupId()
	{
		return this.groupId;
	}

	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "PARENTTYPE", length = 65535)
	public String getParenttype()
	{
		return this.parenttype;
	}

	public void setParenttype(String parenttype)
	{
		this.parenttype = parenttype;
	}

	@Column(name = "PARENTVALUE", length = 65535)
	public String getParentvalue()
	{
		return this.parentvalue;
	}

	public void setParentvalue(String parentvalue)
	{
		this.parentvalue = parentvalue;
	}

	@Column(name = "ISEDITABLE", nullable = false)
	public int getIseditable()
	{
		return this.iseditable;
	}

	public void setIseditable(int iseditable)
	{
		this.iseditable = iseditable;
	}

	@Column(name = "LANGUAGE", nullable = false, length = 20)
	public String getLanguage()
	{
		return this.language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

}
