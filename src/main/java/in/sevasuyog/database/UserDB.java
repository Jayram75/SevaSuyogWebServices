package in.sevasuyog.database;

import java.sql.Timestamp;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDB {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = false)
	public void deleteAllExpiredSessions(Timestamp timestamp) {
		String hql = "delete from MySession where updateTS < :updateTS";
		Query<?> query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("updateTS", timestamp);
		query.executeUpdate();
	}
}
