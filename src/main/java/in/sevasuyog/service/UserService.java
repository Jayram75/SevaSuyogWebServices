package in.sevasuyog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.database.UserDB;
import in.sevasuyog.model.User;

@Service
public class UserService {

	@Autowired
	private UserDB userDB;
	
	@Autowired
	private CommonDB commonDB;
	
	public User loadUserByUsername(String username) {
		return userDB.getUser(username);
	}

	public User loadUserById(Long userId) {
		return commonDB.get(userId, User.class);
	}

}
