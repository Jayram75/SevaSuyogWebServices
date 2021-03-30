package in.sevasuyog.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MySession")
@DynamicUpdate(value = true)
public class MySession {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String sessionId;
	private Long userId;
	private Boolean isExpired;
	private String deviceInfo;
	private Integer deviceHash;
	private Timestamp updateTS;

	public MySession() {}
	
	public MySession(String sessionId, Long userId, String deviceInfo) {
		this.sessionId = sessionId;
		this.userId = userId;
		this.isExpired = false;
		this.deviceInfo = deviceInfo;
		this.deviceHash = deviceInfo.hashCode();
		this.updateTS = new Timestamp(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getDeviceHash() {
		return deviceHash;
	}

	public void setDeviceHash(Integer deviceHash) {
		this.deviceHash = deviceHash;
	}

	public Timestamp getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(Timestamp updateTS) {
		this.updateTS = updateTS;
	}
}
