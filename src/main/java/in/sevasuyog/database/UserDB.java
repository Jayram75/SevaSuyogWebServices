package in.sevasuyog.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sevasuyog.model.User;

@Service
public class UserDB {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	public User getUser(String username) {
		String query="select u from User u where u.username=:username ";

		Session session=sessionFactory.getCurrentSession();
		Query<User> q = session.createQuery(query, User.class);
		q.setParameter("username", username, StringType.INSTANCE);
		return q.getSingleResult();
	}
}
