/**
 * 
 */
package za.ac.theark.shipment.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.dao.HibernateSessionDao;
import au.org.theark.core.dao.StudyDao;
import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.shipment.entity.StaffPhone;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.vo.SubjectVO;
import za.ac.theark.shipment.model.vo.ShipmentVO;

/**
 * @author Freedom Mukomana
 *
 */
/**
 * @author freedom
 *
 */
public class ShipmentDao extends HibernateSessionDao implements IShipmentDao {
	private static Logger	log	= LoggerFactory.getLogger(StudyDao.class);
	protected static final String			HEXES					= "0123456789ABCDEF";
		
	public void createShipment(ShipmentVO shipmentVO) {
		
		
	}

	public void updateShipment(ShipmentVO shipmentVO) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteShipment(ShipmentVO shipmentVO) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteShipment(Long Id) {
		
	}
	
	public long getStudySubjectCount(SubjectVO subjectVO) {
		// Handle for study not in context
		// GEORGE - 30/1/15 Removed handle to allow for global search. Need to test to see if this fails anywhere.
//		if (subjectVO.getLinkSubjectStudy().getStudy() == null) {
//			return 0;
//		}

		Criteria criteria = buildGeneralSubjectCriteria(subjectVO);
		criteria.setProjection(Projections.rowCount());
		Long totalCount = (Long) criteria.uniqueResult();
		return totalCount.intValue();
	}

	private Criteria buildGeneralSubjectCriteria(SubjectVO subjectVO) {
		
		return null;
	}

	public int getStudyShipmentCount(Study study) {
		return 0;
	}
	
	/*
	*	Shipment list related methods
	*/
	@SuppressWarnings("unchecked")
	public List<Organisation> getOrganisationsByTypeList(OrganisationType organisationType) {
		Criteria criteria = getSession().createCriteria(Organisation.class);
		criteria.add(Restrictions.eq("organisationType", organisationType));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Organisation> getOrganisationsByTypeList(String organisationType) {
		Criteria criteria = getSession().createCriteria(Organisation.class);
		criteria.add(Restrictions.eq("organisationType", organisationType));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShipmentMethod> getShipmentMethodList() {
		Example example = Example.create(new ShipmentMethod());
		Criteria criteria = getSession().createCriteria(ShipmentMethod.class).add(example);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShipmentType> getShipmentTypeList() {
		Example example = Example.create(new ShipmentType());
		Criteria criteria = getSession().createCriteria(ShipmentType.class).add(example);
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<ShipmentStatus> getShipmentStatusList() {
		Example example = Example.create(new ShipmentStatus());
		Criteria criteria = getSession().createCriteria(ShipmentStatus.class).add(example);
		return criteria.list();
	}
	
	/*
	 *	Staff related methods
	 */
	public List<Staff> searchStaffList(Organisation organisation) {
		return searchStaffList(organisation.getId());
	}

	@SuppressWarnings("unchecked")
	public List<Staff> searchStaffList(Long organisationID) {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		criteria.add(Restrictions.eq("organisationID", organisationID));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Staff getStaffById(Long staffID) {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		criteria.add(Restrictions.eq("staffID", staffID));
		return (Staff) criteria.uniqueResult();
	}

	public Staff getStaffByStaffID(String staffID) {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		criteria.add(Restrictions.eq("staffID", staffID));
		return (Staff) criteria.uniqueResult();
	}
	
	/*public Staff getStaff(Staff staff) {
		return getStaffById(staff.getId());
	}*/

	public StaffPhone getPreferredStaffContactNumber(Staff staff) {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		criteria.add(Restrictions.eq("preferredPhoneNumber", true)); 
		return (StaffPhone) criteria.uniqueResult();
	}

	public StaffPhone getPreferredStaffContactNumber(String staffID) {
		Staff staff = getStaffByStaffID(staffID);
		return getPreferredStaffContactNumber(staff);
	}	
}
