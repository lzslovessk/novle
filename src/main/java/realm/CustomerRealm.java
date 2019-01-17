package realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import novle.dao.UserDao;
import novle.entity.User;

public class CustomerRealm extends AuthorizingRealm{
	
	@Autowired
	private UserDao userDao;

	Map<String, String> userMap = new HashMap<>();
	{
		userMap.put("Mark", "d40fdd323f5322ff34a41f026f35cf20");
		super.setName("customerRealm");
	}
	
	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		// 从数据库或者缓存中获取角色数据
		Set<String> roles = getRolesByUserName(userName);
		Set<String> permissions = getPermissionsByUserName(roles);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.从主体传过来的认证信息中，获取用户名
		String userName = (String) token.getPrincipal();
		
		// 2.通过用户名到数据库中获取认证
		String password = getPassWordByUserName(userName);
		if (password == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, password, "customerRealm");
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
		return authenticationInfo;
	}

	/**
	   *   查询凭证
	 * @param userName
	 * @return
	 */
	private String getPassWordByUserName(String userName) {
		User user = userDao.queryUserByUserName(userName);
		if (user != null) {
			return user.getPassword(); 
		} else {
			return "";
		}
	}
	
	private Set<String> getPermissionsByUserName(Set<String> roleName) {
		List<String> list = userDao.queryPermissionsByUserName(roleName);
		Set<String> sets = new HashSet<>(list);
		return sets;
	}

	private Set<String> getRolesByUserName(String userName) {
		List<String> list = userDao.queryUserRolesByUserName(userName);
		Set<String> sets = new HashSet<>(list);
		return sets;
	}
	
	public static void main(String[] args) {
		Md5Hash md5 = new Md5Hash("1234567","Mark");
		System.out.println(md5.toString());
	}

}
