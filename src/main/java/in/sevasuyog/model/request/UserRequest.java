package in.sevasuyog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;

public class UserRequest {
	@NotBlank @Size(max = 50, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String username;
	@NotBlank @Size(max = 10, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
