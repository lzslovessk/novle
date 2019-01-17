package realm;

/**
 * 没有连接数据库
 */
import java.util.HashMap;
import java.util.HashSet;
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

public class CustomerRealmCopy extends AuthorizingRealm{

	Map<String, String> userMap = new HashMap<>();
	{
		//userMap.put("Mark", "1234567");
		userMap.put("Mark", "d40fdd323f5322ff34a41f026f35cf20");
		super.setName("customerRealm");
	}
	
	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		// 从数据库或者缓存中获取角色数据
		Set<String> roles = getRolesByUserName(userName);
		Set<String> permissions = getPermissionsByUserName(userName);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	
	private Set<String> getPermissionsByUserName(String userName) {
		Set<String> sets = new HashSet<>();
		sets.add("user:delete");
		sets.add("user:add");
		return sets;
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	private Set<String> getRolesByUserName(String userName) {
		Set<String> sets = new HashSet<>();
		sets.add("admin");
		sets.add("user");
		return sets;
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
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Mark", password, "customerRealm");
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
		return authenticationInfo;
	}

	/**
	 * 模拟数据库查询凭证
	 * @param userName
	 * @return
	 */
	private String getPassWordByUserName(String userName) {
		return userMap.get(userName); 
	}
	
	public static void main(String[] args) {
		Md5Hash md5 = new Md5Hash("1234567","Mark");
		System.out.println(md5.toString());
	}

}
