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
package au.org.theark.core.web.component.customfieldcategory.form;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;

import au.org.theark.core.exception.ArkRunTimeException;
import au.org.theark.core.exception.ArkRunTimeUniqueException;
import au.org.theark.core.exception.ArkSystemException;
import au.org.theark.core.exception.ArkUniqueException;
import au.org.theark.core.exception.EntityCannotBeRemoved;
import au.org.theark.core.exception.EntityNotFoundException;
import au.org.theark.core.model.study.entity.ArkFunction;
import au.org.theark.core.model.study.entity.CustomFieldCategory;
import au.org.theark.core.model.study.entity.CustomFieldType;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.vo.CustomFieldCategoryVO;
import au.org.theark.core.web.behavior.ArkDefaultFormFocusBehavior;
import au.org.theark.core.web.component.audit.button.HistoryButtonPanel;
import au.org.theark.core.web.component.customfieldcategory.Constants;
import au.org.theark.core.web.form.AbstractDetailForm;

/**
 * CustomField's DetailForm
 * 
 * Follows a slightly different abstraction model again trying to improve on top of the existing CRUD abstraction classes. Of note: - Receive a
 * (compound property) model instead of the container form, which should make it lighter than passing the container form in. - We do not have to pass
 * in the container form's model/VO, but instead this can use an independent model for an independent VO.
 * 
 * @auther elam
 * 
 *         Does not use the containerForm under the revised abstraction.
 */
@SuppressWarnings( { "serial", "unchecked", "unused" })
public class DetailForm extends AbstractDetailForm<CustomFieldCategoryVO> {
	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService<Void>				iArkCommonService;

	private int											mode;

	private TextField<String>						categoryIdTxtFld;
	private TextField<String>						categoryNameTxtFld;
	private TextArea<String>						categoryDescriptionTxtAreaFld;
	private DropDownChoice<CustomFieldType>			customFieldTypeDdc;
	private DropDownChoice<CustomFieldCategory>		parentCategoryDdc;
	private TextField<Long>							categoryOrderNoTxtFld;
	
	protected WebMarkupContainer					customFieldCategoryDetailWMC;
	private Collection<CustomFieldCategory> 		customFieldCategoryCollection;
	private HistoryButtonPanel 						historyButtonPanel;
	private  WebMarkupContainer						parentPanel;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param cpModel
	 * @param feedBackPanel
	 * @param arkCrudContainerVO
	 */
	public DetailForm(String id, CompoundPropertyModel<CustomFieldCategoryVO> cpModel, FeedbackPanel feedBackPanel, ArkCrudContainerVO arkCrudContainerVO){//,boolean unitTypeDropDownOn) {
		super(id, feedBackPanel, cpModel, arkCrudContainerVO);
		refreshEntityFromBackend();
	}

	protected void refreshEntityFromBackend()  {
		CustomFieldCategory cfFromBackend = null;
		// Refresh the entity from the backend
		if (getModelObject().getCustomFieldCategory().getId() != null) {
			try {
				cfFromBackend = iArkCommonService.getCustomFieldCategory(getModelObject().getCustomFieldCategory().getId());
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getModelObject().setCustomFieldCategory(cfFromBackend);
		}
	}

	
	/**
	 * Call this after the constructor is finished
	 */
	public void initialiseDetailForm() {
		categoryIdTxtFld = new TextField<String>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_ID);
		categoryNameTxtFld = new TextField<String>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_NAME);
		categoryNameTxtFld.add(new ArkDefaultFormFocusBehavior());
		categoryDescriptionTxtAreaFld = new TextArea<String>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_DESCRIPTION);
		categoryOrderNoTxtFld=new TextField<Long>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_ORDERNUMBER);
		
		initCustomFieldTypeDdc();
		initParentCategoryDdc();
		deleteButton.setEnabled(false);

		//defaultValueTextArea = new TextArea<String>("customField.defaultValue");
		
		addDetailFormComponents();
		attachValidators();

		historyButtonPanel = new HistoryButtonPanel(this, arkCrudContainerVO.getEditButtonContainer(), arkCrudContainerVO.getDetailPanelFormContainer());
	}
	/**
	 * initialize Custom Filed Types.
	 */
	private void initCustomFieldTypeDdc() {
		parentPanel=new WebMarkupContainer("parentPanel");
		parentPanel.setOutputMarkupId(true);
		java.util.Collection<CustomFieldType> customFieldTypeCollection = iArkCommonService.getCustomFieldTypes();
		ChoiceRenderer customfieldTypeRenderer = new ChoiceRenderer(Constants.CUSTOM_FIELD_TYPE_NAME, Constants.CUSTOM_FIELD_TYPE_ID);
		customFieldTypeDdc = new DropDownChoice<CustomFieldType>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_CUSTOM_FIELD_TYPE, (List) customFieldTypeCollection, customfieldTypeRenderer);
		customFieldTypeDdc.add(new AjaxFormComponentUpdatingBehavior("onchange") {
		private static final long serialVersionUID = 1L;
				@Override
			    protected void onUpdate(AjaxRequestTarget target) {
					cpModel.getObject().getCustomFieldCategory().setCustomFieldType(customFieldTypeDdc.getModelObject());
					Collection<CustomFieldCategory> customFieldCategoryCollection=getAvailableCategoryCollectionFromModel();
					parentPanel.remove(parentCategoryDdc);
					ChoiceRenderer customfieldCategoryRenderer = new ChoiceRenderer(Constants.CUSTOMFIELDCATEGORY_NAME, Constants.CUSTOMFIELDCATEGORY_ID);
					parentCategoryDdc = new DropDownChoice<CustomFieldCategory>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_PARENTCATEGORY, (List) customFieldCategoryCollection, customfieldCategoryRenderer);
					parentCategoryDdc.setOutputMarkupId(true);
					parentPanel.add(parentCategoryDdc);
			    	target.add(parentCategoryDdc);
			    	target.add(parentPanel);
			    }
			});
		
	}
	/**
	 * Initialize Custom Field Categories.
	 */
	private void initParentCategoryDdc() {
		Collection<CustomFieldCategory> customFieldCategoryCollection=getAvailableCategoryCollectionFromModel();
		ChoiceRenderer customfieldCategoryRenderer = new ChoiceRenderer(Constants.CUSTOMFIELDCATEGORY_NAME, Constants.CUSTOMFIELDCATEGORY_ID);
		parentCategoryDdc = new DropDownChoice<CustomFieldCategory>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_PARENTCATEGORY, (List) customFieldCategoryCollection, customfieldCategoryRenderer);
		parentCategoryDdc.setOutputMarkupId(true);
	}
	/**
	 * initilize parent categories.
	 *//*
	private void initCustomFieldCategoryDdc() {
		CustomFieldCategory customFieldCategory=cpModel.getObject().getCustomFieldCategory(); 
		Study study=customFieldCategory.getStudy();
		ArkFunction arkFunction=customFieldCategory.getArkFunction();
		CustomFieldType customFieldType=customFieldCategory.getCustomFieldType();
		List<CustomFieldCategory> customfieldCategoryCollection = null;
		try {
			customfieldCategoryCollection = iArkCommonService.getAvailableParentCategoryList(study, arkFunction);
		} catch (ArkSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChoiceRenderer customFieldeCategoryRenderer = new ChoiceRenderer(Constants.CUSTOMFIELDCATEGORY_NAME, Constants.CUSTOMFIELDCATEGORY_ID);
		parentCategoryDdc = new DropDownChoice<CustomFieldCategory>(Constants.FIELDVO_CUSTOMFIELDCATEGORY_PARENTCATEGORY, customfieldCategoryCollection, customFieldeCategoryRenderer) {
			@Override
			protected void onBeforeRender() {
				if (!isNew()) {
					// Disable fieldType if data exists for the field
					//setEnabled(!cpModel.getObject().getCustomFieldCategory().getCustomFieldHasData());
				}
				super.onBeforeRender();
			}
		};
		parentCategoryDdc.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			protected void onUpdate(AjaxRequestTarget target) {
				if(parentCategoryDdc != null) {
					target.add(parentCategoryDdc);
				}
			}
		});
	}*/
	
	
	/**
	 * Get custom field category collection from model.
	 * @return
	 */
	private Collection<CustomFieldCategory> getAvailableCategoryCollectionFromModel(){
		CustomFieldCategory customFieldCategory=cpModel.getObject().getCustomFieldCategory(); 
		Study study=customFieldCategory.getStudy();
		ArkFunction arkFunction=customFieldCategory.getArkFunction();
		CustomFieldType customFieldType=customFieldCategory.getCustomFieldType();
		
		try {
			if(isNew()){
				customFieldCategoryCollection = iArkCommonService.getAvailableAllCategoryListByCustomFieldType(study, arkFunction, customFieldType);
			}else{
				customFieldCategoryCollection= iArkCommonService.getAvailableAllCategoryListByCustomFieldTypeExceptThis(study, arkFunction, customFieldType,customFieldCategory);
			}
		} catch (ArkSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customFieldCategoryCollection;
	}
	
	

	protected void attachValidators() {
		categoryNameTxtFld.setRequired(true);
		// Enforce particular characters for fieldName
		categoryNameTxtFld.add(new PatternValidator("[a-zA-Z0-9_-]+"));
		customFieldTypeDdc.setRequired(true);
		categoryOrderNoTxtFld.setRequired(true);
	}

	@Override
	protected void onSave(Form<CustomFieldCategoryVO> containerForm, AjaxRequestTarget target) {
		// NB: creating/updating the customFieldDisplay will be tied to the customField by the service
		if (getModelObject().getCustomFieldCategory().getId() == null) {
			// Save the Category
			try {
				iArkCommonService.createCustomFieldCategory(getModelObject());
				this.info(new StringResourceModel("info.createSuccessMsg", this, null, new Object[] { getModelObject().getCustomFieldCategory().getName() }).getString());
				onSavePostProcess(target);
			}
			catch (ArkRunTimeException e) {
				this.error(new StringResourceModel("error.nonCFMsg", this, null, new Object[] { getModelObject().getCustomFieldCategory().getOrderNumber()}).getString());
				e.printStackTrace();
			}
			catch (ArkRunTimeUniqueException e) {
				this.error(new StringResourceModel("error.nonUniqueCFMsg", this, null, new Object[] { getModelObject().getCustomFieldCategory().getName() }).getString());
				e.printStackTrace();
			}
			catch (ArkSystemException e) {
				this.error(new StringResourceModel("error.internalErrorMsg", this, null).getString());
				e.printStackTrace();
			}
			
			
			processErrors(target);
		}
		else {
			// Update the Category
			try {
				iArkCommonService.updateCustomFieldCategory(getModelObject());
				this.info(new StringResourceModel("info.updateSuccessMsg", this, null, new Object[] { getModelObject().getCustomFieldCategory().getName() }).getString());
				onSavePostProcess(target);
			}
			catch (ArkSystemException e) {
				this.error(new StringResourceModel("error.internalErrorMsg", this, null).getString());
				e.printStackTrace();
			}
			catch (ArkUniqueException e) {
				this.error(new StringResourceModel("error.nonUniqueCFMsg", this, null, new Object[] { getModelObject().getCustomFieldCategory().getName() }).getString());
				e.printStackTrace();
			}
			processErrors(target);
		}
	}

	protected void onCancel(AjaxRequestTarget target) {

	}

	@Override
	protected void processErrors(AjaxRequestTarget target) {
		target.add(feedBackPanel);
	}

	protected void onDeleteConfirmed(AjaxRequestTarget target, String selection) {
		try {
			iArkCommonService.deleteCustomFieldCategory(getModelObject());
			this.info("Field " + getModelObject().getCustomFieldCategory().getName() + " was deleted successfully");
		}
		catch (ArkSystemException e) {
			this.error(e.getMessage());
		}
		catch (EntityCannotBeRemoved e) {
			this.error(e.getMessage());
		}

		// Display delete confirmation message
		target.add(feedBackPanel);
		// TODO Implement Exceptions in PhentoypicService
		// } catch (UnAuthorizedOperation e) { this.error("You are not authorised to manage study components for the given study " +
		// study.getName()); processFeedback(target); } catch (ArkSystemException e) {
		// this.error("A System error occured, we will have someone contact you."); processFeedback(target); }

		// Move focus back to Search form
		editCancelProcess(target); // this ends up calling onCancel(target)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#isNew()
	 */
	@Override
	protected boolean isNew() {
		if (getModelObject().getCustomFieldCategory().getId() == null) {
			return true;
		}
		else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.theark.core.web.form.AbstractDetailForm#addDetailFormComponents()
	 */
	@Override
	protected void addDetailFormComponents() {
		customFieldCategoryDetailWMC = new WebMarkupContainer("customFieldCategoryDetailWMC");
		customFieldCategoryDetailWMC.setOutputMarkupPlaceholderTag(true);
		customFieldCategoryDetailWMC.add(categoryIdTxtFld.setEnabled(false)); // Disable ID field editing
		customFieldCategoryDetailWMC.add(categoryNameTxtFld);
		customFieldCategoryDetailWMC.add(categoryDescriptionTxtAreaFld);
		customFieldCategoryDetailWMC.add(customFieldTypeDdc);
		parentPanel.add(parentCategoryDdc);
		customFieldCategoryDetailWMC.add(parentPanel);
		customFieldCategoryDetailWMC.add(categoryOrderNoTxtFld);

		// TODO: This 'addOrReplace' (instead of just 'add') is a temporary workaround due to the
		// detailPanelFormContainer being initialised only once at the top-level container panel.
		arkCrudContainerVO.getDetailPanelFormContainer().addOrReplace(customFieldCategoryDetailWMC);
		//arkCrudContainerVO.getDetailPanelFormContainer().addOrReplace(customFieldDisplayDetailWMC);
		add(arkCrudContainerVO.getDetailPanelFormContainer());

	}

}
