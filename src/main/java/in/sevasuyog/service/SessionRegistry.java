package in.sevasuyog.service;

import java.sql.Timestamp;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.database.UserDB;
import in.sevasuyog.model.MySession;
import in.sevasuyog.util.Strings;

@Service
public class SessionRegistry {
	public static Integer maxInactiveInterval = null;
	
	@Autowired
	private CommonDB commonDB;
	
	@Autowired
	private UserDB userDB;
	
	public void registerNewSession(HttpSession httpSession, String deviceInfo) {
		MySession session = getSession(httpSession.getId());
		if(session != null) {
			commonDB.delete(session);
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

	public Collection<MySession> getAllSessions(Long userId) {
		return commonDB.fetchAll("userId", userId, MySession.class);
	}

	public void expireNow(MySession session) {
		session.setIsExpired(true);
		commonDB.update(session);
	}

	public void deleteAllExpiredSessions(int maxInactiveIntervalInSeconds) {
		long time = System.currentTimeMillis() - maxInactiveIntervalInSeconds*1000;
		userDB.deleteAllExpiredSessions(new Timestamp(time));
	}

	public void expireNow(HttpSession session) {
		expireNow(getSession(session.getId()));
	}

	public void update(MySession mySession) {
		mySession.setUpdateTS(new Timestamp(System.currentTimeMillis()));
		commonDB.update(mySession);
	}
}
