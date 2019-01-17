package novel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcRealmTest {
	
	DruidDataSource dataSource = new  DruidDataSource();
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/my_test?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("123456"); 
	}
	
	@Test
	public void testAuthentication() {
		
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		// 权限开关
		jdbcRealm.setPermissionsLookupEnabled(true); 
		
		String sql = "select password from test_user where user_name=? ";
		jdbcRealm.setAuthenticationQuery(sql);
		
		String roleSql = "select role_name from test_user_role where user_name = ?";
		jdbcRealm.setUserRolesQuery(roleSql); 
		
		// 1.构建SecurityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(jdbcRealm);
		
		// 2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		String username = "xiaoming";
		String password = "654321";
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		
		System.out.println("isAuthenticated:" + subject.isAuthenticated());// true
		
		/*subject.checkRole("admin");
		subject.checkRoles("admin","user"); 
		
		subject.checkPermission("user:select");*/
		
		subject.checkRole("user");
	}

}
