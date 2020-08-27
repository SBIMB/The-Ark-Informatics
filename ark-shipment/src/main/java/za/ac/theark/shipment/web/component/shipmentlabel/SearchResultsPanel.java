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
package za.ac.theark.shipment.web.component.shipmentlabel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.model.lims.entity.BioShipmentLabel;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.web.component.ArkCRUDHelper;
import au.org.theark.core.web.component.ArkDataProvider;
import au.org.theark.core.web.component.link.ArkBusyAjaxLink;
import au.org.theark.lims.service.ILimsAdminService;
import au.org.theark.lims.web.component.bioshipmentlabel.form.ContainerForm;

public class SearchResultsPanel extends Panel {

	private static final long	serialVersionUID	= -5165716102918554398L;

	protected transient Logger	log					= LoggerFactory.getLogger(SearchResultsPanel.class);

	@SpringBean(name = au.org.theark.lims.web.Constants.LIMS_ADMIN_SERVICE)
	private ILimsAdminService		iLimsAdminService;

	private ContainerForm		containerForm;
	private ArkCrudContainerVO	arkCrudContainerVo;

	public SearchResultsPanel(String id, ContainerForm containerForm, ArkCrudContainerVO arkCrudContainerVo) {
		super(id);
		this.containerForm = containerForm;
		this.arkCrudContainerVo = arkCrudContainerVo;
	}

	public DataView<BioShipmentLabel> buildDataView(ArkDataProvider<BioShipmentLabel, ILimsAdminService> dataProvider) {
		DataView<BioShipmentLabel> dataView = new DataView<BioShipmentLabel>("resultList", dataProvider) {

			private static final long	serialVersionUID	= 2981419595326128410L;

			@Override
			protected void populateItem(final Item<BioShipmentLabel> item) {
				BioShipmentLabel bioShipmentLabel = item.getModelObject();

				item.add(buildLink(bioShipmentLabel));

				if (bioShipmentLabel.getStudy() != null) {
					// the ID here must match the ones in mark-up
					item.add(new Label("study", bioShipmentLabel.getStudy().getName()));
				}
				else {
					item.add(new Label("study", ""));
				}
				
				if (bioShipmentLabel.getName() != null) {
					// the ID here must match the ones in mark-up
					item.add(new Label("name", bioShipmentLabel.getName()));
				}
				else {
					item.add(new Label("name", ""));
				}
						

				if (bioShipmentLabel.getDescription() != null) {
					// the ID here must match the ones in mark-up
					item.add(new Label("description", bioShipmentLabel.getDescription()));
				}
				else {
					item.add(new Label("description", ""));
				}
				
				if (bioShipmentLabel.getVersion() != null) {
					item.add(new Label("version", bioShipmentLabel.getVersion().toString()));
				}
				else {
					item.add(new Label("version", ""));
				}

				item.add(new AttributeModifier("class", new AbstractReadOnlyModel<String>() {
					private static final long	serialVersionUID	= 1L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}
		};
		return dataView;
	}

	@SuppressWarnings( { "unchecked" })
	private AjaxLink buildLink(final BioShipmentLabel bioShipmentLabel) {
		ArkBusyAjaxLink link = new ArkBusyAjaxLink("link") {

			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				BioShipmentLabel bioShipmentLabelFromDb = iLimsAdminService.searchBioShipmentLabel(bioShipmentLabel);
				bioShipmentLabelFromDb.setBioShipmentLabelData(iLimsAdminService.getBioShipmentLabelDataByBioShipmentLabel(bioShipmentLabelFromDb));
				containerForm.setModelObject(bioShipmentLabelFromDb);
				
				ArkCRUDHelper.preProcessDetailPanelOnSearchResults(target, arkCrudContainerVo);
				// Refresh base container form to remove any feedBack messages
				target.add(containerForm);
			}
		};

		// Add the label for the link
		Label linkLabel = new Label("id", bioShipmentLabel.getId().toString());
		link.add(linkLabel);
		return link;
	}
}
