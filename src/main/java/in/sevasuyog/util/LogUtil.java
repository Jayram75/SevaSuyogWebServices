package in.sevasuyog.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogUtil {
	private static final Logger LOGGER = LogManager.getLogger(LogUtil.class);

	public void info(String message, String methodname, Exception e) {
		LOGGER.info("MethodName: " + methodname + " " + message, e);
	}

	public void info(String message, String methodname) {
		LOGGER.info("MethodName: " + methodname + " " + message);
	}

	public void info(String message) {
		LOGGER.info("MethodName: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " " + message);
	}

	public void info(String message, Exception e) {
		LOGGER.info(message, e);
	}

	public void info(Exception e) {
		LOGGER.info(e);
	}

	public void error(String message, String methodname, Exception e) {

		LOGGER.error("MethodName: " + methodname + " " + message, e);
	}

	public void error(String message, Exception e) {
		LOGGER.error("MethodName: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " " + message, e);
	}

	public void error(Exception e) {
		LOGGER.error("MethodName: " + Thread.currentThread().getStackTrace()[2].getMethodName(), e);
	}

	public void error(String string) {
		LOGGER.error(string);
	}
}
