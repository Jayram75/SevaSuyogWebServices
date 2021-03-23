package in.sevasuyog.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sevasuyog.model.request.CityRequest;
import in.sevasuyog.util.CommonUtil;

@SuppressWarnings("unused")
@Service
public class AdminDB {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CommonDB commonDB;
}
