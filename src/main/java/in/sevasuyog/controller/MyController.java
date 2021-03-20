package in.sevasuyog.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.Greeting;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.AttributeName;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.request.LoginRequest;
import in.sevasuyog.model.response.LoginResponse;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.UserService;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.MyPasswordEncoder;
import in.sevasuyog.util.Strings;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@Logging
@RestController
@RequestMapping(value = "/")
@Api
public class MyController {
	private static final Logger LOGGER = LogManager.getLogger(MyController.class);
	
	@Autowired
	private CommonDB commonDB;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyPasswordEncoder encoder;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@GetMapping("/greetings")
	public List<Greeting> greetings(@ApiIgnore @CookieValue(value="myCookie",required = false) String myCookie) throws Exception {
		List<Greeting> greetings = commonDB.fetchAll(Greeting.class);
		System.out.println(request.getHeader("User-Agent"));
		
		request.getHeaderNames().asIterator().forEachRemaining(t -> System.out.println(t + " --> " + request.getHeader(t)));

		response.getHeaderNames().forEach(t -> System.out.println(t + " --> " + response.getHeaders(t)));
		LOGGER.info(commonUtil.toJSON(response.getHeaders("Set-Cookie")));
		return greetings; 
	}
	
	@GetMapping("/logoutMe")
	public String logout() {
		try {
			session.invalidate();
		} catch (IllegalStateException e) {	//Already logged out!
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	@Logging(value = false) // For not logging the PASSWORD as it is a sensitive information!
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		
		try {
			User user = userService.loadUserByUsername(loginRequest.getUsername());
			boolean passwordMatches = encoder.matches(loginRequest.getPassword(), user.getPassword());
			
			if(!passwordMatches) {
				loginResponse.setMessage(ResponseMessage.INCORRECT_PASSWORD);
				return loginResponse;
			}
			
			if(!attributeService.isTrue(user.getUserAttributes(), AttributeName.VERIFIED)) {
				loginResponse.setMessage(ResponseMessage.NOT_VERIFIED);
				return loginResponse;
			}
			
			if(!attributeService.isTrue(user.getUserAttributes(), AttributeName.ACTIVE)) {
				loginResponse.setMessage(ResponseMessage.NOT_ACTIVE);
				return loginResponse;
			}
			
			session.setAttribute(Strings.USER_ID, user.getId());
			
			loginResponse.setMessage(ResponseMessage.SUCCESSFUL);
			loginResponse.setUser(user);
		} catch (NoResultException e) {
			loginResponse.setMessage(ResponseMessage.INCORRECT_USERNAME);
		} catch (Exception e) {
			LOGGER.error(e);
			loginResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		
		return loginResponse;
	}
}