package in.sevasuyog.controller;

import java.util.Arrays;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.AttributeName;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.request.UserRequest;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.UserService;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.MyPasswordEncoder;
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
	private MyPasswordEncoder encoder;
	
	/* ---- User ---- */
	
	@DeleteMapping("/disable") 
	public String disableUser(
			@Valid @RequestParam @NotBlank @Size(max = 50, min = 1) 
			String username) {
		User user = userService.getLoggedInUser(session);
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.values()));
		attributeService.setValue(user, AttributeName.ACTIVE, "false");
		session.invalidate();
		return ResponseMessage.SUCCESSFUL.name();	
	}
	
	@DeleteMapping("/enable") 
	public String enableUser(@RequestBody UserRequest userRequest) {
		User user = userService.loadUserByUsername(userRequest.getUsername());
		commonUtil.isOperationAllowed(user, Arrays.asList(Role.values()));
		boolean passwordMatches = encoder.matches(userRequest.getPassword(), user.getPassword());
		if(!passwordMatches) {
			return ResponseMessage.INCORRECT_PASSWORD.name();
		}
		attributeService.setValue(user, AttributeName.ACTIVE, "true");
		return ResponseMessage.SUCCESSFUL.name();
	}
}
