/**
 * 
 */
package au.org.theark.core.dao;

import java.util.List;

import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.study.entity.Study;

/**
 * @author Freedom Mukomana
 *
 */
public interface IShipmentDao {

	public void createShipment();
	
	public void updateShipment();
	
	public void deleteShipment();
	
	public int getStudyShipmentCount(Study study);
	
	public List<Organisation> getOrganisationsByType(OrganisationType OrganisationType);
	
	public List<Organisation> getOrganisationsByType(String OrganisationType);
	
	public List<ShipmentMethod> getShipmentMethod();
	
	public List<ShipmentType> getShipmentType();
	
	public List<ShipmentStatus> getShipmentStatus(); 
	
	public List<Staff> getShipmentContactPersonList();
	
	public List<Staff> getShipmentContactPersonByOrganisationList(Organisation organisation);
	
}
