package in.sevasuyog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import in.sevasuyog.controller.MyInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	
	@Bean
	public BCryptPasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
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
        String encryptedPassword = myPasswordEncoder().encode("seva@341@swag");
        auth.inMemoryAuthentication()
			.withUser("sevasuyogswagger")
			.password(encryptedPassword)
			.authorities("Admin");
    }
	
	@Bean
	public MyInterceptor myInterceptor() {
	    return new MyInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(myInterceptor());
	}
}