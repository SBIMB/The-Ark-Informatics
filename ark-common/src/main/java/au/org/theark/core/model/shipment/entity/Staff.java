/*******************************************************************************
 * Copyright (c) 2020  University of Witwatersrand. All rights reserved.
 * 
 * This file is part of The Ark.
 * 
 * The Ark is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * The Ark is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package au.org.theark.core.model.shipment.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import au.org.theark.core.Constants;
import au.org.theark.core.audit.annotations.ArkAuditDisplay;
import au.org.theark.core.model.study.entity.PersonContactMethod;
import au.org.theark.core.model.study.entity.TitleType;

/**
 * 
 * ShipmentStaff Entity
 * @author Freedom Mukomana
 * 
 */
@Entity
@Table(name = "SHIPMENT_STAFF", schema = Constants.STUDY_SCHEMA)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Staff implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long 					id;
	private String 					firstName;
	private String 					middleName;
	private String 					lastName;
	private String 					preferredName;
	private TitleType 				titleType;
	private Organisation		organisation;
	private JobTitle 				jobTitle;
	private PersonContactMethod 	personContactMethod;

	private Set<StaffPhone> 		staffPhones = new HashSet<StaffPhone>(0);
	private Set<StaffEmailAccount> 	staffEmailAccounts = new HashSet<StaffEmailAccount>(0);


	public Staff() {
	}

	public Staff(Long id) {
		this.id = id;
	}

	public Staff(Long id, String firstName, String middleName, String lastName, String preferredName,
			TitleType titleType, Organisation organisation, JobTitle jobTitle,
			PersonContactMethod personContactMethod, Set<StaffPhone> staffPhones,
			Set<StaffEmailAccount> staffEmailAccounts) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.preferredName = preferredName;
		this.titleType = titleType;
		this.organisation = organisation;
		this.jobTitle = jobTitle;
		this.personContactMethod = personContactMethod;
		this.staffPhones = staffPhones;
		this.staffEmailAccounts = staffEmailAccounts;
	}

	@Id
	@SequenceGenerator(name = "person_generator", sequenceName = "PERSON_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "person_generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@ArkAuditDisplay
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "FIRST_NAME", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "MIDDLE_NAME", length = 50)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "LAST_NAME", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "PREFERRED_NAME", length = 150)
	public String getPreferredName() {
		return this.preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TITLE_TYPE_ID")
	public TitleType getTitleType() {
		return this.titleType;
	}

	public void setTitleType(TitleType titleType) {
		this.titleType = titleType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_TITLE_ID")
	public JobTitle getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_TITLE_ID")
	public Organisation getShipOrganisation() {
		return this.organisation;
	}

	public void setShipOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
	public Set<StaffEmailAccount> getStaffEmailAccounts() {
		return staffEmailAccounts;
	}

	public void setStaffEmailAccounts(Set<StaffEmailAccount> staffEmailAccounts) {
		this.staffEmailAccounts = staffEmailAccounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
	public Set<StaffPhone> getStaff() {
		return this.staffPhones;
	}

	public void setStaffPhones(
			Set<StaffPhone> staffPhones) {
		this.staffPhones = staffPhones;
	}
	
	/**
	 * @param personContactMethod
	 *            the personContactMethod to set
	 */
	public void setPersonContactMethod(PersonContactMethod personContactMethod) {
		this.personContactMethod = personContactMethod;
	}

	/**
	 * @return the personContactMethod
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_CONTACT_METHOD_ID")
	public PersonContactMethod getPersonContactMethod() {
		return personContactMethod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((personContactMethod == null) ? 0 : personContactMethod.hashCode());
		result = prime * result + ((preferredName == null) ? 0 : preferredName.hashCode());
		result = prime * result + ((organisation == null) ? 0 : organisation.hashCode());
		result = prime * result + ((staffEmailAccounts == null) ? 0 : staffEmailAccounts.hashCode());
		result = prime * result + ((staffPhones == null) ? 0 : staffPhones.hashCode());
		result = prime * result + ((titleType == null) ? 0 : titleType.hashCode());
		return result;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Staff other = (Staff) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		if (personContactMethod == null) {
			if (other.personContactMethod != null)
				return false;
		} else if (!personContactMethod.equals(other.personContactMethod))
			return false;
		if (preferredName == null) {
			if (other.preferredName != null)
				return false;
		} else if (!preferredName.equals(other.preferredName))
			return false;
		if (organisation == null) {
			if (other.organisation != null)
				return false;
		} else if (!organisation.equals(other.organisation))
			return false;
		if (staffEmailAccounts == null) {
			if (other.staffEmailAccounts != null)
				return false;
		} else if (!staffEmailAccounts.equals(other.staffEmailAccounts))
			return false;
		if (staffPhones == null) {
			if (other.staffPhones != null)
				return false;
		} else if (!staffPhones.equals(other.staffPhones))
			return false;
		if (titleType == null) {
			if (other.titleType != null)
				return false;
		} else if (!titleType.equals(other.titleType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + "]";
	}

}

