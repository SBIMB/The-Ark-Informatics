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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.org.theark.core.model.Constants;
import au.org.theark.core.model.lims.entity.BarcodeLabelData;
import au.org.theark.core.model.study.entity.Study;

/**
 *
 * ShipmentCourier Entity
 * @author Freedom Mukomana
 *
 */
@Entity
@Table(name = "shipment_courier", schema = Constants.LIMS_TABLE_SCHEMA)
public class ShipmentCourier implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Long                            id;
    private ShipOrganisation                    shipOrganisation;
    private String                            name;
    private String                            description;
    private String                            labelPrefix;
    private String                            labelSuffix;
    private List<BarcodeLabelData>            barcodeLabelData    = new ArrayList<BarcodeLabelData>(0);

    public ShipmentCourier() {
    }

    public ShipmentCourier(Long id) {
        this.id = id;
    }

    public ShipmentCourier(Long id, ShipOrganisation shipOrganisation, String name, String description, String labelPrefix, String labelSuffix, List<BarcodeLabelData> barcodeLabelData) {
        super();
        this.id = id;
        this.shipOrganisation = shipOrganisation;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDY_ID")
    public ShipOrganisation getShipOrganisation() {
        return this.shipOrganisation;
    }

    public void setShipOrgamisation(ShipOrganisation shipOrganisation) {
        this.shipOrganisation = shipOrganisation;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "barcodeLabel")
    public List<BarcodeLabelData> getBarcodeLabelData() {
        return this.barcodeLabelData;
    }

    public void setBarcodeLabelData(List<BarcodeLabelData> barcodeLabelData) {
        this.barcodeLabelData = barcodeLabelData;
    }
}
