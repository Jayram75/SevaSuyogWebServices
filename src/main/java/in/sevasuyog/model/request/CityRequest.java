package in.sevasuyog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;

public class CityRequest {
	@NotBlank @Size(max = 4, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String guid;
	@NotBlank @Size(max = 50, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String city;
	@NotBlank @Size(max = 3, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String stateGuid;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateGuid() {
		return stateGuid;
	}
	public void setStateGuid(String stateGuid) {
		this.stateGuid = stateGuid;
	}
}
