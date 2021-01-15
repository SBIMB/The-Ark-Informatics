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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.exception.EntityNotFoundException;
import au.org.theark.core.model.shipment.entity.ShipmentLabel;
import au.org.theark.core.model.study.entity.ArkModule;
import au.org.theark.core.model.study.entity.ArkUser;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.security.ArkPermissionHelper;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.vo.ArkUserVO;
import au.org.theark.core.web.component.AbstractContainerPanel;
import au.org.theark.core.web.component.ArkDataProvider;
import za.ac.theark.shipment.service.IShipmentAdminService;
import za.ac.theark.shipment.web.component.shipmentlabel.form.ContainerForm;

/**
 * 
 * @author Freedom Mukomana
 * 
 */
public class BioshipmentLabelContainerPanel extends AbstractContainerPanel<ShipmentLabel> {

	private static final long	serialVersionUID	= 2114933695455527870L;

	private static final Logger										log					= LoggerFactory.getLogger(BioshipmentLabelContainerPanel.class);

	protected CompoundPropertyModel<ShipmentLabel>				cpModel;

	protected ArkCrudContainerVO										arkCrudContainerVO;

	protected WebMarkupContainer										arkContextMarkup;
	protected ContainerForm												containerForm;
	protected Panel														resultsListPanel;

	private ArkDataProvider<ShipmentLabel, ILimsAdminService>	dataProvider;
	private DataView<ShipmentLabel>									dataView;
	
	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService<Void>									iArkCommonService;

	@SpringBean(name = au.org.theark.shipment.web.Constants.LIMS_ADMIN_SERVICE)
	private ILimsAdminService												iLimsAdminService;

	public BioshipmentLabelContainerPanel(String id, WebMarkupContainer arkContextMarkup) {
		super(id);
		this.arkContextMarkup = arkContextMarkup;
		ShipmentLabel bioshipmentLabel = new ShipmentLabel();
		Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		if(sessionStudyId != null) {
			Study study = iArkCommonService.getStudy(sessionStudyId);
			bioshipmentLabel.setStudy(study);
		}
		cpModel = new CompoundPropertyModel<ShipmentLabel>(shipmentLabel);
		arkCrudContainerVO = new ArkCrudContainerVO();

		initialisePanel();
	}

	public void initialisePanel() {
		containerForm = new ContainerForm("containerForm", cpModel);

		containerForm.add(initialiseFeedBackPanel());
		containerForm.add(initialiseSearchPanel());
		containerForm.add(initialiseSearchResults());
		containerForm.add(initialiseDetailPanel());
		this.add(containerForm);
	}

	protected WebMarkupContainer initialiseSearchPanel() {
		WebMarkupContainer searchPanelContainer = arkCrudContainerVO.getSearchPanelContainer();
		SearchPanel searchPanel = new SearchPanel("searchPanel", feedBackPanel, containerForm, arkCrudContainerVO);
		searchPanel.initialisePanel();
		searchPanelContainer.add(searchPanel);
		return searchPanelContainer;
	}

	protected WebMarkupContainer initialiseSearchResults() {
		WebMarkupContainer resultListContainer = arkCrudContainerVO.getSearchResultPanelContainer();
		resultListContainer.setOutputMarkupPlaceholderTag(true);

		SearchResultsPanel searchResultsPanel = new SearchResultsPanel("resultListPanel", containerForm, arkCrudContainerVO);

		initialiseDataView();
		dataView = searchResultsPanel.buildDataView(dataProvider);
		dataView.setItemsPerPage(iArkCommonService.getRowsPerPage());
		PagingNavigator pageNavigator = new PagingNavigator("navigator", dataView);
		searchResultsPanel.add(pageNavigator);
		searchResultsPanel.add(dataView);
		arkCrudContainerVO.getSearchResultPanelContainer().add(searchResultsPanel);
		return arkCrudContainerVO.getSearchResultPanelContainer();
	}

	@Override
	protected WebMarkupContainer initialiseDetailPanel() {
		DetailPanel detailPanel = new DetailPanel("detailPanel", feedBackPanel, arkCrudContainerVO, containerForm);
		detailPanel.initialisePanel();
		arkCrudContainerVO.getDetailPanelContainer().add(detailPanel);
		return arkCrudContainerVO.getDetailPanelContainer();
	}

	private void initialiseDataView() {
		// Data provider to paginate resultList
		dataProvider = new ArkDataProvider<BioShipmentLabel, ILimsAdminService>(iLimsAdminService) {

			private static final long	serialVersionUID	= 1L;
			List<Study> studyListForUser =  getStudyListForUser();

			public int size() {
				return (int)service.getBioShipmentLabelCount(model.getObject(), studyListForUser).intValue();
			}

			public Iterator<BioShipmentLabel> iterator(int first, int count) {
				List<BioShipmentLabel> listCollection = new ArrayList<BioShipmentLabel>();
				if (ArkPermissionHelper.isActionPermitted(au.org.theark.core.Constants.SEARCH)) {
					listCollection = service.searchPageableBioShipmentLabels(model.getObject(), first, count, studyListForUser);
				}
				return listCollection.iterator();
			}
		};
		// Set the criteria into the data provider's model
		dataProvider.setModel(new LoadableDetachableModel<BioShipmentLabel>() {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected BioShipmentLabel load() {
				return cpModel.getObject();
			}
		});
	}
	
	/**
	 * Returns a list of Studies the user is permitted to access
	 * 
	 * @return
	 */
	private List<Study> getStudyListForUser() {
		List<Study> studyListForUser = new ArrayList<Study>(0);
		try {
			Subject currentUser = SecurityUtils.getSubject();
			ArkUser arkUser = iArkCommonService.getArkUser(currentUser.getPrincipal().toString());
			ArkUserVO arkUserVo = new ArkUserVO();
			arkUserVo.setArkUserEntity(arkUser);

			Long sessionArkModuleId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.ARK_MODULE_KEY);
			ArkModule arkModule = null;
			arkModule = iArkCommonService.getArkModuleById(sessionArkModuleId);
			studyListForUser = iArkCommonService.getStudyListForUserAndModule(arkUserVo, arkModule);
		}
		catch (EntityNotFoundException e) {
			log.error(e.getMessage());
		}
		return studyListForUser;
	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}
}