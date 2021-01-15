package za.ac.theark.shipment.web.component.shipmentdetails.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;

import com.itextpdf.tool.xml.html.HTML.Tag;

import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.Shipment;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.shipment.entity.StaffPhone;
import au.org.theark.core.model.spark.entity.Analysis;
import au.org.theark.core.model.spark.entity.Computation;
import au.org.theark.core.model.spark.entity.DataSource;
import au.org.theark.core.model.spark.entity.MicroService;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.model.study.entity.TitleType;
import au.org.theark.core.util.ContextHelper;
import au.org.theark.core.vo.ArkCrudContainerVO;
import au.org.theark.core.web.behavior.ArkDefaultFormFocusBehavior;
import au.org.theark.core.web.component.ArkDatePicker;
import au.org.theark.core.web.form.AbstractDetailForm;
import za.ac.theark.shipment.util.Constants;
import za.ac.theark.shipment.dao.ShipmentOrganisationType;
import za.ac.theark.shipment.model.vo.ShipmentVO;
import za.ac.theark.shipment.service.IShipmentService;
import za.ac.theark.shipment.service.ShipmentServiceImpl;

public class DetailForm extends AbstractDetailForm<ShipmentVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = org.slf4j.LoggerFactory.getLogger(DetailForm.class);

	@SpringBean(name = Constants.SHIPMENT_SERVICE)
	private IShipmentService iShipmentService;

	private ArkCrudContainerVO 						arkCrudContainerVO;
	
	//Shipment details
	private TextField<String> 						shipmentIDTxtFld;
	private TextField<String> 						shipmentUIDTxtFld;
	private TextField<String> 						shipmentNameTxtFld;
	private TextField<String> 						trackingNumberTxtFld;
	private TextField<String> 						shipmentDescriptionTxtFld;
	private TextField<String> 						commentsTxtFld;			

	private DropDownChoice<ShipmentMethod>			shipmentMethodDdc;
	private DropDownChoice<ShipmentStatus>			shipmentStatusDdc;
	private DropDownChoice<ShipmentType>			shipmentTypeDdc;
	
	//Courier
	private DropDownChoice<Organisation> 			shippingCourierDdc;
	private TextField<String>						shippingCourierPhoneTxtFld;
	
	//Shipping dates
	private DateTextField							shippedDateTxtFld;
	private DateTextField							receivedDateTxtFld;
	
	
	//Laboratory Details
	private DropDownChoice<Organisation>			shipmentClientDdc;
	private TextField<String>						shipmentClientPhoneTxtFld;
	
	private DropDownChoice<Staff>					shipmentContactPersonDdc;
	private TextField<String>						shipmentContactPersonPhoneTxtFld;
	
	private List<Shipment> shipmentList;

	public DetailForm(String id, FeedbackPanel feedBackPanel, ArkCrudContainerVO arkCrudContainerVO, ContainerForm containerForm) {
		super(id, feedBackPanel, containerForm, arkCrudContainerVO);
		this.feedBackPanel = feedBackPanel;
		this.arkCrudContainerVO = arkCrudContainerVO;
	}

	public void onBeforeRender() {
		super.onBeforeRender();
	}

	public void initialiseDetailForm() {
		Long studyId = (Long) SecurityUtils.getSubject().getSession().getAttribute(au.org.theark.core.Constants.STUDY_CONTEXT_ID);
		
		Shipment searchCriteria = new Shipment();
		Study study = new Study();
		study.setId(studyId);
		searchCriteria.setStudy(study);
		//this.shipmentList = iShipmentService.searchShipment(searchCriteria);
		
		shipmentIDTxtFld = new TextField<String>(Constants.ID);
		shipmentUIDTxtFld = new TextField<String>(Constants.SHIPMENT_UID);
		shipmentNameTxtFld = new TextField<String>(Constants.NAME);
		trackingNumberTxtFld = new TextField<String>(Constants.TRACKING_NUMBER);
		shipmentDescriptionTxtFld = new TextField<String>(Constants.DESCRIPTION);
		commentsTxtFld = new TextField<String>(Constants.COMMENTS);
		
		initShipmentMethodDdc();
		initShipmentStatusDdc();
		initShipmentTypeDdc();
		
		initShipmentCourierDdc();
		shippingCourierPhoneTxtFld = new TextField<String>(Constants.SHIPMENT_COURIER_PHONE);
		
		shippedDateTxtFld = new DateTextField(Constants.SHIPPED_DATE, new PatternDateConverter(au.org.theark.core.Constants.DD_MM_YYYY,false));
		ArkDatePicker shippedDatePicker = new ArkDatePicker();
		shippedDatePicker.bind(shippedDateTxtFld);
		shippedDateTxtFld.add(shippedDatePicker);
		
		receivedDateTxtFld = new DateTextField(Constants.RECEIVED_DATE, new PatternDateConverter(au.org.theark.core.Constants.DD_MM_YYYY,false));
		ArkDatePicker receivedDatePicker = new ArkDatePicker();
		receivedDatePicker.bind(receivedDateTxtFld);
		receivedDateTxtFld.add(receivedDatePicker);
		
		initShipmentClientDdc();
		shipmentClientPhoneTxtFld = new TextField<String>(Constants.SHIPMENT_CLIENT_PHONE);
		
		initShipmentContactPersonDdc();
		shipmentContactPersonPhoneTxtFld = new TextField<String>(Constants.SHIPMENT_CONTACT_PERSON_PHONE);

		addDetailFormComponents();
		attachValidators();
	}
	
	private void initShipmentCourierDdc(){
		OrganisationType organisationType = new OrganisationType();
		organisationType.setName("Courier");
		Collection<Organisation> courierList = iShipmentService.getOrganisationsByTypeList(organisationType);
		ChoiceRenderer<Organisation> defaultChoiceRenderer = new ChoiceRenderer<Organisation>(Constants.NAME, Constants.ID);
		shippingCourierDdc = new DropDownChoice<Organisation>(Constants.SHIP_ORGANISATION_TYPE, (List) courierList, defaultChoiceRenderer);
		shippingCourierDdc.add(new ArkDefaultFormFocusBehavior());
	}
	
	protected void initShipmentMethodDdc(){
		Collection<ShipmentMethod> shipmentMethodList = iShipmentService.getShipmentMethodList(); 
		ChoiceRenderer<ShipmentMethod> defaultChoiceRenderer = new ChoiceRenderer<ShipmentMethod>(Constants.NAME, Constants.ID);
		shipmentMethodDdc = new DropDownChoice<ShipmentMethod>(Constants.SHIPMENT_METHOD, (List) shipmentMethodList, defaultChoiceRenderer);
		shipmentMethodDdc.add(new ArkDefaultFormFocusBehavior());
	}	
	
	protected void initShipmentTypeDdc() {
		Collection<ShipmentType> shipmentTypeList = iShipmentService.getShipmentTypeList(); 
		ChoiceRenderer<ShipmentType> defaultChoiceRenderer = new ChoiceRenderer<ShipmentType>(Constants.NAME, Constants.ID);
		shipmentTypeDdc = new DropDownChoice<ShipmentType>(Constants.SHIPMENT_TYPE, (List) shipmentTypeList, defaultChoiceRenderer);
		shipmentTypeDdc.add(new ArkDefaultFormFocusBehavior());
	}			
			
	protected void initShipmentStatusDdc() {
		Collection<ShipmentStatus> shipmentStatusList = iShipmentService.getShipmentStatusList(); 
		ChoiceRenderer<ShipmentStatus> defaultChoiceRenderer = new ChoiceRenderer<ShipmentStatus>(Constants.NAME, Constants.ID);
		shipmentStatusDdc = new DropDownChoice<ShipmentStatus>(Constants.SHIPMENT_STATUS, (List) shipmentStatusList, defaultChoiceRenderer);
		shipmentStatusDdc.add(new ArkDefaultFormFocusBehavior());	
	}
	
	protected void initShipmentClientDdc() {
		Collection<Organisation> shipmentClientList = iShipmentService.getOrganisationsByTypeList("Laboratory"); 
		ChoiceRenderer<Organisation> defaultChoiceRenderer = new ChoiceRenderer<Organisation>(Constants.NAME, Constants.ID);
		shipmentClientDdc = new DropDownChoice<Organisation>(Constants.ORGANISATION, (List) shipmentClientList, defaultChoiceRenderer);
		shipmentClientDdc.add(new ArkDefaultFormFocusBehavior());	
		
		this.shipmentClientDdc.add(new AjaxFormComponentUpdatingBehavior(new String("onChange")) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Organisation shipmentClient = getModelObject().getShipmentClient();
				shipmentContactPersonDdc.setChoices(iShipmentService.searchStaffList(shipmentClient));
				target.add(shipmentContactPersonDdc);
			}

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {

			}
		});
	}
	
	protected void initShipmentContactPersonDdc() {
		ChoiceRenderer<Staff> defaultChoiceRenderer = new ChoiceRenderer<Staff>(Constants.NAME, Constants.ID);
		shipmentContactPersonDdc = new DropDownChoice<Staff>(Constants.SHIPMENT_CONTACT_PERSON, iShipmentService.getStaffList(), defaultChoiceRenderer);
		shipmentContactPersonDdc.add(new ArkDefaultFormFocusBehavior());	
		
		shipmentContactPersonDdc.add(new AjaxFormComponentUpdatingBehavior(new String("onChange")) {
			Staff staff = iShipmentService.getStaff(getModelObject().getShipmentContactPerson());

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				StaffPhone staffPhone = iShipmentService.getPreferredStaffContactNumber(staff);
				shipmentContactPersonPhoneTxtFld.getModel().setObject(staffPhone.getPhoneNumber());
				target.add(shipmentContactPersonPhoneTxtFld);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {

			}
		});
	}

	@Override
	protected void attachValidators() {
		shipmentUIDTxtFld.setRequired(true).setLabel(new StringResourceModel("shipment.uid.required", this, null));
		shipmentNameTxtFld.setRequired(true).setLabel(new StringResourceModel("shipment.name.required", this, null));
	}

	@Override
	protected void onCancel(AjaxRequestTarget target) {
		ShipmentVO shipmentVo = new ShipmentVO();
		containerForm.setModelObject(shipmentVo);
	}

	@Override
	protected void onSave(Form<ShipmentVO> containerForm, AjaxRequestTarget target) {
		// iGenomicService.saveOrUpdate(containerForm.getModelObject().getAnalysis());
		try {
			if (containerForm.getModelObject().getId() == null) {
				this.iShipmentService.createShipment(containerForm.getModelObject());
				this.saveInformation();
				//this.info("Computation " + containerForm.getModelObject().getAnalysis().getName() + " was created successfully");
			} else {
				iShipmentService.updateShipment(containerForm.getModelObject());
				this.updateInformation();
				//this.info("Computation " + containerForm.getModelObject().getAnalysis().getName() + " was updated successfully");
			}
			processErrors(target);
			onSavePostProcess(target);
			target.add(arkCrudContainerVO.getEditButtonContainer());
		} catch (Exception e) {
			log.error("Error in saving micro service entity ", e);
			this.error("A system error occurred. Please contact the system administrator.");
			processErrors(target);
		}
	}

	@Override
	protected void onDeleteConfirmed(AjaxRequestTarget target, String selection) {
		 ShipmentVO shipmentVO = containerForm.getModelObject();
		if (Constants.SHIPMENT_STATUS_NEW.equals(shipmentVO.getShipmentStatus())) {
			iShipmentService.deleteShipment(shipmentVO);
			this.deleteInformation();
			//containerForm.info("Analysis was deleted successfully.");
			editCancelProcess(target);
		} else {
			containerForm.error("An unexpected error occurred. Unable to delete analysis submitted to process.");
			processErrors(target);
		}

	}

	@Override
	protected void processErrors(AjaxRequestTarget target) {
		target.add(feedBackPanel);
	}

	@Override
	protected boolean isNew() {
		if (containerForm.getModelObject().getShipmentUID() == null) {
			return true;
		} else {
			return false;
		}
	}

	private ShipmentVO getFormModelObject() {
		return containerForm.getModelObject();
	}

	@Override
	protected void addDetailFormComponents() {
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentIDTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentUIDTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentNameTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentDescriptionTxtFld);
		
		arkCrudContainerVO.getDetailPanelFormContainer().add(shippingCourierDdc);
		arkCrudContainerVO.getDetailPanelFormContainer().add(trackingNumberTxtFld);
		
		arkCrudContainerVO.getDetailPanelFormContainer().add(shippedDateTxtFld);
		arkCrudContainerVO.getDetailPanelFormContainer().add(receivedDateTxtFld);
		
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentMethodDdc);
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentStatusDdc);
		arkCrudContainerVO.getDetailPanelFormContainer().add(shipmentTypeDdc);
		
		arkCrudContainerVO.getDetailPanelFormContainer().add(saveButton);
		arkCrudContainerVO.getDetailPanelFormContainer().add(deleteButton);
		arkCrudContainerVO.getDetailPanelFormContainer().add(saveButton);
	}

}
