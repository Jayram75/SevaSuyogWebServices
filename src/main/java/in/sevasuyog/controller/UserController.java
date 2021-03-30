package in.sevasuyog.controller;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.AttributeName;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.service.AttributeService;
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
	
	@DeleteMapping("/disable") 
	public String disableUser() {
		User user = userService.getLoggedInUser(session);
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.values()));
		attributeService.setValue(user, AttributeName.ACTIVE, "false");
		SessionRegistry.expireNow(session);
		return ResponseMessage.SUCCESSFUL.name();	
	}
}
