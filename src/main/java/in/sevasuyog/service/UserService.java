package in.sevasuyog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.User;
import in.sevasuyog.util.Strings;

@Service
public class UserService {
	@Autowired
	private CommonDB commonDB;
	
	public User loadUserByUsername(String username) {
		return commonDB.get(User.class, Strings.USERNAME, username);
	}

	public User loadUserById(Long userId) {
		return commonDB.get(userId, User.class);
	}
}
