package in.sevasuyog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.MySession;
import in.sevasuyog.service.ProcessServiceImpl;
import in.sevasuyog.service.SessionRegistry;

public class MyInterceptor implements HandlerInterceptor {
	@Autowired
	private CommonDB commonDB;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private ProcessServiceImpl processServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession httpSession = request.getSession();
		MySession mySession = sessionRegistry.getSession(httpSession.getId());
		if(mySession == null || mySession.getIsExpired()) {
			try {
				httpSession.invalidate();
			} catch (IllegalStateException e) {}	//Already logged out!
			commonDB.delete(mySession);
		} else {
			sessionRegistry.update(mySession);
		}
		SessionRegistry.maxInactiveInterval = request.getSession().getMaxInactiveInterval();
		processServiceImpl.process();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}