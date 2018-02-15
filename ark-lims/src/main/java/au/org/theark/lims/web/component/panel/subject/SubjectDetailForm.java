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
package au.org.theark.lims.web.component.panel.subject;

import java.util.Date;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.model.study.entity.EthnicityType;
import au.org.theark.core.model.study.entity.LinkSubjectStudy;
import au.org.theark.core.model.study.entity.Study;

/**
 * @author cellis
 * 
 */
public class SubjectDetailForm extends Form<LinkSubjectStudy> {

	@SpringBean(name = au.org.theark.core.Constants.ARK_COMMON_SERVICE)
	private IArkCommonService<Void>						iArkCommonService;

	private static final long		serialVersionUID	= 1L;
	protected static final Logger	log					= LoggerFactory.getLogger(SubjectDetailForm.class);
	protected TextField<String>	subjectUIDTxtFld;
	protected TextField<String>	firstNameTxtFld;
	protected TextField<String>	middleNameTxtFld;
	protected TextField<String>	lastNameTxtFld;
	protected DateTextField			dateOfBirthTxtFld;
	protected TextField<String> 				subjectStatusTxtFld;	
	protected TextField<String> 				ethnicityTxtFld;
	protected TextField<Integer>			ageAtCollectionTxtFld;
	protected Study					study;

	public SubjectDetailForm(String id, IModel<LinkSubjectStudy> model) {
		super(id, model);
	}

	public void initialiseDetailForm() {
		subjectUIDTxtFld = new TextField<String>("subjectUID", new PropertyModel<String>(getDefaultModel(), "subjectUID"));
		subjectUIDTxtFld.setOutputMarkupId(true);
		firstNameTxtFld = new TextField<String>("person.firstName", new PropertyModel<String>(getDefaultModel(), "person.firstName"));
		lastNameTxtFld = new TextField<String>("person.lastName", new PropertyModel<String>(getDefaultModel(), "person.lastName"));
		dateOfBirthTxtFld = new DateTextField( "person.dateOfBirth", new PropertyModel<Date>(getDefaultModel(), "person.dateOfBirth"), new PatternDateConverter( au.org.theark.core.Constants.DD_MM_YYYY, false));
		subjectStatusTxtFld = new TextField<String>("subjectStatus", new PropertyModel<String>(getModelObject(), "subjectStatus.name"));
		//subjectStatusDdc = new DropDownChoice<SubjectStatus>("subjectStatus", new ListModel<SubjectStatus>(l),getModel().getObject().getSubjectStatus().getName());
		/*List<SubjectStatus> subjectStatusList = iArkCommonService.getSubjectStatus();
		ChoiceRenderer<SubjectStatus> subjectStatusRenderer = new ChoiceRenderer<SubjectStatus>(Constants.NAME, Constants.SUBJECT_STATUS_ID);
		subjectStatusDdc = new DropDownChoice<SubjectStatus>("linkSubjectStudy.subjectStatus", subjectStatusList, subjectStatusRenderer);
		//subjectStatusDdc = new DropDownChoice<SubjectStatus>("subjectStatus", new PropertyModel<String>(getDefaultModel(), "subjectStatus"));
		
		Collection<EthnicityType> ethnicityTypeList = iArkCommonService.getEthnicityTypes();
		ChoiceRenderer<EthnicityType> ethnicityTypeRenderer = new ChoiceRenderer<EthnicityType>(Constants.NAME, Constants.ID);
		ethnicityTypeDdc = new DropDownChoice<EthnicityType>("linkSubjectStudy.person.ethnicityType", (List<EthnicityType>) ethnicityTypeList, ethnicityTypeRenderer);*/
		//ethnicityTxtFld = new TextField<String>("person.ethnicityType", new PropertyModel<String>(getDefaultModel(), "person.ethnicityType"));
		//ageAtCollectionTxtFld = new TextField<Integer>( "person.d", new PropertyModel<Date>(getDefaultModel(), "person.dateOfBirth"), au.org.theark.core.Constants.DD_MM_YYYY);
		ethnicityTxtFld = new TextField<String>("ethnicity", new PropertyModel<String>(getModelObject(), "person.ethnicityType.name"));
		
		addDetailFormComponents();
	}

	public void addDetailFormComponents() {
		add(subjectUIDTxtFld);
		add(subjectStatusTxtFld);
		add(ethnicityTxtFld);
		add(firstNameTxtFld);
		add(lastNameTxtFld);
		add(dateOfBirthTxtFld);
		setEnabled(false);
	}
}