package in.sevasuyog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.UserDB;
import in.sevasuyog.model.User;

@Service
public class UserService {

	@Autowired
	private UserDB userDB;
	
	public User loadUserByUsername(String username) {
		return userDB.getUser(username);
	}

}
