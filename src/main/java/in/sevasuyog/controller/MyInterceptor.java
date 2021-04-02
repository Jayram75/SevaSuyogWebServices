package in.sevasuyog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import in.sevasuyog.model.MySession;
import in.sevasuyog.service.SessionRegistry;

public class MyInterceptor implements HandlerInterceptor {
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession httpSession = request.getSession();
		MySession mySession = sessionRegistry.getSession(httpSession.getId());
		if(mySession == null) {
			try {
				httpSession.invalidate();
			} catch (IllegalStateException e) {}	//Already logged out!
		} else {
			sessionRegistry.update(mySession);
		}
		sessionRegistry.deleteAllExpiredSessions(request.getSession().getMaxInactiveInterval());
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