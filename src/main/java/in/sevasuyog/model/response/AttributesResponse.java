package in.sevasuyog.model.response;

import java.util.List;

import in.sevasuyog.model.Attribute;
import in.sevasuyog.model.enums.ResponseMessage;

public class AttributesResponse {
	private ResponseMessage message;
	private List<Attribute> attributes;
	
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
