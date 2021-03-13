package in.sevasuyog.model.request;

public class LoginRequest {
	private String username;
	private String password;
	private String roleGUID;
	
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
	public String getRoleGUID() {
		return roleGUID;
	}
	public void setRoleGUID(String roleGUID) {
		this.roleGUID = roleGUID;
	}
}
