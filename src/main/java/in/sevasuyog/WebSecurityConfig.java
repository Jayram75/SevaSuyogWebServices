package in.sevasuyog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
    }
    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String encryptedPassword = myPasswordEncoder.encode("seva@341@swag");
        auth.inMemoryAuthentication()
			.withUser("sevasuyogswagger")
			.password(encryptedPassword )
			.authorities("Admin");
    }
}