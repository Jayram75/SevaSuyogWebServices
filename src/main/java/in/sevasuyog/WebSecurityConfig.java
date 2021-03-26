package in.sevasuyog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import in.sevasuyog.util.MyPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()               
			.antMatchers("/v2/api-docs").authenticated()
			.and()
			.httpBasic();
        http.csrf().disable();
        http.sessionManagement() 
        	.maximumSessions(-1)
        	.sessionRegistry(sessionRegistry());
    }
    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String encryptedPassword = myPasswordEncoder.encode("seva@341@swag");
        auth.inMemoryAuthentication()
			.withUser("sevasuyogswagger")
			.password(encryptedPassword )
			.authorities("Admin");
    }
	
	@Bean
	public SessionRegistry sessionRegistry() {
	    return new SessionRegistryImpl();
	}
}