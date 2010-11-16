/**
 * 
 * This is a new file
 *
 *
 */
package au.org.theark.study.web.component.studycomponent.form;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import au.org.theark.core.exception.ArkSystemException;
import au.org.theark.core.exception.UnAuthorizedOperation;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.web.form.AbstractDetailForm;
import au.org.theark.study.model.vo.StudyCompVo;
import au.org.theark.study.service.IStudyService;

/**
 * @author nivedann
 *
 */
public class DetailForm extends AbstractDetailForm<StudyCompVo>{

	@SpringBean( name =  au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService iArkCommonService;
	
	@SpringBean(name ="studyService")
	private IStudyService studyService;
	private Study study;
	
	private TextField<String> componentIdTxtFld;
	private TextField<String> componentNameTxtFld;
	private TextArea<String> componentDescription;
	private TextArea<String> keywordTxtArea;

	/**
	 * @param id
	 * @param resultListContainer
	 * @param detailPanelContainer
	 */
	public DetailForm(	String id,
						FeedbackPanel feedBackPanel,
						WebMarkupContainer resultListContainer,
						WebMarkupContainer detailPanelContainer,
						WebMarkupContainer detailPanelFormContainer,
						WebMarkupContainer searchPanelContainer,
						WebMarkupContainer viewButtonContainer,
						WebMarkupContainer editButtonContainer,
						ContainerForm containerForm				) {
		
	
		super(	id, feedBackPanel,	
				resultListContainer, detailPanelContainer, 
				detailPanelFormContainer, searchPanelContainer,
				viewButtonContainer, editButtonContainer,
				containerForm);
		
	}
	
	public void initialiseDetailForm(){
		componentIdTxtFld = new TextField<String>("studyComponent.studyCompKey");
		componentIdTxtFld.setEnabled(false);
		componentNameTxtFld = new TextField<String>("studyComponent.name");
		componentDescription = new TextArea<String>("studyComponent.description");
		keywordTxtArea = new TextArea<String>("studyComponent.keyword");
		addDetailFormComponents();
		attachValidators();
	}
	
	public void addDetailFormComponents(){

		detailPanelFormContainer.add(componentIdTxtFld);
		detailPanelFormContainer.add(componentIdTxtFld);
		detailPanelFormContainer.add(componentNameTxtFld);
		detailPanelFormContainer.add(componentDescription);
		detailPanelFormContainer.add(keywordTxtArea);
		
	}

	/* (non-Javadoc)
	 * @see au.org.theark.core.web.form.AbstractDetailForm#attachValidators()
	 */
	@Override
	protected void attachValidators() {
		componentNameTxtFld.setRequired(true);
		componentNameTxtFld.add(StringValidator.lengthBetween(3, 100));
		componentDescription.add(StringValidator.lengthBetween(5, 500));
		keywordTxtArea.add(StringValidator.lengthBetween(1,255));
	}

	/* (non-Javadoc)
	 * @see au.org.theark.core.web.form.AbstractDetailForm#onCancel(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onCancel(AjaxRequestTarget target) {

		StudyCompVo studyCompVo = new StudyCompVo();
		containerForm.setModelObject(studyCompVo);
		onCancelPostProcess(target);
		
	}

	/* (non-Javadoc)
	 * @see au.org.theark.core.web.form.AbstractDetailForm#onSave(org.apache.wicket.markup.html.form.Form, org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onSave(Form<StudyCompVo> containerForm,	AjaxRequestTarget target) {
		// 
		target.addComponent(detailPanelContainer);
		
		
		
		try {

			Long studyId = (Long)SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
			study =	iArkCommonService.getStudy(studyId);	
			containerForm.getModelObject().getStudyComponent().setStudy(study);
			
			if(containerForm.getModelObject().getStudyComponent().getStudyCompKey() == null){
				
				studyService.create(containerForm.getModelObject().getStudyComponent());
				this.info("Study Component " + containerForm.getModelObject().getStudyComponent().getName() + " was created successfully" );
				processErrors(target);
			
			}else{
			
				studyService.update(containerForm.getModelObject().getStudyComponent());
				this.info("Study Component " + containerForm.getModelObject().getStudyComponent().getName() + " was updated successfully" );
				processErrors(target);
				
			}
			onSavePostProcess(target);
			
		} catch (UnAuthorizedOperation e) {
			 this.error("You are not authorised to manage study components for the given study " + study.getName());
			 processErrors(target);
		} catch (ArkSystemException e) {
			this.error("A System error occured, we will have someone contact you.");
			processErrors(target);
		}
		
	}

	/* (non-Javadoc)
	 * @see au.org.theark.core.web.form.AbstractDetailForm#processErrors(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void processErrors(AjaxRequestTarget target) {
		target.addComponent(feedBackPanel);
		
	}

	/* (non-Javadoc)
	 * @see au.org.theark.core.web.form.AbstractDetailForm#onDelete(org.apache.wicket.markup.html.form.Form, org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onDelete(Form<StudyCompVo> containerForm,AjaxRequestTarget target) {
		
	}

}
