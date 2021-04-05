package in.sevasuyog.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.FieldType;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.AttributeName;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.request.SuggestionRequest;
import in.sevasuyog.model.response.EntitiesResponse;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.CommonService;
import in.sevasuyog.service.SessionRegistry;
import in.sevasuyog.service.UserService;
import in.sevasuyog.util.CommonUtil;
import io.swagger.annotations.Api;

@Logging
@RestController
@RequestMapping(value = "/user")
@Api
public class UserController {
	@Autowired
    private HttpSession session;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private UserService userService;

	@Autowired
	public CommonUtil commonUtil;
	
	@Autowired
	public SessionRegistry SessionRegistry;
	
	@Autowired
	private CommonService commonService;
	
	@DeleteMapping("/disable") 
	public String disableUser() {
		User user = userService.getLoggedInUser(session);
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.values()));
		attributeService.setValue(user, AttributeName.ACTIVE, "false");
		SessionRegistry.expireNow(session);
		return ResponseMessage.SUCCESSFUL.name();	
	}
	
	@GetMapping("/fieldType") 
	public EntitiesResponse<FieldType> getFieldTypes() {
		return getEntities(FieldType.class, Arrays.asList(Role.EMPLOYEE, Role.EMPLOYER, Role.ADMIN));
	}
	
	@GetMapping("/role") 
	public EntitiesResponse<Role> getRoles() {
		User user = userService.getLoggedInUser(session);
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.values()));
		return new EntitiesResponse<Role>(userService.getRoles(user));
	}
	
	@PostMapping("/suggestion") 
	public String addSuggestion(SuggestionRequest suggestionRequest) {
		User user = userService.getLoggedInUser(session);
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.EMPLOYEE, Role.EMPLOYER));
		commonUtil.validate(suggestionRequest);
		userService.addSuggestion(user, suggestionRequest);
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	/* OTHERS */
	
	private <T> EntitiesResponse<T> getEntities(Class<T> clazz, List<Role> roles) {
		commonUtil.isOperationAllowed(session, roles);
		return new EntitiesResponse<T>(commonService.getObjectList(clazz));
	}
}
