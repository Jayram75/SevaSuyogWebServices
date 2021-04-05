package in.sevasuyog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.FieldType;
import in.sevasuyog.model.Suggestion;
import in.sevasuyog.model.User;
import in.sevasuyog.model.UserRole;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.request.SuggestionRequest;
import in.sevasuyog.util.Strings;

@Service
public class UserService {
	@Autowired
	private CommonDB commonDB;
	
	public User loadUserByUsername(String username) {
		return commonDB.get(Strings.USERNAME, username, User.class);
	}

	private User loadUserById(Long userId) {
		return commonDB.get(userId, User.class);
	}
	
	public User getLoggedInUser(HttpSession session) {
		Long userId = (Long) session.getAttribute(Strings.USER_ID);
		if(userId == null) throw new UnsupportedOperationException(ResponseMessage.INVALID_SESSION.name());
		return loadUserById(userId);
	}

	public void addSuggestion(User user, SuggestionRequest suggestionRequest) {
		FieldType fieldType = commonDB.getFromGUID(suggestionRequest.getFieldTypeGuid(), FieldType.class);
		Suggestion suggestion = new Suggestion(
			user.getId(), 
			fieldType.getId(), 
			suggestionRequest.getFieldValue()
		);
		suggestion.setGuid(UUID.randomUUID().toString().substring(0, 8));
		commonDB.save(suggestion);
	}
	
	public void assignRole(String username, Role role) {
		User user = loadUserByUsername(username);
		List<UserRole> userRoles = commonDB.fetchAll(Strings.USER_ID_FIELD, user.getId(), UserRole.class);
		
		for(UserRole ur: userRoles) {
			if(ur.getRole() == role) return;
		}
		
		UserRole userRole = new UserRole(user.getId(), role);
		userRole.setGuid(UUID.randomUUID().toString().substring(0, 8));
		commonDB.save(userRole);
	}

	public void revokeRole(String username, Role role) {
		User user = loadUserByUsername(username);
		List<UserRole> userRoles = commonDB.fetchAll(Strings.USER_ID_FIELD, user.getId(), UserRole.class);
		
		for(UserRole ur: userRoles) {
			if(ur.getRole() == role) {
				commonDB.delete(ur);
				return;
			}
		}
	}

	public List<Role> getRoles(User user) {
		List<Role> roles = new ArrayList<Role>();
		for(UserRole ur: user.getUserRoles()) {
			roles.add(ur.getRole());
		}
		return roles;
	}
}
