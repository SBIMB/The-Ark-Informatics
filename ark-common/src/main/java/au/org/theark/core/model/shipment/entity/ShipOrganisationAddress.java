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
import au.org.theark.core.Constants;
import au.org.theark.core.model.study.entity.AddressStatus;
import au.org.theark.core.model.study.entity.AddressType;
import au.org.theark.core.model.study.entity.Country;
import au.org.theark.core.model.study.entity.State;

/**
 *
 * OrganisationAddress Entity
 * @author Freedom Mukomana
 *
 */
@Entity
@Table(name = "ORGANISATION_ADDRESS", schema = Constants.STUDY_SCHEMA)
public class ShipOrganisationAddress implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ShipOrganisation shipOrganisation;
    private String addressLineOne;
    private String streetAddress;
    private String postCode;
    private String city;
    private Country country;
    private State state;
    private String otherState;
    private AddressStatus addressStatus;
    private AddressType addressType;
    private Boolean preferredMailingAddress;
    private String source;

    public ShipOrganisationAddress() {
    }

    public ShipOrganisationAddress(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "address_generator", sequenceName = "ADDRESS_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "address_generator")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANISATION_ID")
    public ShipOrganisation getShipOrganisation() {
        return shipOrganisation;
    }

    public void setShipOrganisation(ShipOrganisation shipOrganisation) {
        this.shipOrganisation = shipOrganisation;
    }

    // TODO Lets keep naming consistant
    @Column(name = "STREET_ADDRESS")
    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Column(name = "POST_CODE", length = 10)
    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "CITY", length = 30)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_STATUS_ID")
    public AddressStatus getAddressStatus() {
        return this.addressStatus;
    }

    public void setAddressStatus(AddressStatus addressStatus) {
        this.addressStatus = addressStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_TYPE_ID")
    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Column(name = "OTHER_STATE", length = 45)
    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    @Column(name = "PREFERRED_MAILING_ADDRESS", nullable = false)
    public Boolean getPreferredMailingAddress() {
        return preferredMailingAddress;
    }

    public void setPreferredMailingAddress(Boolean preferredMailingAddress) {
        this.preferredMailingAddress = preferredMailingAddress;
    }

    @Column(name = "ADDRESS_LINE_1")
    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
