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
package au.org.theark.lims.web.component.bioshipmentlabel.form;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.model.lims.entity.BioShipmentLabel;
import au.org.theark.core.model.lims.entity.BioShipmentLabelData;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.web.form.AbstractDetailForm;
import au.org.theark.lims.service.ILimsAdminService;
import au.org.theark.lims.web.Constants;
import au.org.theark.lims.web.component.bioshipmentlabeldata.BioShipmentLabelDataPanel;
import au.org.theark.lims.web.component.button.PrinterListPanel;

/**
 * @author cellis
 * 
 */
public class DetailForm extends AbstractDetailForm<BioShipmentLabel> {


	private static final long					serialVersionUID	= 6808980290575067265L;

	protected static final Logger				log					= LoggerFactory.getLogger(DetailForm.class);

	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService<Void>			iArkCommonService;

	@SpringBean(name = au.org.theark.lims.web.Constants.LIMS_ADMIN_SERVICE)
	private ILimsAdminService					iLimsAdminService;

	private TextField<Long>						idTxtFld;
	private DropDownChoice<Study>				studyDdc;
	private TextField<String>					nameTxtFld;
	private TextArea<String>					descriptionTxtArea;
	private TextField<Number>					versionTxtFld;
	
	private Panel 								bioShipmentLabelDataPanel;
	private Label								bioShipmentLabelTemplateLbl;
	private DropDownChoice<BioShipmentLabel>	bioShipmentLabelTemplateDdc;
	private TextArea<String> 					exampleBioShipmentDataFile;
	StringValue BioShipmentPrinterName;
	
	private PrinterListPanel printerList;

	/**
	 * 
	 * @param id
	 * @param feedBackPanel
	 * @param arkCrudContainerVO
	 * @param containerForm
	 */
	public DetailForm(String id, FeedbackPanel feedBackPanel, ArkCrudContainerVO arkCrudContainerVO, ContainerForm containerForm) {
		super(id, feedBackPanel, containerForm, arkCrudContainerVO);
		this.feedBackPanel = feedBackPanel;
	}

	public void initialiseDetailForm() {
		idTxtFld = new TextField<Long>("id");
		nameTxtFld = new TextField<String>("name");
		nameTxtFld.setEnabled(false);
		descriptionTxtArea = new TextArea<String>("description");
		versionTxtFld = new TextField<Number>("version");
		
		bioShipmentLabelTemplateLbl = new Label("bioShipmentLabelTemplateLbl", "Clone from BioShipment Label Template:"){

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				this.setVisible(isNew());
			}
		};

		initStudyDdc();
		initBioShipmentLabelTemplateDdc();
		
		bioShipmentLabelDataPanel = new EmptyPanel("bioShipmentLabelDataPanel");
		exampleBioShipmentDataFile = new TextArea<String>("exampleBioShipmentDataFile", new Model<String>(""));
		exampleBioShipmentDataFile.setEnabled(false);
		exampleBioShipmentDataFile.setVisible(isNew());

		addDetailFormComponents();
		attachValidators();
	}
	
	@Override
	public void onBeforeRender() {
		if(!isNew()) {
			BioShipmentLabelDataPanel bioShipmentLabelDataPanel = new BioShipmentLabelDataPanel("BioShipmentLabelDataPanel", containerForm.getModelObject(), feedBackPanel);
			bioShipmentLabelDataPanel.initialisePanel();
			arkCrudContainerVO.getDetailPanelFormContainer().addOrReplace(bioShipmentLabelDataPanel);
			exampleBioShipmentDataFile.setModelObject(iLimsAdminService.getBioShipmentLabelTemplate(containerForm.getModelObject()));
		}
		
		/*String selected = new String();
		if(containerForm.getModelObject().getBioShipmentPrinterName() != null) {
			selected = containerForm.getModelObject().getBioShipmentPrinterName();
		}
		printerList = new PrinterListPanel("printerList", selected, isNew());
		printerList.add(new AbstractDefaultAjaxBehavior() {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
				super.renderHead(component, response);
				response.renderOnLoadJavaScript("findPrinters()");
				String js = "function callWicket(selectedPrinter) { var wcall = wicketAjaxGet ('"
				    + getCallbackUrl() + "&selectedPrinter='+selectedPrinter, function() { }, function() { } ) }";
				response.renderJavaScript(js, "selectPrinter");
			}

			@Override
			protected void respond(AjaxRequestTarget arg0) {
				BioShipmentPrinterName = RequestCycle.get().getRequest().getQueryParameters().getParameterValue("selectedPrinter");
			}
		});*/
		arkCrudContainerVO.getDetailPanelFormContainer().addOrReplace(printerList);
		super.onBeforeRender();
	}

	private void initStudyDdc() {
		List<Study> studyListForUser = new ArrayList<Study>(0);
		studyListForUser = getStudyListForUser();
		ChoiceRenderer<Study> choiceRenderer = new ChoiceRenderer<Study>(Constants.NAME, Constants.ID);
		studyDdc = new DropDownChoice<Study>("study", studyListForUser, choiceRenderer) {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				studyDdc.setEnabled(isNew());
				studyDdc.setChoices(getStudyListForUser());
			}
		};

		studyDdc.add(new AjaxFormComponentUpdatingBehavior("onChange") {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(bioShipmentLabelTemplateDdc);
			}
		});
	}
	
	private void initBioShipmentLabelTemplateDdc() {
		ChoiceRenderer<BioShipmentLabel> choiceRenderer = new ChoiceRenderer<BioShipmentLabel>("nameAndVersion", Constants.ID);
		bioShipmentLabelTemplateDdc = new DropDownChoice<BioShipmentLabel>("BioShipmentLabelTemplate") {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				setVisible(isNew());
				List<BioShipmentLabel> choices = iLimsAdminService.getBioShipmentLabelTemplates();
				this.setChoices(choices);
			}
		};
		bioShipmentLabelTemplateDdc.setOutputMarkupPlaceholderTag(true);
		bioShipmentLabelTemplateDdc.setChoiceRenderer(choiceRenderer);
		
		bioShipmentLabelTemplateDdc.add(new AjaxFormComponentUpdatingBehavior("onChange") {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// Clone data from other BioShipmentLabel
				if (bioShipmentLabelTemplateDdc.getModelObject() != null) {
					String labelPrefix = bioShipmentLabelTemplateDdc.getModelObject().getLabelPrefix();
					String labelSuffix = bioShipmentLabelTemplateDdc.getModelObject().getLabelSuffix();
					Long version = bioShipmentLabelTemplateDdc.getModelObject().getVersion();
					
					// Set default/required values
					containerForm.getModelObject().setLabelPrefix(labelPrefix);
					containerForm.getModelObject().setLabelSuffix(labelSuffix);
					containerForm.getModelObject().setVersion(version);
					
					exampleBioShipmentDataFile.setModelObject(iLimsAdminService.getBioShipmentLabelTemplate(bioShipmentLabelTemplateDdc.getModelObject()));
					exampleBioShipmentDataFile.setVisible(true);
					target.add(exampleBioShipmentDataFile);
					
					nameTxtFld.setModelObject(bioShipmentLabelTemplateDdc.getModelObject().getName());
					target.add(nameTxtFld);

					versionTxtFld.setModelObject(version);
					target.add(versionTxtFld);
				}
			}
		});
	}

	public void addDetailFormComponents() {
		arkCrudContainerVO.getDetailPanelFormContainer().add(idTxtFld.setEnabled(false));
		arkCrudContainerVO.getDetailPanelFormContainer().add(nameTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(studyDdc);
		arkCrudContainerVO.getDetailPanelFormContainer().add(descriptionTxtArea);
		arkCrudContainerVO.getDetailPanelFormContainer().add(versionTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(bioShipmentLabelDataPanel);
		arkCrudContainerVO.getDetailPanelFormContainer().add(bioShipmentLabelTemplateLbl);
		arkCrudContainerVO.getDetailPanelFormContainer().add(bioShipmentLabelTemplateDdc);
		arkCrudContainerVO.getDetailPanelFormContainer().add(exampleBioShipmentDataFile);
		add(arkCrudContainerVO.getDetailPanelFormContainer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#attachValidators()
	 */
	@Override
	protected void attachValidators() {
		nameTxtFld.setRequired(true).setLabel(new StringResourceModel("error.name.required", this, new Model<String>("Name")));
		studyDdc.setRequired(true).setLabel(new StringResourceModel("error.study.required", this, new Model<String>("Study")));
		versionTxtFld.setRequired(true).setLabel(new StringResourceModel("error.version.required", this, new Model<String>("Version")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#onCancel(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onCancel(AjaxRequestTarget target) {
		containerForm.setModelObject(new BioShipmentLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#onDeleteConfirmed(org.apache.wicket.ajax.AjaxRequestTarget, java.lang.String,
	 * org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow)
	 */
	@Override
	protected void onDeleteConfirmed(AjaxRequestTarget target, String selection) {
		iLimsAdminService.deleteBioShipmentLabel(containerForm.getModelObject());
		containerForm.info("The BioShipment label record was deleted successfully.");
		editCancelProcess(target);
		onCancel(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#processErrors(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void processErrors(AjaxRequestTarget target) {
		target.add(feedBackPanel);
	}

	@Override
	protected void onSave(Form<BioShipmentLabel> containerForm, AjaxRequestTarget target) {
		if(BioShipmentPrinterName == null) {
			this.error("BioShipment Printer is required");
		}
		else {
			//containerForm.getModelObject().setBioShipmentPrinterName(BioShipmentPrinterName.toString());
		
			if (isNew()) {
				if (bioShipmentLabelTemplateDdc.getModelObject() != null) {
					List<BioShipmentLabelData> cloneBioShipmentLabelDataList = iLimsAdminService.getBioShipmentLabelDataByBioShipmentLabel(bioShipmentLabelTemplateDdc.getModelObject());
					List<BioShipmentLabelData> BioShipmentLabelDataList = new ArrayList<BioShipmentLabelData>(0);
					for (Iterator<BioShipmentLabelData> iterator = cloneBioShipmentLabelDataList.iterator(); iterator.hasNext();) {
						BioShipmentLabelData cloneBioShipmentLabelData = (BioShipmentLabelData) iterator.next();
						BioShipmentLabelData BioShipmentLabelData = new BioShipmentLabelData();
						// Copy parent details to new BioShipmentLabelData
						try {
							PropertyUtils.copyProperties(BioShipmentLabelData, cloneBioShipmentLabelData);
						}
						catch (IllegalAccessException e) {
							log.error(e.getMessage());
						}
						catch (InvocationTargetException e) {
							log.error(e.getMessage());
						}
						catch (NoSuchMethodException e) {
							log.error(e.getMessage());
						}
						BioShipmentLabelData.setId(null);
						BioShipmentLabelDataList.add(BioShipmentLabelData);
					}
					containerForm.getModelObject().setBioShipmentLabelData(BioShipmentLabelDataList);
				}
				
				iLimsAdminService.createBioShipmentLabel(containerForm.getModelObject());
			}
			else {
				iLimsAdminService.updateBioShipmentLabel(containerForm.getModelObject());
			}
			this.info("BioShipment label: " + containerForm.getModelObject().getName() + " was created/updated successfully.");
		}
		target.add(feedBackPanel);
		onSavePostProcess(target);
	}
	
	/*@Override
	protected void onTest(Form<BioShipmentLabel> containerForm, AjaxRequestTarget target) {
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#isNew()
	 */
	@Override
	protected boolean isNew() {
		if (containerForm.getModelObject().getId() == null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns study in context
	 * 
	 * @return
	 */
	private List<Study> getStudyListForUser() {
		List<Study> studyListForUser = new ArrayList<Study>(0);
		Long sessionStudyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		Study study = null;
		if(sessionStudyId != null) {
			study = iArkCommonService.getStudy(sessionStudyId);
			studyListForUser.add(study);
		}
		return studyListForUser;
	}
}
