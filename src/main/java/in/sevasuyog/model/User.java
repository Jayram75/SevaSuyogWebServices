package in.sevasuyog.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
@DynamicUpdate(value = true)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    private String password;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Biodata biodata;
    
    @JsonIgnore
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<UserAttribute> userAttributes;

	public Set<UserAttribute> getUserAttributes() {
		return userAttributes;
	}

	public void setUserAttributes(Set<UserAttribute> userAttributes) {
		this.userAttributes = userAttributes;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Biodata getBiodata() {
		return biodata;
	}

	public void setBiodata(Biodata biodata) {
		this.biodata = biodata;
	}
}
