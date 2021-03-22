package in.sevasuyog.model.response;

import java.util.List;

import in.sevasuyog.model.Bhasha;
import in.sevasuyog.model.enums.ResponseMessage;

public class BhashaResponse {
	private ResponseMessage message;
	private List<Bhasha> bhashayen;
	
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public List<Bhasha> getBhashayen() {
		return bhashayen;
	}
	public void setBhashayen(List<Bhasha> bhashayen) {
		this.bhashayen = bhashayen;
	}
}
