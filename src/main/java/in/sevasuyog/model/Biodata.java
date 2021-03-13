package in.sevasuyog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "biodata")
@DynamicUpdate(value = true)
public class Biodata {
	@Id
    @Column(name = "UserID")
	@JsonIgnore
    private Long userId;
	
	@Column
	private String fullName;
	
	@OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "UserID")
    private User user;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
