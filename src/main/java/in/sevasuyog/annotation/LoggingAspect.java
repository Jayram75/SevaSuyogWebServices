package in.sevasuyog.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.sevasuyog.util.LogUtil;

@Aspect
@Component
public class LoggingAspect {
	@Autowired
	private LogUtil logUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Pointcut("execution(@in.sevasuyog.annotation.Logging * *.*(..))")
    void annotatedMethod() {}

    @Pointcut("execution(* (@in.sevasuyog.annotation.Logging *).*(..))")
    void methodOfAnnotatedClass() {}
	
	@Before("annotatedMethod() && @annotation(logger)")
	public void handleAnnotatedMethod(JoinPoint joinPoint, Logging logger) throws Throwable {
		logArgsOfAnnotatedMethod(joinPoint, logger);
	}

	@Before("methodOfAnnotatedClass() && !annotatedMethod() && @within(logger)")
    public void handleMethodsOfAnnotatedClass(JoinPoint joinPoint, Logging logger) throws Throwable {
		logArgsOfAnnotatedMethod(joinPoint, logger);
    }
	
	private void logArgsOfAnnotatedMethod(JoinPoint joinPoint, Logging logger) throws Throwable {
		if(!logger.value()) return;
		Object obj = joinPoint.getThis();
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
		
		List<Object> args = new ArrayList<Object>();
		for(int i = 0; i < method.getParameters().length; i++) {
			Parameter parameter =  method.getParameters()[i];
			if(parameter.isAnnotationPresent(CookieValue.class)) continue;
			if(joinPoint.getArgs()[i] instanceof HttpServletRequest) continue;
			if(joinPoint.getArgs()[i] instanceof HttpServletResponse) continue;
			args.add(joinPoint.getArgs()[i]);
		}
		
		String methodName = obj.getClass().getName().split("\\$")[0] + "." + method.getName();
		String message = objectMapper.writeValueAsString(args);
		logUtil.debug(message, methodName);
	}
}
