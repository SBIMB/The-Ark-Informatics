package za.ac.theark.shipment.web.component.shipmentdetails.form;

import org.apache.wicket.model.CompoundPropertyModel;

import au.org.theark.core.web.form.AbstractContainerForm;
import za.ac.theark.shipment.model.vo.ShipmentVO;

public class ContainerForm extends AbstractContainerForm<ShipmentVO> {

	private static final long serialVersionUID = 1L;

	public ContainerForm(String id, CompoundPropertyModel<ShipmentVO> cpmModel) {
		super(id, cpmModel);
	}
}
