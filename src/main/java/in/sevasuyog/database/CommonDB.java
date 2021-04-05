package in.sevasuyog.database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.util.CommonUtil;

@Service
public class CommonDB {
	private static Logger LOGGER = LogManager.getLogger(CommonDB.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;

	@Transactional(readOnly = false)
	public void saveOrUpdate(Object object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Transactional(readOnly = false)
	public void save(Object object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Transactional(readOnly = false)
	public void update(Object object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Transactional(readOnly = false)
	public Object merge(Object object) {
		Object obj = sessionFactory.getCurrentSession().merge(object);
		return obj;
	}

	@Transactional(readOnly = true)
	public <T> T get(Serializable id, Class<T> type) {
		T obj = sessionFactory.getCurrentSession().get(type, id);
		return obj;
	}
	
	@Transactional(readOnly = true)
	public <T> Long getIdFromGuid(String guid, Class<T> type) {
		Query<Long> q = sessionFactory.getCurrentSession().createQuery("select o.id from " 
			+ type.getSimpleName() + " o " + "where o.guid = :guid", Long.class);
		q.setParameter("guid", guid, StringType.INSTANCE);
		return (Long)q.uniqueResult();
	}

	@Transactional(readOnly = true)
	public void refresh(Object object) {
		sessionFactory.getCurrentSession().refresh(object);
	}

	@Transactional(readOnly = false)
	public void delete(Object object) {
		if(object == null) return;
		LOGGER.debug("An instance of " + object.getClass() + " deleted!");
		sessionFactory.getCurrentSession().delete(object);
	}
	
	@Transactional(readOnly = true)
	public <T> List<T> fetchAll(Class<T> class2) {
		CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(class2);
	    criteria.from(class2);
	    return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	@Transactional(readOnly = true)
	public <T> T getFromGUID(String guid, Class<T> type) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery("select o from " + type.getSimpleName() + " o "
				+ "where o.guid = :guid", type);
		q.setParameter("guid", guid, StringType.INSTANCE);
		return q.uniqueResult();
	}

	@Transactional(readOnly = false)
	public <T> void deleteUsingGUID(String guid, Class<T> class1) {
		T obj = getFromGUID(guid, class1);
		if(obj == null) throw new UnsupportedOperationException(ResponseMessage.ENTITY_NOT_FOUND.name());
		sessionFactory.getCurrentSession().delete(obj);
	}

	@Transactional(readOnly = false)
	public void addOrUpdate(Object obj) {
		Long id = getIdFromGuid(commonUtil.getGuid(obj), obj.getClass());
		commonUtil.setId(obj, id);
		saveOrUpdate(obj);
	}

	@Transactional(readOnly = true)
	public <T> List<T> fetchAll(String parameterName, Object value, Class<T> type) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery("select o from " + type.getSimpleName() + " o "
				+ "where o." + parameterName + " = :parameter", type);
		q.setParameter("parameter", value);
		return q.getResultList();
	}
	
	@Transactional(readOnly = true)
	public <T> T get(String parameterName, Object value, Class<T> type) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery("select o from " + type.getSimpleName() + " o "
				+ "where o." + parameterName + " = :parameter", type);
		q.setParameter("parameter", value);
		return q.uniqueResult();
	}
}

