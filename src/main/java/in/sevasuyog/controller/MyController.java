package in.sevasuyog.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.Greeting;
import io.swagger.annotations.Api;

@Logging
@RestController
@RequestMapping(value = "/")
@Api
public class MyController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private CommonDB commonDB;
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/greetings")
	public List<Greeting> greetings() {
		return commonDB.fetchAll(Greeting.class);
	}
}