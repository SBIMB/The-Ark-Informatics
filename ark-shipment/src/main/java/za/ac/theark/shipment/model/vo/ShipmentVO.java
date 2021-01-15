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
package za.ac.theark.shipment.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.org.theark.core.model.lims.entity.InvBox;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.vo.ArkVo;

/**
 * @author Freedom Mukomana
 *
 */
public class ShipmentVO implements ArkVo, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long					id;
	protected Study					study;
	private String					shipmentUID;
	private String					name;
	private String					description;
	
	private Organisation			shippingCourier;
	private String					trackingNumber;
	
	private Date					shippedDate;
	private Date					receivedDate;
	private ShipmentMethod			shipmentMethod;
	private ShipmentStatus			shipmentStatus;
	private ShipmentType			shipmentType;
	
	private Organisation			shipmentClient;
	private Staff					shipmentContactPerson;
	private String					comments;
	private List<InvBox>			shippedBoxes;
	
	/** A List of Study(s) for the user in context */
	protected List<Study>					studyList;
		
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the study
	 */
	public Study getStudy() {
		return study;
	}

	/**
	 * @param study
	 *           the study to set
	 */
	public void setStudy(Study study) {
		this.study = study;
	}

	/**
	 * @return the shipmentUID
	 */
	public String getShipmentUID() {
		return shipmentUID;
	}

	/**
	 * @param shipmentUID the shipmentUID to set
	 */
	public void setShipmentUID(String shipmentUID) {
		this.shipmentUID = shipmentUID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the shippingCourier
	 */
	public Organisation getShippingCourier() {
		return shippingCourier;
	}

	/**
	 * @param shippingCourier the shippingCourier to set
	 */
	public void setShippingCourier(Organisation shippingCourier) {
		this.shippingCourier = shippingCourier;
	}

	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}

	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	/**
	 * @return the shippedDate
	 */
	public Date getShippedDate() {
		return shippedDate;
	}

	/**
	 * @param dateOfShipment the shippedDate to set
	 */
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	/**
	 * @return the dateReceived
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param dateReceived the dateReceived to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the shipmentMethod
	 */
	public ShipmentMethod getShipmentMethod() {
		return shipmentMethod;
	}

	/**
	 * @param shipmentMethod the shipmentMethod to set
	 */
	public void setShipmentMethod(ShipmentMethod shipmentMethod) {
		this.shipmentMethod = shipmentMethod;
	}

	/**
	 * @return the shipmentStatus
	 */
	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}

	/**
	 * @param shipmentStatus the shipmentStatus to set
	 */
	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	/**
	 * @return the shipmentType
	 */
	public ShipmentType getShipmentType() {
		return shipmentType;
	}

	/**
	 * @param shipmentType the shipmentType to set
	 */
	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}

	/**
	 * @return the shipOrganisation
	 */
	public Organisation getShipmentClient() {
		return shipmentClient;
	}

	/**
	 * @param shippingClient the shippingClient to set
	 */
	public void setShipmentClient(Organisation shipmentClient) {
		this.shipmentClient = shipmentClient;
	}

	/**
	 * @return the shipmentContactPerson
	 */
	public Staff getShipmentContactPerson() {
		return shipmentContactPerson;
	}

	/**
	 * @param shipmentContactPerson the shipmentContactPerson to set
	 */
	public void setShipmentContactPerson(Staff shipmentContactPerson) {
		this.shipmentContactPerson = shipmentContactPerson;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the shipmentBoxes
	 */
	public List<InvBox> getShippedBoxes() {
		return shippedBoxes;
	}

	/**
	 * @param shipmentBoxes the shipmentBoxes to set
	 */
	public void setShippedBoxes(List<InvBox> shippedBoxes) {
		this.shippedBoxes = shippedBoxes;
	}

	public String getArkVoName() {
		return "Shipment";
	}
	
	/**
	 * @return the studyList
	 */
	public List<Study> getStudyList() {
		return studyList;
	}

	/**
	 * @param studyList
	 *           the studyList to set
	 */
	public void setStudyList(List<Study> studyList) {
		this.studyList = studyList;
	}

}
