package in.sevasuyog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommonUtil {
	@Autowired
	private ObjectMapper objectMapper;

	public <T> T fromJSON(String jsonPacket) {
		try {
			return (T) objectMapper.readValue(jsonPacket, new TypeReference<T>() {});
		} catch (Exception e) {}
		
		return null;
	}
	
	public String toJSON(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {}
		
		return null;
	}
}
