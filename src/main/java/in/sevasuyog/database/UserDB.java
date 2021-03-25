package in.sevasuyog.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDB {

	@SuppressWarnings("unused")
	@Autowired
	private SessionFactory sessionFactory;
}
