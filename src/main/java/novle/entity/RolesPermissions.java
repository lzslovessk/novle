package novle.entity;

import java.io.Serializable;

public class RolesPermissions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2775344157430230893L;
	
	private Integer id;
	private String roleName;
	private String permission;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
