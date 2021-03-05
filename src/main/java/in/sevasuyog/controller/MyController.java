package in.sevasuyog.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.Greeting;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.MyPasswordEncoder;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@Logging
@RestController
@RequestMapping(value = "/")
@Api
public class MyController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private CommonDB commonDB;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MyPasswordEncoder encoder;
	
	@GetMapping("/greeting")
	public Greeting greeting(
	        HttpServletRequest request, 
	        HttpServletResponse response,
			@RequestParam(value = "name", defaultValue = "World") String name,
			@ApiIgnore @CookieValue(value="sessionId",required = false) String sessionId,
			@ApiIgnore @CookieValue(value="sessionId2",required = false) String sessionId2,
			@ApiIgnore @CookieValue(value="sessionId3",required = false) String sessionId3,
			@ApiIgnore @CookieValue(value="sessionId4",required = false) String sessionId4) throws Exception {
		System.out.println(sessionId + ", " + sessionId2 + ", " + sessionId3 + ", " + sessionId4);
		response.addCookie(new Cookie("sessionId", "xyz"));
		Cookie c2 = new Cookie("sessionId2", "xy2z");
		c2.setHttpOnly(true);
		response.addCookie(c2);
		Cookie c3 = new Cookie("sessionId3", "xy3z");
		c3.setSecure(true);
		response.addCookie(c3);
		Cookie c4 = new Cookie("sessionId4", "xy4z");
		c4.setSecure(true);
		c4.setHttpOnly(true);
		response.addCookie(c4);
		
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/greetings")
	public List<Greeting> greetings(HttpServletRequest request, HttpServletResponse response,
			@ApiIgnore @CookieValue(value="sessionId",required = false) String sessionId,
			@ApiIgnore @CookieValue(value="sessionId2",required = false) String sessionId2,
			@ApiIgnore @CookieValue(value="sessionId3",required = false) String sessionId3,
			@ApiIgnore @CookieValue(value="sessionId4",required = false) String sessionId4) throws Exception {
		System.out.println(sessionId + ", " + sessionId2 + ", " + sessionId3 + ", " + sessionId4);
		List<Greeting> greetings = commonDB.fetchAll(Greeting.class);
		System.out.println(commonUtil.<List<Greeting>>fromJSON(commonUtil.toJSON(greetings)));
		
		System.out.println(request.getHeader("User-Agent"));
		
		response.addCookie(new Cookie("sessionId", "xyz"));
		Cookie c2 = new Cookie("sessionId2", "xy2z");
		c2.setHttpOnly(true);
		response.addCookie(c2);
		Cookie c3 = new Cookie("sessionId3", "xy3z");
		c3.setSecure(true);
		response.addCookie(c3);
		Cookie c4 = new Cookie("sessionId4", "xy4z");
		c4.setSecure(true);
		c4.setHttpOnly(true);
		response.addCookie(c4);
		
		String ep = encoder.encode("password");
		System.out.println(ep);
		System.out.println(encoder.matches("password", ep));
		System.out.println(encoder.matches("password1", ep));
		
		return greetings; 
	}
}