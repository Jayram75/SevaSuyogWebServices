package in.sevasuyog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;

public class LocalityRequest {
	@NotBlank @Size(max = 4, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String guid;
	@NotBlank @Size(max = 50, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String locality;
	@NotBlank @Size(max = 4, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String cityGuid;
	@NotBlank @Size(max = 6, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String pinCode;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCityGuid() {
		return cityGuid;
	}
	public void setCityGuid(String cityGuid) {
		this.cityGuid = cityGuid;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
}
