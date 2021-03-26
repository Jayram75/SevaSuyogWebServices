package in.sevasuyog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.util.Strings;

@Service
public class UserService {
	@Autowired
	private CommonDB commonDB;
	
	public User loadUserByUsername(String username) {
		return commonDB.get(User.class, Strings.USERNAME, username);
	}

	private User loadUserById(Long userId) {
		return commonDB.get(userId, User.class);
	}
	
	public User getLoggedInUser(HttpSession session) {
		Long userId = (Long) session.getAttribute(Strings.USER_ID);
		if(userId == null) throw new UnsupportedOperationException(ResponseMessage.INVALID_SESSION.name());
		return loadUserById(userId);
	}
}
