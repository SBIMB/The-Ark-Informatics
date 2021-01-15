/**
 * 
 */
package za.ac.theark.shipment.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.theark.core.dao.IStudyDao;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.shipment.entity.StaffPhone;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.service.IArkCommonService;
import au.org.theark.core.vo.SubjectVO;
import za.ac.theark.shipment.dao.IShipmentDao;
import za.ac.theark.shipment.dao.ShipmentOrganisationType;
import za.ac.theark.shipment.model.vo.ShipmentVO;

/**
 * @author Freedom Mukomana
 *
 */
@Transactional
@Service(za.ac.theark.shipment.util.Constants.SHIPMENT_SERVICE)
public class ShipmentServiceImpl implements IShipmentService{
	private static Logger		log	= LoggerFactory.getLogger(ShipmentServiceImpl.class);

	@SuppressWarnings("unchecked")
	private IArkCommonService	arkCommonService;
	private IStudyDao			iStudyDao;
	private IShipmentDao		iShipmentDao;
	
	/**
	 * @param arkCommonService
	 *           the arkCommonService to set
	 */
	@SuppressWarnings("unchecked")
	@Autowired
	public void setArkCommonService(IArkCommonService arkCommonService) {
		this.arkCommonService = arkCommonService;
	}

	/**
	 * @param iStudyDao
	 *           the iStudyDao to set
	 */
	@Autowired
	public void setiStudyDao(IStudyDao iStudyDao) {
		this.iStudyDao = iStudyDao;
	}
	
	
	
	/**
	 * Gets the list of child studies for the specifed parent Study
	 * 
	 * @param study
	 *           the parent study
	 * @return
	 */
	public List<Study> getChildStudyListOfParent(Study study) {
		return iStudyDao.getChildStudyListOfParent(study);
	}
	
	public long getStudyShipmentCount(ShipmentVO shipmentVoCriteria) {
		return arkCommonService.getStudyShipmentCount(shipmentVoCriteria);
	}

	public List<Organisation> getOrganisationsByTypeList(OrganisationType organisationType) {
		return iShipmentDao.getOrganisationsByTypeList(organisationType);
	}
	
	public List<Organisation> getOrganisationsByTypeList(String string) {
		return iShipmentDao.getOrganisationsByTypeList(string);
	}

	public List<ShipmentMethod> getShipmentMethodList() {
		return iShipmentDao.getShipmentMethodList();
	}

	public List<ShipmentType> getShipmentTypeList() {
		return iShipmentDao.getShipmentTypeList();
	}

	public List<ShipmentStatus> getShipmentStatusList() {
		return iShipmentDao.getShipmentStatusList();
	}

	public List<Staff> searchStaffList(Organisation organisation) {
		return iShipmentDao.searchStaffList(organisation);
	}

	public List<Staff> searchStaffList(Long organisationID) {
		return iShipmentDao.searchStaffList(organisationID);
	}

	public Staff getStaffById(Long Id) {
		return iShipmentDao.getStaffById(Id);
	}

	public Staff getStaffByStaffId(String staffID) {
		return iShipmentDao.getStaffByStaffID(staffID);
	}
	
	public StaffPhone getPreferredStaffContactNumber(Staff staff) {
		return iShipmentDao.getPreferredStaffContactNumber(staff);

	}
	
	public StaffPhone getPreferredStaffContactNumber(String staffID) {
		return iShipmentDao.getPreferredStaffContactNumber(staffID);
	}

}
