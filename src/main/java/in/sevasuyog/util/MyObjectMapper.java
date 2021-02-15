package in.sevasuyog.util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class MyObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public MyObjectMapper() {
		super();
		Hibernate5Module hbm = new Hibernate5Module();
		hbm.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		hbm.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
		disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		registerModule(hbm);

	}

	public static <T> T fromJSON(final TypeReference<T> type, final String jsonPacket) {
		T data = null;

		try {
			data = (T) new ObjectMapper().readValue(jsonPacket, type);
		} catch (Exception e) {}
		
		return data;
	}
}
