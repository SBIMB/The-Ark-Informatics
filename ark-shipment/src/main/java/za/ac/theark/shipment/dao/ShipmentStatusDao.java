/**
 * 
 */
package za.ac.theark.shipment.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.theark.core.dao.HibernateSessionDao;
import au.org.theark.core.dao.StudyDao;

/**
 * @author Freedom Mukomana
 *
 */
public class ShipmentStatusDao extends HibernateSessionDao implements IShipmentStaffDao {
	private static Logger	log	= LoggerFactory.getLogger(StudyDao.class);
	protected static final String			HEXES					= "0123456789ABCDEF";
}
