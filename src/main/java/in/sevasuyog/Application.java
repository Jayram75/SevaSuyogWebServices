package in.sevasuyog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import in.sevasuyog.util.AttributeUtil;

@SpringBootApplication
public class Application {
	
	
	@Autowired
	private AttributeUtil attributes;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void init() {
		attributes.init();
	}
}
