package in.sevasuyog.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.sevasuyog.model.User;
import in.sevasuyog.model.UserRole;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.service.UserService;

@Service
public class CommonUtil {
	private static final Logger LOGGER = LogManager.getLogger(CommonUtil.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;

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

	private boolean isOperationAllowedInternal(User user, Role role) {
		if(user == null) return false;
		
		Set<UserRole> userRoles = user.getUserRoles();
		for(UserRole userRole: userRoles) {
			if(userRole.getRole() == role) {
				return true;
			}
		}
		
		return false;
	}

	public void isOperationAllowed(User user, List<Role> roles) {
		boolean isOperationAllowed = false;
		for(Role role: roles) {
			if(isOperationAllowedInternal(user, role)) {
				isOperationAllowed = true;
				break;
			}
		}
		if(!isOperationAllowed) {
			throw new UnsupportedOperationException(ResponseMessage.OPERATION_NOT_ALLOWED.name());
		}
	}
	
	public void isOperationAllowed(User user, Role role) {
		isOperationAllowed(user, Collections.singletonList(role));
	}

	public void isOperationAllowed(User user) {
		isOperationAllowed(user, Role.ADMIN);
	}
	
	public void isOperationAllowed(HttpSession session, List<Role> roles) {
		isOperationAllowed(userService.getLoggedInUser(session), roles);
	}

	public void isOperationAllowed(HttpSession session, Role role) {
		isOperationAllowed(session, Collections.singletonList(role));
	}

	public void isOperationAllowed(HttpSession session) {
		isOperationAllowed(session, Role.ADMIN);
	}
	
	public <T> void validate(T obj) {
		if(obj != null) {
			Set<ConstraintViolation<T>> a = validator.validate(obj);
			if(!a.isEmpty()) {
				throw new ConstraintViolationException(a);
			}
		}
	}
}
