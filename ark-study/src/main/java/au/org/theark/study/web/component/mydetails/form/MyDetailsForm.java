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
package au.org.theark.study.web.component.mydetails.form;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.dao.ArkShibbolethServiceProviderContextSource;
import au.org.theark.core.exception.ArkSystemException;

import au.org.theark.core.model.study.entity.ArkPermission;
import au.org.theark.core.model.study.entity.ArkRolePolicyTemplate;

import au.org.theark.core.exception.EntityNotFoundException;
import au.org.theark.core.model.config.entity.UserConfig;

import au.org.theark.core.model.study.entity.ArkUserRole;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.vo.ArkUserVO;
import au.org.theark.core.web.component.listeditor.AbstractListEditor;
import au.org.theark.core.web.form.ArkFormVisitor;
import au.org.theark.study.service.IUserService;
import au.org.theark.study.web.Constants;

public class MyDetailsForm extends Form<ArkUserVO> {


	private static final long			serialVersionUID			= 2381693804874240001L;
	private transient static Logger	log							= LoggerFactory.getLogger(MyDetailsForm.class);

	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService<Void>	iArkCommonService;
	@SpringBean(name = "userService")
	private IUserService					iUserService;
	protected TextField<String>			userNameTxtField			= new TextField<String>(Constants.USER_NAME);
	protected TextField<String>			firstNameTxtField			= new TextField<String>(Constants.FIRST_NAME);
	protected TextField<String>			lastNameTxtField			= new TextField<String>(Constants.LAST_NAME);
	protected TextField<String>			emailTxtField				= new TextField<String>(Constants.EMAIL);
	protected TextField<String>			phoneNumberTxtField			= new TextField<String>(Constants.PHONE_NUMBER);
	protected PasswordTextField			userPasswordField			= new PasswordTextField(Constants.PASSWORD);
	protected PasswordTextField			confirmPasswordField		= new PasswordTextField(Constants.CONFIRM_PASSWORD);
	protected WebMarkupContainer		groupPasswordContainer		= new WebMarkupContainer("groupPasswordContainer");
	private WebMarkupContainer			listViewPanel				= new WebMarkupContainer("listViewPanel");
	
	
	
	protected WebMarkupContainer		shibbolethSession = new WebMarkupContainer("shibbolethSession");
	protected WebMarkupContainer		shibbolethSessionDetails = new WebMarkupContainer("shibbolethSessionDetails");
   
	private AjaxButton					saveButton;
	private AjaxButton					closeButton;
	private FeedbackPanel				feedbackPanel;
	private ModalWindow					modalWindow;

	private IModel<List<ArkRolePolicyTemplate>> iModel;
	private PageableListView<ArkRolePolicyTemplate>		pageableListView;
	private AjaxPagingNavigator         pageNavigator;

	@SuppressWarnings("unchecked")
	private AbstractListEditor<UserConfig> userConfigListEditor;
	

	// Add a visitor class for required field marking/validation/highlighting
	ArkFormVisitor							formVisitor					= new ArkFormVisitor();
	private DropDownChoice<Study>		studyDdc;
	
	

	public MyDetailsForm(String id, CompoundPropertyModel<ArkUserVO> model, final FeedbackPanel feedbackPanel, ModalWindow modalWindow) {
		super(id, model);
		this.feedbackPanel = feedbackPanel;
		this.modalWindow = modalWindow;
		
	}

	@SuppressWarnings( { "unchecked" })
	public void initialiseForm() {
		//ArkUserVO arkUserVOFromBackend = new ArkUserVO();
		try {

			ArkUserVO arkUserVOFromBackend = iUserService.lookupArkUser(getModelObject().getUserName(), getModelObject().getStudy());
			arkUserVOFromBackend.setStudy(getModelObject().getStudy());
			//Study will selected with model object and that will be the page list view accordingly.
			listViewPanel.setOutputMarkupId(true);
			iModel = new LoadableDetachableModel() {
				private static final long	serialVersionUID	= 1L;
				@Override
				protected Object load() {
					return getModelObject().getArkRolePolicyTemplatesList();
				}
			};
			initStudyDdc(arkUserVOFromBackend);
			
			List<UserConfig> arkUserConfigs = iArkCommonService.getUserConfigs(arkUserVOFromBackend.getArkUserEntity());
			arkUserVOFromBackend.setArkUserConfigs(arkUserConfigs);
			getModelObject().setArkUserConfigs(arkUserConfigs);

		}
		catch (ArkSystemException e) {
			log.error(e.getMessage());
		}
		
		emailTxtField.setOutputMarkupId(true);
		lastNameTxtField.add(new AjaxFormComponentUpdatingBehavior("onChange") {
			private static final long serialVersionUID = -4514605801401294450L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				getModelObject().setEmail(lastNameTxtField.getValue());
				target.add(emailTxtField);
			}
		});

		saveButton = new AjaxButton(Constants.SAVE) {

			private static final long	serialVersionUID	= -8737230044711628981L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onSave(target);
			}

			public void onError(AjaxRequestTarget target, Form<?> form) {
				processFeedback(target, feedbackPanel);
			}
		};

		closeButton = new AjaxButton(Constants.CLOSE) {

			private static final long	serialVersionUID	= 5457464178392550628L;

			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modalWindow.close(target);
			}

			public void onError(AjaxRequestTarget target, Form<?> form) {
				processFeedback(target, feedbackPanel);
			}
		};
		closeButton.setDefaultFormProcessing(false);

		emailTxtField.add(EmailAddressValidator.getInstance());
		
		boolean loggedInViaAAF = ((String)SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.SHIB_SESSION_ID) != null);
		groupPasswordContainer.setVisible(!loggedInViaAAF);

		shibbolethSessionDetails.add(new AttributeModifier("src", ArkShibbolethServiceProviderContextSource.handlerUrl + ArkShibbolethServiceProviderContextSource.session));
		shibbolethSession.add(new AttributeModifier("class", "paddedDetailPanel"));
		
		shibbolethSession.setVisible(loggedInViaAAF);
		shibbolethSession.add(shibbolethSessionDetails);

		attachValidators();
		addComponents();
	}

	

	private void attachValidators() {
		userNameTxtField.add(EmailAddressValidator.getInstance()).setLabel(new StringResourceModel("userName.incorrect.format", this, null));
		userNameTxtField.setRequired(true).setLabel(new StringResourceModel("userName", this, null));
		userNameTxtField.add(StringValidator.lengthBetween(3, 50)).setLabel(new StringResourceModel("userName", this, null));

		firstNameTxtField.setRequired(true).setLabel(new StringResourceModel("firstName", this, null));
		firstNameTxtField.add(StringValidator.lengthBetween(3, 50)).setLabel(new StringResourceModel("firstName", this, null));

		emailTxtField.add(EmailAddressValidator.getInstance()).setLabel(new StringResourceModel("email.incorrect.format", this, null));
		emailTxtField.setRequired(true).setLabel(new StringResourceModel("email", this, null));

		lastNameTxtField.add(StringValidator.lengthBetween(3, 50)).setLabel(new StringResourceModel("lastNameLength", this, null));
		lastNameTxtField.setRequired(true).setLabel(new StringResourceModel("lastName", this, null));

		userPasswordField.setRequired(false);
		confirmPasswordField.setRequired(false);

		userPasswordField.setLabel(Model.of("Password"));
		userPasswordField.add(new PatternValidator(Constants.PASSWORD_PATTERN));
		confirmPasswordField.setLabel(Model.of("Confirm Password"));
	}

	private void addComponents() {
		add(userNameTxtField);
		add(firstNameTxtField);
		add(lastNameTxtField);
		add(emailTxtField);
		groupPasswordContainer.add(userPasswordField);
		groupPasswordContainer.add(confirmPasswordField);
		add(groupPasswordContainer);
		add(shibbolethSession);
		add(new EqualPasswordInputValidator(userPasswordField, confirmPasswordField));
		add(saveButton);
		add(closeButton);
		add(listViewPanel);
		add(studyDdc);
		
	}
	
	protected void processFeedback(AjaxRequestTarget target, FeedbackPanel fb) {

	}

	protected void onSave(AjaxRequestTarget target) {

	}

	protected void onCancel(AjaxRequestTarget target) {

	}

	public TextField<String> getUserNameTxtField() {
		return userNameTxtField;
	}
	/**
	 * 
	 * @param permissionsLst
	 * @param operation
	 * @return
	 */
	private boolean isPermissionLstContain(List<ArkPermission> arkPermissionsLst,String operation){
		for (ArkPermission arkPermission : arkPermissionsLst) {
			if(arkPermission.getName().equals(operation)) {
				return true;
			}
		}
		return false;
	}
	private void initStudyDdc(ArkUserVO arkUserVO) {
		List<Study> studyListForUser =iArkCommonService.getStudyListForUser(arkUserVO);
		ChoiceRenderer<Study> choiceRenderer = new ChoiceRenderer<Study>(Constants.NAME, Constants.ID);
		//Set to selected study.
		studyDdc = new DropDownChoice<Study>("studyLst", new PropertyModel(this, getModelObject().getStudy().getName()),studyListForUser, choiceRenderer);
		studyDdc.add(new AjaxFormComponentUpdatingBehavior("onChange") {
			private static final long serialVersionUID = -4514605801401294450L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				listViewPanel.remove(pageableListView);
				listViewPanel.remove(pageNavigator);
				List<ArkUserRole> arkUserRoleList = iArkCommonService.getArkRoleListByUserAndStudy(arkUserVO, studyDdc.getModelObject());
				arkUserVO.setArkUserRoleList(arkUserRoleList);
				getModelObject().setArkUserRoleList(arkUserRoleList);
				//Add Ark Role Policy Templates
				List<ArkRolePolicyTemplate> arkRolePolicyTemplatesLst=iArkCommonService.getArkRolePolicytemplateList(arkUserVO);
				getModelObject().setArkRolePolicyTemplatesList(arkRolePolicyTemplatesLst);
				log.info("List:"+arkRolePolicyTemplatesLst.size());
				createPageListView(iModel);
				listViewPanel.add(pageableListView);
				listViewPanel.add(pageNavigator);
				target.add(listViewPanel);
			}
		});
		initiPageListViewWithSelectedStudy(arkUserVO);
	}

	/**
	 * initialise page list with study.
	 * @param arkUserVO
	 */
	private void initiPageListViewWithSelectedStudy(ArkUserVO arkUserVO) {
		List<ArkUserRole> arkUserRoleList = iArkCommonService.getArkRoleListByUserAndStudy(arkUserVO, getModelObject().getStudy());
		arkUserVO.setArkUserRoleList(arkUserRoleList);
		getModelObject().setArkUserRoleList(arkUserRoleList);
		//Add Ark Role Policy Templates
		List<ArkRolePolicyTemplate> arkRolePolicyTemplatesLst=iArkCommonService.getArkRolePolicytemplateList(arkUserVO);
		getModelObject().setArkRolePolicyTemplatesList(arkRolePolicyTemplatesLst);
		createPageListView(iModel);
	}
	
	private void createPageListView(IModel<List<ArkRolePolicyTemplate>> iModel) {
		// TODO: Amend hard-coded 50 row limit, pageableListView didn't work within a ModalWindow
		pageableListView = new PageableListView<ArkRolePolicyTemplate>("arkRolePolicyTemplatesList", iModel, 50) {
			private static final long	serialVersionUID	= 3557668722549243826L;

			@Override
			protected void populateItem(final ListItem<ArkRolePolicyTemplate> item) {
				ArkRolePolicyTemplate arkRolePolicyTemplate = (ArkRolePolicyTemplate) item.getModelObject();
				/*if (((ArkUserVO)getModelObject()).getStudy() != null) {
					item.addOrReplace(new Label("studyName", ((ArkUserVO)getModelObject()).getStudy().getName()));
				}
				else {
					item.addOrReplace(new Label("studyName", "[All Study Access]"));
				}*/
				
				if(arkRolePolicyTemplate.getArkModule()!=null){
					item.addOrReplace(new Label("moduleName", arkRolePolicyTemplate.getArkModule().getName()));
				}else{
					item.addOrReplace(new Label("moduleName", "Not specified Module"));
				}
				if(arkRolePolicyTemplate.getArkRole()!=null){
					item.addOrReplace(new Label("roleName", arkRolePolicyTemplate.getArkRole().getName()));
				}else{
					item.addOrReplace(new Label("roleName", "Not specified Role"));
				}
				if(arkRolePolicyTemplate.getArkFunction()!=null){
					item.addOrReplace(new Label("functionName", arkRolePolicyTemplate.getArkFunction().getName()));
				}else{
					item.addOrReplace(new Label("functionName", "Not specified function"));
				}
				List<ArkPermission> arkPermissionsList=iArkCommonService.getArkPremissionListForRoleAndModule(arkRolePolicyTemplate);
				
					
					if (isPermissionLstContain(arkPermissionsList,"CREATE")) {
						item.addOrReplace(new ContextImage("arkCreatePermission", new Model<String>("images/icons/tick.png")));
					}
					else {
						item.addOrReplace(new ContextImage("arkCreatePermission", new Model<String>("images/icons/cross.png")));
					}

					// the ID here must match the ones in mark-up
					if (isPermissionLstContain(arkPermissionsList,"READ")) {
						item.addOrReplace(new ContextImage("arkReadPermission", new Model<String>("images/icons/tick.png")));
					}
					else {
						item.addOrReplace(new ContextImage("arkReadPermission", new Model<String>("images/icons/cross.png")));
					}

					// the ID here must match the ones in mark-up
					if (isPermissionLstContain(arkPermissionsList,"UPDATE")) {
						item.addOrReplace(new ContextImage("arkUpdatePermission", new Model<String>("images/icons/tick.png")));
					}
					else {
						item.addOrReplace(new ContextImage("arkUpdatePermission", new Model<String>("images/icons/cross.png")));
					}

					// the ID here must match the ones in mark-up
					if (isPermissionLstContain(arkPermissionsList,"DELETE")) {
						item.addOrReplace(new ContextImage("arkDeletePermission", new Model<String>("images/icons/tick.png")));
					}
					else {
						item.addOrReplace(new ContextImage("arkDeletePermission", new Model<String>("images/icons/cross.png")));
					}

				item.setEnabled(false);

				item.add(new AttributeModifier("class", new AbstractReadOnlyModel() {
					private static final long	serialVersionUID	= -8887455455175404701L;

					@Override
					public String getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}
		};
		pageableListView.setReuseItems(false);
		pageableListView.setOutputMarkupId(true);
		listViewPanel.add(pageableListView);
		pageNavigator = new AjaxPagingNavigator("navigator", pageableListView);
		listViewPanel.add(pageNavigator);
	}

}
