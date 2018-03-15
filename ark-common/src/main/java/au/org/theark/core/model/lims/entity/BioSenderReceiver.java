/*******************************************************************************
 * Copyright (c) 2011  University of Western Australia. All rights reserved.
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
package au.org.theark.core.model.lims.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import au.org.theark.core.model.Constants;
import au.org.theark.core.model.study.entity.Address;
import au.org.theark.core.model.study.entity.BusinessContact;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.vo.PersonVO;

/**
 * 
 * @author freedom
 * 
 */
@Entity
@Table(name = "biosenderreceiver", schema = Constants.LIMS_TABLE_SCHEMA)
public class BioSenderReceiver implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Long							id;
	private PersonVO						person;
	private String							position;
	private List<Address>					addresses = new ArrayList<Address>();
	private List<BusinessContact>			businessPhones = new ArrayList<BusinessContact>(0);
	

	public BioSenderReceiver() {
	}

	public BioSenderReceiver(Long id) {
		this.id = id;
	}

	public BioSenderReceiver(Long id, Study study, PersonVO person, String position, List<Address> addresses, List<BusinessContact> businessPhones) {
		super();
		this.id = id;
		this.person = person;
		this.position = position;
		this.addresses = addresses;
		this.businessPhones = businessPhones;
	}

	@Id
	@SequenceGenerator(name = "bioshipment_generator", sequenceName = "BIOSHIPMENT_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bioshipment_generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy="bioSenderReceiver")
	public PersonVO getPerson() {
		return this.person;
	}

	public void setPerson(PersonVO person) {
		this.person = person;
	}

	@Column(name = "POSITION", length = 255)
	public String getPosition() {
		return this.position;
	}

	public void setDescription(String position) {
		this.position = position;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bioSenderReceiver")
	public List<Address> getPhysicalAddress() {
		return addresses;
	}

	public void setPhysicalAddress(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bioSenderReceiver")
	public List<BusinessContact> getBusinessPhones() {
		return businessPhones;
	}

	public void setBusinessPhones(List<BusinessContact> businessPhones) {
		this.businessPhones = businessPhones;
	}
}
