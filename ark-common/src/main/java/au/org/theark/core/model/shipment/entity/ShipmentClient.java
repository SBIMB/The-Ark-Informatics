
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import au.org.theark.core.model.Constants;

/**
 *
 * ShipmentClient Entity
 * @author Freedom Mukomana
 *
 */
@Entity
@Table(name = "shipment_client", schema = Constants.LIMS_TABLE_SCHEMA)
public class ShipmentClient implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Long                            id;
    private ShipOrganisation                shipOrganisation;

    public ShipmentClient() {
    }

    public ShipmentClient(Long id) {
        this.id = id;
    }

    public ShipmentClient(Long id, ShipOrganisation shipOrganisation) {
        super();
        this.id = id;
        this.shipOrganisation = shipOrganisation;
    }

    @Id
    @SequenceGenerator(name = "shipmentclient_generator", sequenceName = "SHIPMENTCLIENT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "shipmentclient_generator")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy="shipmentClient")
    public ShipOrganisation getShipOrganisation() {
        return this.shipOrganisation;
    }

    public void setPerson(ShipOrganisation shipOrganisation) {
        this.shipOrganisation = shipOrganisation;
    }
}
