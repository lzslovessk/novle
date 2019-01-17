package admin.filter;
/**
 * 角色-自定义filter
 */
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// 获取当前主体
		Subject subject = getSubject(request, response);
		String[] roles = (String[]) mappedValue;
		if (roles == null || roles.length == 0) {
			return true;
		}
		for (String role : roles) {
			if (subject.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

}
