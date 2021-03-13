package in.sevasuyog.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import in.sevasuyog.service.UserService;
import in.sevasuyog.util.AttributeUtil;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.MyPasswordEncoder;
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
	private AttributeUtil attributeUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@GetMapping("/greetings")
	public List<Greeting> greetings(HttpServletRequest request, HttpServletResponse response,
			@ApiIgnore @CookieValue(value="myCookie",required = false) String myCookie) throws Exception {
		List<Greeting> greetings = commonDB.fetchAll(Greeting.class);
		System.out.println(request.getHeader("User-Agent"));
		
		Cookie c = new Cookie("JSESSIONID", "custom_value_of_cookie");
		c.setHttpOnly(true);
		response.addCookie(c);
		
		request.getHeaderNames().asIterator().forEachRemaining(t -> System.out.println(t + " --> " + request.getHeader(t)));

		request.getSession(true);
		response.getHeaderNames().forEach(t -> System.out.println(t + " --> " + response.getHeaders(t)));
		LOGGER.info(commonUtil.toJSON(response.getHeaders("Set-Cookie")));
		return greetings; 
	}
	
	@PostMapping("/refreshApp") 
	public String refreshApp() {
		attributeUtil.refresh();
		return ResponseMessage.SUCCESSFUL.name();
		
	}
	
	@Logging(value = false)
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
			
			if(!attributeUtil.isTrue(user.getUserAttributes(), AttributeName.VERIFIED)) {
				loginResponse.setMessage(ResponseMessage.NOT_VERIFIED);
				return loginResponse;
			}
			
			if(!attributeUtil.isTrue(user.getUserAttributes(), AttributeName.ACTIVE)) {
				loginResponse.setMessage(ResponseMessage.NOT_ACTIVE);
				return loginResponse;
			}
			// TODO Determine Role
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