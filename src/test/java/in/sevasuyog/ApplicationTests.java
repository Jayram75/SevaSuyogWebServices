package in.sevasuyog;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.sevasuyog.model.Greeting;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.MyPasswordEncoder;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private MyPasswordEncoder encoder;
	
	@Autowired
	private CommonUtil commonUtil;
	
	private static final Logger LOGGER = LogManager.getLogger(ApplicationTests.class);
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Test
	public void passwordEncoder() {
		String rawPassword = "1234";
		String encryptedPassword = encoder.encode(rawPassword);
		LOGGER.info(encryptedPassword);
		LOGGER.info(encoder.matches(rawPassword, encryptedPassword));
	}

	@Test
	public void greeting() {
		for(int i = 0; i < 3; i++) {
			Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, "Jayram"));
			String json = commonUtil.toJSON(greeting);
			LOGGER.info(json);
		}
	}
}
