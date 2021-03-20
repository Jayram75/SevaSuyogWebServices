package in.sevasuyog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import in.sevasuyog.service.AttributeService;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class }) 
public class Application {
	@Autowired
	private AttributeService attributeService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void init() {
		attributeService.init();
	}
}
