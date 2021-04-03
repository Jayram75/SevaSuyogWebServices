package in.sevasuyog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.sevasuyog.util.StringDeserializer;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "SuggestionByUser")
@DynamicUpdate(value = true)
public class Suggestion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@ApiModelProperty(hidden=true)
    private Long id;
	@NotBlank @Size(max = 8, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String guid;
	@JsonIgnore
	private Long userId;
	@ManyToOne
    @JoinColumn(name="UserID", insertable = false, updatable = false)
    private User user;
	@JsonIgnore
	private Long fieldTypeId;
	@ManyToOne
    @JoinColumn(name="FieldTypeID", insertable = false, updatable = false)
    private FieldType fieldType;
	@NotBlank @Size(max = 50, min = 1)
	@JsonDeserialize(using=StringDeserializer.class)
	private String fieldValue;
	
	public Suggestion() {}
	
	public Suggestion(Long userId, Long fieldTypeId, String fieldValue) {
		this.userId = userId;
		this.fieldTypeId = fieldTypeId;
		this.fieldValue = fieldValue;
	}
	
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getFieldTypeId() {
		return fieldTypeId;
	}
	public void setFieldTypeId(Long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}