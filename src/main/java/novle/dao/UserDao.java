package novle.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import novle.entity.User;

public interface UserDao {
	
	public User queryUserByUserName(String userName);
	
	public List<String> queryUserRolesByUserName(String userName);
	
	public List<String> queryPermissionsByUserName(@Param("set") Set<String> roleName);

}
