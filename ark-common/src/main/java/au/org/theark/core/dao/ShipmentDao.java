package au.org.theark.core.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import au.org.theark.core.model.shipment.entity.OrganisationType;
import au.org.theark.core.model.shipment.entity.ShipmentMethod;
import au.org.theark.core.model.shipment.entity.Organisation;
import au.org.theark.core.model.shipment.entity.ShipmentStatus;
import au.org.theark.core.model.shipment.entity.ShipmentType;
import au.org.theark.core.model.shipment.entity.Staff;
import au.org.theark.core.model.study.entity.Study;
import au.org.theark.core.vo.SubjectVO;

public class ShipmentDao extends HibernateSessionDao implements IShipmentDao {

	@Override
	public void createShipment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShipment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteShipment() {
		// TODO Auto-generated method stub
		
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

	@Override
	public int getStudyShipmentCount(Study study) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<Organisation> getOrganisationsByType(OrganisationType organisationType) {
		Criteria criteria = getSession().createCriteria(Organisation.class);
		criteria.add(Restrictions.eq("organisationType", organisationType));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Organisation> getOrganisationsByType(String organisationType) {
		Criteria criteria = getSession().createCriteria(Organisation.class);
		criteria.add(Restrictions.eq("organisationType", organisationType));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShipmentMethod> getShipmentMethod() {
		Example example = Example.create(new ShipmentMethod());
		Criteria criteria = getSession().createCriteria(ShipmentMethod.class).add(example);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShipmentType> getShipmentType() {
		Example example = Example.create(new ShipmentType());
		Criteria criteria = getSession().createCriteria(ShipmentType.class).add(example);
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<ShipmentStatus> getShipmentStatus() {
		Example example = Example.create(new ShipmentStatus());
		Criteria criteria = getSession().createCriteria(ShipmentStatus.class).add(example);
		return criteria.list();
	}

	@Override
	public List<Staff> getShipmentContactPersonList() {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		return criteria.list();
	}

	@Override
	public List<Staff> getShipmentContactPersonByOrganisationList(Organisation organisation) {
		Example example = Example.create(new Staff());
		Criteria criteria = getSession().createCriteria(Staff.class).add(example);
		criteria.add(Restrictions.eq("organisation", organisation));
		return criteria.list();
	}
	
	

}
