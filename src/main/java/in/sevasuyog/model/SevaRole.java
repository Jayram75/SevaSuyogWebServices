package in.sevasuyog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SevaRole")
@DynamicUpdate(value = true)
public class SevaRole {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
    private Long id;
	private String guid;
	private String saveRole;
	
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
	public String getSaveRole() {
		return saveRole;
	}
	public void setSaveRole(String saveRole) {
		this.saveRole = saveRole;
	}
}
