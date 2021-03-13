package in.sevasuyog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import in.sevasuyog.util.AttributeUtil;
import in.sevasuyog.util.SevaRoleUtil;

@SpringBootApplication
public class Application {
	@Autowired
	private AttributeUtil attributeUtil;
	
	@Autowired
	private SevaRoleUtil sevaRoleUtil; 
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void init() {
		attributeUtil.init();
		sevaRoleUtil.init();
	}
}
