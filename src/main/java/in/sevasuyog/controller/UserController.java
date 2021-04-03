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
import in.sevasuyog.util.GetOperation;
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
		return getEntities(FieldType.class);
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
	
	private <T> EntitiesResponse<T> getEntities(Class<T> clazz) {
		return new MyGetOperation<T>().getEntities(clazz);
	}
	
	class MyGetOperation<T> extends GetOperation<T> {
		public MyGetOperation() {
			commonUtil.isOperationAllowed(session);
		}
		@Override
		protected List<T> fetch(Class<T> clazz) {
			return commonService.getObjectList(clazz);
		}
	}
}
