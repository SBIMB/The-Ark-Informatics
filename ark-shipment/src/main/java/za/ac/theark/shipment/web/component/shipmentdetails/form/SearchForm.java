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
package za.ac.theark.shipment.web.component.shipmentdetails.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.exception.EntityNotFoundException;
import au.org.theark.core.model.shipment.entity.Shipment;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.study.entity.ArkUser;
import au.org.theark.core.model.study.entity.EthnicityType;
import au.org.theark.core.model.study.entity.GenderType;
import au.org.theark.core.model.study.entity.LinkSubjectStudy;
import au.org.theark.core.model.study.entity.OtherID;
import au.org.theark.core.model.study.entity.Person;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.model.study.entity.SubjectStatus;
import au.org.theark.core.model.study.entity.VitalStatus;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.util.ContextHelper;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.vo.ArkUserVO;
import au.org.theark.core.vo.LimsVO;
import au.org.theark.core.vo.SubjectVO;
import au.org.theark.core.web.StudyHelper;
import au.org.theark.core.web.component.ArkDatePicker;
import au.org.theark.core.web.form.AbstractSearchForm;
import za.ac.theark.shipment.model.vo.ShipmentVO;
import za.ac.theark.shipment.service.IShipmentService;
import za.ac.theark.shipment.util.Constants;

/**
 * @author nivedann
 * 
 */
public class SearchForm extends AbstractSearchForm<ShipmentVO> {
	private static final long						serialVersionUID	= 1L;
	protected static final Logger				log					= LoggerFactory.getLogger(SearchForm.class);

	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService						iArkCommonService;

	@SpringBean(name = au.org.theark.core.Constants.STUDY_SERVICE)
	private IShipmentService							iShipmentService;
	
	protected WebMarkupContainer arkContextMarkup;
	protected WebMarkupContainer studyNameMarkup;
	protected WebMarkupContainer studyLogoMarkup;

	private DropDownChoice<Study>					studyDdc;
	private TextField<String>						shipmentUIDTxtFld;
	private TextField<String>						trackingNumberTxtFld;
	protected TextField<String>						nameTxtFld;
	private DateTextField							shippedDateTxtFld;
	private DateTextField							receivedDateTxtFld;
	private DropDownChoice<ShipmentType>			shipmentTypeDdc;

	// TODO get explanation never accessed, yet we can set it - maybe wicket can access?
	//private PageableListView<SubjectVO>			listView;
	private CompoundPropertyModel<ShipmentVO>	cpmModel;

	/**
	 * @param id
	 * @param cpmModel
	 * @param arkContextMarkup 
	 * @param studyNameMarkup s
	 * @param studyLogoMarkup
	 */
	public SearchForm(String id, CompoundPropertyModel<ShipmentVO> cpmModel, PageableListView<ShipmentVO> listView, FeedbackPanel feedBackPanel, ArkCrudContainerVO arkCrudContainerVO, WebMarkupContainer arkContextMarkup, WebMarkupContainer studyNameMarkup, WebMarkupContainer studyLogoMarkup) {
		// super(id, cpmModel);
		super(id, cpmModel, feedBackPanel, arkCrudContainerVO);

		this.cpmModel = cpmModel;
		//this.listView = listView;
		this.arkContextMarkup = arkContextMarkup;
		this.studyNameMarkup = studyNameMarkup;
		this.studyLogoMarkup = studyLogoMarkup;
		
		initialiseSearchForm();
		addSearchComponentsToForm();
		Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		disableSearchForm(sessionStudyId, "There is no study selected. Please select a study.");
	}

	protected void addSearchComponentsToForm() {
		add(studyDdc);
		add(shipmentUIDTxtFld);
		add(trackingNumberTxtFld);
		add(nameTxtFld);
		add(shippedDateTxtFld);
		add(receivedDateTxtFld);
		add(shipmentTypeDdc);
	}

	protected void initialiseSearchForm() {
		initStudyDdc();
		shipmentUIDTxtFld = new TextField<String>(Constants.SHIPMENT_UID);
		nameTxtFld = new TextField<String>(Constants.NAME);
		trackingNumberTxtFld = new TextField<String>(Constants.TRACKING_NUMBER);
		
		shippedDateTxtFld = new DateTextField(Constants.SHIPPED_DATE, new PatternDateConverter(au.org.theark.core.Constants.DD_MM_YYYY,false));
		ArkDatePicker shippedDatePicker = new ArkDatePicker();
		shippedDatePicker.bind(shippedDateTxtFld);
		shippedDateTxtFld.add(shippedDatePicker);
		
		receivedDateTxtFld = new DateTextField(Constants.RECEIVED_DATE, new PatternDateConverter(au.org.theark.core.Constants.DD_MM_YYYY,false));
		ArkDatePicker receivedDatePicker = new ArkDatePicker();
		receivedDatePicker.bind(receivedDateTxtFld);
		receivedDateTxtFld.add(receivedDatePicker);
		
		initShipmentTypeDdc();

		//dateOfBirthTxtFld = new DateTextField(Constants.PERSON_DOB, new PatternDateConverter(au.org.theark.core.Constants.DD_MM_YYYY,false));
		//ArkDatePicker dobDatePicker = new ArkDatePicker();
		//dobDatePicker.bind(dateOfBirthTxtFld);
		//dateOfBirthTxtFld.add(dobDatePicker);
	}
	
	@SuppressWarnings("unchecked")
	private void initStudyDdc() {
		CompoundPropertyModel<ShipmentVO> limsCpm = cpmModel;
		PropertyModel<Study> studyPm = new PropertyModel<Study>(limsCpm, "study");
		List<Study> studyListForUser = new ArrayList<Study>(0);
		try {
			Subject currentUser = SecurityUtils.getSubject();
			ArkUser arkUser = iArkCommonService.getArkUser(currentUser.getPrincipal().toString());
			ArkUserVO arkUserVo = new ArkUserVO();
			arkUserVo.setArkUserEntity(arkUser);
			
			//Long sessionArkModuleId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.ARK_MODULE_KEY);
			//ArkModule arkModule = null;
			//arkModule = iArkCommonService.getArkModuleById(sessionArkModuleId);
			//studyListForUser = iArkCommonService.getStudyListForUserAndModule(arkUserVo, arkModule);
			
			Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
			if(sessionStudyId != null) {
				studyListForUser = iArkCommonService.getParentAndChildStudies(sessionStudyId);
				cpmModel.getObject().setStudyList(studyListForUser);
			}
		}
		catch (EntityNotFoundException e) {
			log.error(e.getMessage());
		}
		ChoiceRenderer<Study> studyRenderer = new ChoiceRenderer<Study>(Constants.NAME, Constants.ID);
		studyDdc = new DropDownChoice<Study>("study", studyPm, (List<Study>) studyListForUser, studyRenderer){ 
			@Override
			protected void onBeforeRender() {
				Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
				Study study = iArkCommonService.getStudy(sessionStudyId);
				cpmModel.getObject().setStudy(study);
				super.onBeforeRender();
			};
		};
	}

	@SuppressWarnings("unchecked")
	private void initShipmentTypeDdc() {
		CompoundPropertyModel<ShipmentVO> shipmentCpm = cpmModel;
		PropertyModel<Shipment> shipmentPm = new PropertyModel<Shipment>(shipmentCpm, "shipment");
		PropertyModel<ShipmentType> shipmentTypePm = new PropertyModel<ShipmentType>(shipmentPm, Constants.SHIPMENT_TYPE);
		Collection<ShipmentType> shipmentTypeList = iArkCommonService.getGenderTypes();
		ChoiceRenderer shipmentTypeRenderer = new ChoiceRenderer(Constants.NAME, Constants.ID);
		shipmentTypeDdc = new DropDownChoice<ShipmentType>(Constants.SHIPMENT_TYPE, shipmentTypePm, (List) shipmentTypeList, shipmentTypeRenderer);
	}

	@SuppressWarnings("unchecked")
	protected void onNew(AjaxRequestTarget target) {
		// Disable SubjectUID if auto-generation set
		Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		Study studyInContext = iArkCommonService.getStudy(sessionStudyId);
		
		TextField<String> subjectUIDTxtFld = (TextField<String>) arkCrudContainerVO.getDetailPanelFormContainer().get(Constants.SHIPMENT_UID);
		subjectUIDTxtFld.setEnabled(true);
		target.add(subjectUIDTxtFld);

		// Available child studies
		/*List<Study> availableChildStudies = new ArrayList<Study>(0);
		if (studyInContext.getParentStudy() != null) {
			availableChildStudies = iShipmentService.getChildStudyListOfParent(studyInContext);
		}
		getModelObject().setAvailableChildStudies(availableChildStudies);

		getModelObject().getShipment().getPerson().getOtherIDs().clear();
		if(!otherIDTxtFld.getValue().isEmpty()) {
			OtherID searchedOtherID = new OtherID();
			searchedOtherID.setOtherID(otherIDTxtFld.getValue());
			searchedOtherID.setPerson(cpmModel.getObject().getLinkSubjectStudy().getPerson());
			getModelObject().getLinkSubjectStudy().getPerson().getOtherIDs().add(searchedOtherID);
		}*/
		
		preProcessDetailPanel(target);
	}

	protected void onSearch(AjaxRequestTarget target) {
		target.add(feedbackPanel);
		//Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		//getModelObject().getLinkSubjectStudy().setStudy(iArkCommonService.getStudy(sessionStudyId));
		
		//getModelObject().getLinkSubjectStudy().getStudy();

		String otherIDSearch = otherIDTxtFld.getValue();
		if(otherIDSearch != null) {
			OtherID o = new OtherID();
			o.setOtherID(otherIDSearch);
			List<OtherID> otherIDs = new ArrayList<OtherID>();
			otherIDs.add(o);
			cpmModel.getObject().getLinkSubjectStudy().getPerson().getOtherIDs().clear();//setOtherIDs(otherIDs);
			log.info("onsearch otherids: " + cpmModel.getObject().getLinkSubjectStudy().getPerson().getOtherIDs());
			cpmModel.getObject().getLinkSubjectStudy().getPerson().getOtherIDs().add(o);
		}
		long count = iArkCommonService.getStudySubjectCount(cpmModel.getObject());
		if (count == 0L) {
			this.info("There are no subjects with the specified search criteria.");
			target.add(feedbackPanel);
		}
		arkCrudContainerVO.getSearchResultPanelContainer().setVisible(true);
		target.add(arkCrudContainerVO.getSearchResultPanelContainer());// For ajax this is required so
	}

	@Override
	protected void onBeforeRender() {
		Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		Study study = iArkCommonService.getStudy(sessionStudyId);
		cpmModel.getObject().getShipment().setStudy(study);
		boolean parentStudy = (study.getParentStudy() == null || (study.getParentStudy() == study)) && cpmModel.getObject().isEnableNewButton();
		newButton.setEnabled(parentStudy);
		super.onBeforeRender();
	}
}
