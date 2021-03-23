package in.sevasuyog.util;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.response.EntitiesResponse;

public abstract class GetOperation<T> {
	private static final Logger LOGGER = LogManager.getLogger(Operation.class);
	
	private CommonUtil commonUtil;
	private HttpSession session;
	
	public GetOperation(CommonUtil commonUtil, HttpSession session) {
		this.commonUtil = commonUtil;
		this.session = session;
	}

	public EntitiesResponse<T> getEntities() {
		return getEntities(Role.ADMIN);
	}
	
	public EntitiesResponse<T> getEntities(Role role) {
		return getEntities(Collections.singletonList(role));
	}
	
	public EntitiesResponse<T> getEntities(List<Role> roles) {
		EntitiesResponse<T> entitiesResponse = new EntitiesResponse<T>();
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				entitiesResponse.setMessage(ResponseMessage.OPERATION_NOT_ALLOWED);
				return entitiesResponse;
			}
			entitiesResponse.setMessage(ResponseMessage.SUCCESSFUL);
			entitiesResponse.setEntities(fetch());
		} catch (Exception e) {
			LOGGER.error(e);
			entitiesResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		return entitiesResponse;
	}

	protected abstract List<T> fetch();
}
