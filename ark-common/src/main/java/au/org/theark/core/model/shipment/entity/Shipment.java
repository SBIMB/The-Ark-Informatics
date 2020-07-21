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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.web.bind.annotation.CrossOrigin;

import au.org.theark.core.model.Constants;
import au.org.theark.core.model.lims.entity.BarcodeLabelData;
import au.org.theark.core.model.lims.entity.InvBox;
import au.org.theark.core.model.study.entity.Study;

/**
 *
 * Shipment Entity
 * @author Freedom Mukomana
 *
 */
@Entity
@Table(name = "shipment", schema = Constants.LIMS_TABLE_SCHEMA)
public class Shipment implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Long							id;
    private Study							study;
    private String							shipmentUID;
    private String							name;
    private String							description;
    
    private Organisation					shippingCourier;
    private String							trackingNumber;
    
    private Date	                        shippedDate;
    private Date							receivedDate;
    private ShipmentStatus					shipmentStatus;
    private ShipmentMethod					shipmentMethod;
    private ShipmentType                    shipmentType;
    
    private Organisation					shippingClient;
    private Staff							shipmentContactPerson;
    private String							comments;
    private List<InvBox>					invBoxes			= new ArrayList<InvBox>();
    
    private List<BarcodeLabelData>			barcodeLabelData    = new ArrayList<BarcodeLabelData>(0);
    private String							labelPrefix;
    private String							labelSuffix;

    public Shipment() {
    }

    public Shipment(Long id) {
        this.id = id;
    }

    public Shipment(Long id, String shipmentUID, String name, String description, String labelPrefix, String labelSuffix, List<BarcodeLabelData> barcodeLabelData) {
        super();
        this.id = id;
        this.shipmentUID = shipmentUID;
        this.name = name;
        this.description = description;
        this.labelPrefix = labelPrefix;
        this.labelSuffix = labelSuffix;
        this.barcodeLabelData = barcodeLabelData;
    }

    @Id
    @SequenceGenerator(name = "barcodelabel_generator", sequenceName = "BARCODELABEL_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "barcodelabel_generator")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Long getId() {
        return this.id;
    }

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
	 * @param study the study to set
	 */
	public void setStudy(Study study) {
		this.study = study;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDY_ID")
    public String getShipmentUID() {
        return this.shipmentUID;
    }

    public void setShipmentUID(String shipmentUID) {
        this.shipmentUID = shipmentUID;
    }
    
    @Column(name = "NAME", length = 50, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", length = 255)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_COURIER_ID")
    public Organisation getShippingCourier() {
        return shippingCourier;
    }
    
    public void setShippingCourier(Organisation shippingCourier) {
        this.shippingCourier = shippingCourier;
    }
    
    @Column(name = "TRACKING_NUMBER")
    public String getTrackingNumber() {
        return trackingNumber;
    }
     
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_CLIENT_ID")
    public Organisation getShippingClient() {
        return shippingClient;
    }
    
    public void setShippingClient(Organisation shippingClient) {
        this.shippingClient = shippingClient;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPMENT_STATUS_ID")
    public ShipmentStatus getShipmentStatus(){
        return shipmentStatus;
    }
    
	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	
    @Temporal(TemporalType.DATE)
    @Column(name = "SHIPPED_DATE", length=7)
    public Date getShippedDate() {
        return shippedDate;
    }
    
    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECEIVED_DATE_ID")
	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPMENT_METHOD_ID")
    public ShipmentMethod getShipmentMethod() {
        return shipmentMethod;
    }
    
    public void setShipmentMethod(ShipmentMethod shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPMENT_TYPE_ID")
	public ShipmentType getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_PERSON_ID")
	public Staff getShipmentContactPerson() {
		return shipmentContactPerson;
	}

	public void setShipmentContactPerson(Staff shipmentContactPerson) {
		this.shipmentContactPerson = shipmentContactPerson;
	}

	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@OrderBy(value="trackingNumber")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shipment") 
	public List<InvBox> getInvBoxes() {
		return invBoxes;
	}

	public void setInvBoxes(List<InvBox> invBoxes) {
		this.invBoxes = invBoxes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "barcodeLabel")
    public List<BarcodeLabelData> getBarcodeLabelData() {
        return this.barcodeLabelData;
    }

    /**
     * @param barcodeLabelData
     *           the barcodeLabelData to set
     */
    public void setBarcodeLabelData(List<BarcodeLabelData> barcodeLabelData) {
        this.barcodeLabelData = barcodeLabelData;
    }
        
    @Column(name = "LABEL_PREFIX", length = 50, nullable = false)
    public String getLabelPrefix() {
        return this.labelPrefix;
    }

    public void setLabelPrefix(String labelPrefix) {
        this.labelPrefix = labelPrefix;
    }

    @Column(name = "LABEL_SUFFIX", length = 50, nullable = false)
    public String getLabelSuffix() {
        return this.labelSuffix;
    }

    public void setLabelSuffix(String labelSuffix) {
        this.labelSuffix = labelSuffix;
    }
}
