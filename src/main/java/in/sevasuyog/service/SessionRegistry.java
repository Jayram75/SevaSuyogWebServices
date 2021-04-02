package in.sevasuyog.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.database.UserDB;
import in.sevasuyog.model.MySession;
import in.sevasuyog.model.User;
import in.sevasuyog.util.Strings;

@Service
public class SessionRegistry {
	@Autowired
	private CommonDB commonDB;
	
	@Autowired
	private UserDB userDB;
	
	public void registerNewSession(HttpSession httpSession, String deviceInfo) {
		MySession session = getSession(httpSession.getId());
		if(session != null) {
			expireNow(session);
		}
		session = new MySession(
			httpSession.getId(), (Long)httpSession.getAttribute(Strings.USER_ID),
			deviceInfo
		);
		commonDB.save(session);
	}

	public MySession getSession(String sessionId) {
		return commonDB.get("sessionId", sessionId, MySession.class);
	}

	public void expireNow(MySession session) {
		commonDB.delete(session);
	}

	public void deleteAllExpiredSessions(long maxInactiveInterval) {
		long currentMillis = System.currentTimeMillis();
		long inactiveIntervalInMillis = maxInactiveInterval*1000;
		long time = currentMillis - inactiveIntervalInMillis;
		userDB.deleteAllExpiredSessions(new Timestamp(time));
	}

	public void expireNow(HttpSession session) {
		expireNow(getSession(session.getId()));
	}

	public void update(MySession mySession) {
		mySession.setUpdateTS(new Timestamp(System.currentTimeMillis()));
		commonDB.update(mySession);
	}

	public void expireNow(User user) {
		expireNow(commonDB.get("userId", user.getId(), MySession.class));
	}
}
