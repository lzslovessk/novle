package novel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import realm.CustomerRealm;

public class CustomerRealmTest {

	@Test
	public void testAuthentication() {
		
		CustomerRealm customerRealm = new CustomerRealm();
		
		// 1.构建SecurityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customerRealm);
		
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		// 算法名称，例如：md5加密
		matcher.setHashAlgorithmName("md5");
		// 加密次数
		matcher.setHashIterations(1);
		customerRealm.setCredentialsMatcher(matcher);
		
		// 2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		String username = "Mark";
		String password = "1234567";
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		
		System.out.println("isAuthenticated:" + subject.isAuthenticated());// true
		
		subject.checkRole("admin");
		subject.checkPermissions("user:delete","user:add"); 
		
	}
	
}
