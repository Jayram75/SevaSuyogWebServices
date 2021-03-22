package in.sevasuyog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;

@Service
public class CommonService {
	
	@Autowired
	private CommonDB commonDB;

	public <T> void addOrUpdate(T obj) {
		commonDB.addOrUpdate(obj);
	}

	public <T> void delete(String guid, Class<T> clazz) {
		commonDB.deleteUsingGUID(guid, clazz);
	}

	public <T> List<T> getObjectList(Class<T> clazz) {
		return commonDB.fetchAll(clazz);
	}
}
