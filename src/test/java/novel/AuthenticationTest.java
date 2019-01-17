package novel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
	
	SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
	
	@Before
	public void addUser() {
		String username = "Mark";
		String password = "123456";
		//String roles = "admin";
		String[] roles = {"admin","user"};
		
		simpleAccountRealm.addAccount(username, password, roles);
	}
	
	@Test
	public void testAuthentication() {
		// 1.构建SecurityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		// 2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		String username = "Mark";
		String password = "123456";
		//String roles = "admin";
		String[] roles = {"admin","user"};
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		
		System.out.println("isAuthenticated:" + subject.isAuthenticated());// true
		
//		subject.logout();
//		System.out.println("isAuthenticated:" + subject.isAuthenticated());// false
		
		subject.checkRoles(roles);
		
	}
}
