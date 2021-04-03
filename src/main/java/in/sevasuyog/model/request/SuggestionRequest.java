package in.sevasuyog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;

public class SuggestionRequest {
	@NotBlank @Size(max = 4, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String fieldTypeGuid;
	@NotBlank @Size(max = 50, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String fieldValue;
	
	public String getFieldTypeGuid() {
		return fieldTypeGuid;
	}
	public void setFieldTypeGuid(String fieldTypeGuid) {
		this.fieldTypeGuid = fieldTypeGuid;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}
