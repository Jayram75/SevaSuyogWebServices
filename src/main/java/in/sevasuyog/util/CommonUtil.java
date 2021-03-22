package in.sevasuyog.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.sevasuyog.model.User;
import in.sevasuyog.model.UserRole;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.service.UserService;

@Service
public class CommonUtil {
	private static final Logger LOGGER = LogManager.getLogger(CommonUtil.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserService userService;

	public <T> T fromJSON(String jsonPacket) {
		try {
			return (T) objectMapper.readValue(jsonPacket, new TypeReference<T>() {});
		} catch (Exception e) {}
		
		return null;
	}
	
	public String toJSON(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {}
		
		return null;
	}

	public <T> T getSingleElement(List<T> list) {
		try {
			return (T) list.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean isOperationAllowed(User user, Role role) {
		if(user == null) return false;
		
		Set<UserRole> userRoles = user.getUserRoles();
		for(UserRole userRole: userRoles) {
			if(userRole.getRole() == role) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isOperationAllowed(Long userId, Role role) {
		if(userId == null) return false;

		User user = userService.loadUserById(userId);
		return isOperationAllowed(user, role);
	}

	public boolean isOperationAllowed(HttpSession session, Role role) {
		Long userId = (Long) session.getAttribute(Strings.USER_ID);
		return isOperationAllowed(userId, role);
	}
	
	public String getGuid(Object object) {
		return (String) getFieldValue(object, "guid");
	}
	
	public Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setId(Object obj, Long id) {
        try {
        	Field field = obj.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(obj, id);
        } catch (Exception e) {
        	LOGGER.error(e);
        }
	}
}
