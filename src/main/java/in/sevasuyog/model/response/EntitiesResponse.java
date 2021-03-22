package in.sevasuyog.model.response;

import java.util.List;

import in.sevasuyog.model.enums.ResponseMessage;

public class EntitiesResponse<T> {
	private ResponseMessage message;
	private List<T> entities;
	
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public List<T> getEntities() {
		return entities;
	}
	public void setEntities(List<T> entities) {
		this.entities = entities;
	}
}
