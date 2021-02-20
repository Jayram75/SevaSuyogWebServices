package in.sevasuyog.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Aspect
@Component
public class SecureCookieAspect {
	
	@Pointcut("execution(@in.sevasuyog.annotation.SecureCookie * *.*(..))")
    void annotatedMethod() {}

    @Pointcut("execution(* (@in.sevasuyog.annotation.SecureCookie *).*(..))")
    void methodOfAnnotatedClass() {}
	
	@After("annotatedMethod() && @annotation(cookie)")
	public void handleAnnotatedMethod(JoinPoint joinPoint, SecureCookie cookie) throws Throwable {
		logArgsOfAnnotatedMethod(joinPoint, cookie);
	}

	@After("methodOfAnnotatedClass() && !annotatedMethod() && @within(cookie)")
    public void handleMethodsOfAnnotatedClass(JoinPoint joinPoint, SecureCookie cookie) throws Throwable {
		logArgsOfAnnotatedMethod(joinPoint, cookie);
    }
	
	private void logArgsOfAnnotatedMethod(JoinPoint joinPoint, SecureCookie secureCookie) throws Throwable {
		if(!secureCookie.value()) return;
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		
		if(method.getAnnotations() == null || method.getAnnotations().length == 0) return;
		
		boolean isRequestMappingAnnotationFound = false;
		for(Annotation annotation : method.getAnnotations()) {
			if(annotation.annotationType() == GetMapping.class ||
				annotation.annotationType() == PostMapping.class || 
				annotation.annotationType() == PutMapping.class ||
				annotation.annotationType() == DeleteMapping.class) {
				isRequestMappingAnnotationFound = true;
				break;
			}
		}
		if(!isRequestMappingAnnotationFound) return;
		
		HttpServletResponse httpServletResponse = null;
		HttpServletRequest httpServletRequest = null;
		
		for(int i = 0; i < method.getParameters().length; i++) {
			if(joinPoint.getArgs()[i] instanceof HttpServletRequest) {
				httpServletRequest = ((HttpServletRequest)joinPoint.getArgs()[i]);
			}
			if(joinPoint.getArgs()[i] instanceof HttpServletResponse) {
				httpServletResponse = ((HttpServletResponse)joinPoint.getArgs()[i]);
			}
		}
		
		if(httpServletRequest != null && httpServletResponse != null) {
			for (Cookie cookie : httpServletRequest.getCookies()) {
				cookie.setSecure(true);
				httpServletResponse.addCookie(cookie);
            }
		}
	}
}
