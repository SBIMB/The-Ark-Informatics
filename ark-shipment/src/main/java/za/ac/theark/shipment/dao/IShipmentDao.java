/**
 * 
 */
package za.ac.theark.shipment.dao;

import java.util.Collection;
import java.util.List;

import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.shipment.entity.StaffPhone;
import au.org.theark.core.model.study.entity.Study;
import za.ac.theark.shipment.model.vo.ShipmentVO;

/**
 * @author Freedom Mukomana
 *
 */
public interface IShipmentDao {
	
	//Shipment related methods
	
	public void createShipment(ShipmentVO shipmentVO);
	
	public void updateShipment(ShipmentVO shipmentVO);
	
	public void deleteShipment(ShipmentVO shipmentVO);
	
	public void deleteShipment(Long Id);
	
	public int getStudyShipmentCount(Study study);
	
	
	//Shipment list related methods
	
	public List<Organisation> getOrganisationsByTypeList(OrganisationType organisationType);
	
	public List<Organisation> getOrganisationsByTypeList(String string); 
	
	public List<ShipmentMethod> getShipmentMethodList();
	
	public List<ShipmentType> getShipmentTypeList();
	
	public List<ShipmentStatus> getShipmentStatusList();
	
	
	//Staff related methods

	public List<Staff> searchStaffList(Organisation organisation);
	
	public List<Staff> searchStaffList(Long organisationID);
	
	public List<Staff> getStaffList();
	
	public Staff getStaffById(Long Id);
	
	public Staff getStaffByStaffID(String staffID);

	//public Staff getStaff(Staff staff);
	
	//public Staff getStaff(Long staffID);

	public StaffPhone getPreferredStaffContactNumber(Staff staff);
	
	public StaffPhone getPreferredStaffContactNumber(String staffID);

}
