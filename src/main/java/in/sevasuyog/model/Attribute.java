package in.sevasuyog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Attribute")
@DynamicUpdate(value = true)
public class Attribute {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@ApiModelProperty(hidden=true)
    private Long id;
	@NotBlank @Size(max = 3, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String guid;
	@NotBlank
	@JsonDeserialize(using=StringDeserializer.class)
	private String attribute;
	@NotBlank
	@JsonDeserialize(using=StringDeserializer.class)
	private String defaultValue;
	@NotBlank
	@JsonDeserialize(using=StringDeserializer.class)
	private String possibleValues;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getPossibleValues() {
		return possibleValues;
	}
	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}
}
