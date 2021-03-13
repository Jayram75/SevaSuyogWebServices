package in.sevasuyog.model.response;

import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.ResponseMessage;

public class LoginResponse {
	private ResponseMessage message;
	private User user;
	
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
