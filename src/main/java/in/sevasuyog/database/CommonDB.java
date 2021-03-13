package in.sevasuyog.database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sevasuyog.util.CommonUtil;

@Service
public class CommonDB {

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
	public <T> Object get(Serializable id, Class<T> type) {
		Object obj = sessionFactory.getCurrentSession().get(type, id);
		return obj;
	}
	
	@Transactional(readOnly = true)
	public <T> Long getIdFromGuid(String guid, Class<T> type) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery("select o.id from " + type.getSimpleName() + " o "
				+ "where o.guid = :guid", type);
		q.setParameter("guid", guid, StringType.INSTANCE);
		return (Long)q.uniqueResult();
	}

	@Transactional(readOnly = true)
	public void refresh(Object object) {
		sessionFactory.getCurrentSession().refresh(object);
	}

	@Transactional(readOnly = false)
	public void delete(Object object) {
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
	public <T> Object getFromGUID(String guid, Class<T> type) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery("select o from " + type.getSimpleName() + " o "
				+ "where o.guid = :guid", type);
		q.setParameter("guid", guid, StringType.INSTANCE);
		return q.uniqueResult();
	}

	public <T> T getSingleElement(Query<T> q) {
		return commonUtil.getSingleElement(q.list());
	}

	public <T> T getSingleResult(Query<T> q) {
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

