package in.sevasuyog.database;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDB {
	private static Logger LOGGER = LogManager.getLogger(UserDB.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = false)
	public void deleteAllExpiredSessions(Timestamp timestamp) {
		String hql = "delete from MySession where updateTS < :updateTS";
		Query<?> query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("updateTS", timestamp);
		int deletedSessionsCount = query.executeUpdate();
		LOGGER.debug(deletedSessionsCount + " sessions deleted!");
	}
}
