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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import au.org.theark.core.model.Constants;

/**
 *
 * ShipOrganisation Entity
 * @author Freedom Mukomana
 *
 */
@Entity
@Table(name = "ship_organisation", schema = Constants.LIMS_TABLE_SCHEMA)
public class ShipOrganisation implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Long                            id;
    private String                            name;
    
    private Set<ShipOrganisationAddress>        shipOrganisationalAddress    =    new HashSet<ShipOrganisationAddress>(0);
    private Set<ShipOrganisationPhone>            shipOrganisationPhones        =     new HashSet<ShipOrganisationPhone>(0);
    private Set<ShipmentStaff>                     shipmentStaffs                =     new HashSet<ShipmentStaff>(0);
    
    public ShipOrganisation() {
    }

    public ShipOrganisation(Long id) {
        this.id = id;
    }

    public ShipOrganisation(Long id, String name, ShipOrganisationAddress shipOrganisationalAddress) {
        super();
        this.id = id;
        this.name = name;
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

    @Column(name = "NAME", length = 50, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organisation")
    public Set<ShipOrganisationAddress> getShipOrganisationalAddress() {
        return shipOrganisationalAddress;
    }

    public void setShipOrganisationalAddress(Set<ShipOrganisationAddress> shipOrganisationalAddress) {
        this.shipOrganisationalAddress = shipOrganisationalAddress;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shipOrganisation")
    public Set<ShipOrganisationPhone> getPhones() {
        return this.shipOrganisationPhones;
    }

    public void setShipOrganisationPhones(Set<ShipOrganisationPhone> phones) {
        this.shipOrganisationPhones = phones;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organisation")
    public Set<ShipmentStaff> getShipmentStaffs() {
        return this.shipmentStaffs;
    }

    public void setShipStaffs(Set<ShipmentStaff> shipmentStaffs) {
        this.shipmentStaffs = shipmentStaffs;
    }
}

