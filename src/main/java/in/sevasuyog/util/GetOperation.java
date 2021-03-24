package in.sevasuyog.util;

import java.util.List;

import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.response.EntitiesResponse;

public abstract class GetOperation<T> {	
	public EntitiesResponse<T> getEntities(Class<T> clazz) {
		EntitiesResponse<T> entitiesResponse = new EntitiesResponse<T>();
		
		entitiesResponse.setEntities(fetch(clazz));
		entitiesResponse.setMessage(ResponseMessage.SUCCESSFUL);
		return entitiesResponse;
	}

	protected abstract List<T> fetch(Class<T> clazz);
}
