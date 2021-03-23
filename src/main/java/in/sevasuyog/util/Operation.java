package in.sevasuyog.util;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;

public abstract class Operation {
	private static final Logger LOGGER = LogManager.getLogger(Operation.class);
	
	public String execute(CommonUtil commonUtil, HttpSession session) {
		return execute(commonUtil, session, Role.ADMIN);
	}
	
	public String execute(CommonUtil commonUtil, HttpSession session, Role role) {
		return execute(commonUtil, session, Collections.singletonList(role));
	}
	
	public String execute(CommonUtil commonUtil, HttpSession session, List<Role> roles) {
		try {
			for(Role role: roles) {
				if(!commonUtil.isOperationAllowed(session, role)) {
					return ResponseMessage.OPERATION_NOT_ALLOWED.name();
				}
			}
			operate();
			return ResponseMessage.SUCCESSFUL.name();
		} catch(IllegalArgumentException e) {
			return e.getMessage();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}

	protected abstract void operate();
}
