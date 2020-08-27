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
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import au.org.theark.core.Constants;
import au.org.theark.core.model.study.entity.PhoneStatus;
import au.org.theark.core.model.study.entity.PhoneType;
import au.org.theark.core.model.study.entity.YesNo;

/**
 *
 * ShipOrganisationPhone Entity
 * @author Freedom Mukomana
 *
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "ORGANISATION_PHONE", schema = Constants.STUDY_SCHEMA, uniqueConstraints = { @UniqueConstraint(columnNames = {
        "AREA_CODE", "PHONE_NUMBER", "PERSON_ID" }) })
public class OrganisationPhone implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private PhoneType phoneType;
    private Organisation  organisation;
    private String phoneNumber;
    private String areaCode;
    private PhoneStatus phoneStatus;
    private YesNo silentMode;
    private String comment;
    private Boolean preferredPhoneNumber;


    /** default constructor */
    public OrganisationPhone() {
    }

    public OrganisationPhone(Long id) {
        this.id = id;
    }

    public OrganisationPhone(Long id, PhoneType phoneType, Organisation organisation,
            String phoneNumber, String areaCode) {
        this.id = id;
        this.phoneType = phoneType;
        this.organisation = organisation;
        this.phoneNumber = phoneNumber;
        this.areaCode = areaCode;
    }

    @Id
    @SequenceGenerator(name = "organisational_phone_generator", sequenceName = "ORGANISATIONAL_PHONE_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organisational_phone_generator")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHONE_TYPE_ID")
    public PhoneType getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANISATION_ID")
    public Organisation getShipOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Column(name = "PHONE_NUMBER", length = 10)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "AREA_CODE", length = 10)
    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHONE_STATUS_ID")
    public PhoneStatus getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(PhoneStatus phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    /*
     * TODO : Java does already have a concept like this ... boolean ... does
     * this code have a legacy reason?
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SILENT")
    public YesNo getSilentMode() {
        return silentMode;
    }

    public void setSilentMode(YesNo silentMode) {
        this.silentMode = silentMode;
    }

    @Column(name = "COMMENT", length = 1000)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "PREFERRED_PHONE_NUMBER", length = 10)
    public Boolean getPreferredPhoneNumber() {
        return preferredPhoneNumber;
    }

    public void setPreferredPhoneNumber(Boolean preferredPhoneNumber) {
        this.preferredPhoneNumber = preferredPhoneNumber;
    }
    
    
}
