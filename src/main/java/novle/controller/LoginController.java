package novle.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import constants.ResultState;
import constants.ResultType;
import novle.entity.TestUser;
import novle.model.ResultDto;

@Controller
public class LoginController extends BaseController {
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		//清除session
		request.getSession().removeAttribute("identity");
		request.getSession().setAttribute("_const_cas_assertion_", null);
		//清除cookie
		Cookie[] cookies = request.getCookies();
		 for(int i = 0,len = cookies.length; i < len; i++) {
			Cookie cookie = new Cookie(cookies[i].getName(),null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
		}
		//重定向到单点登陆的登录页面
		return "";
	}
	
	@RequestMapping(value = "/login.do")
	@ResponseBody
	public ResultDto login(HttpServletRequest request, HttpServletResponse response, TestUser user) {
		ResultDto resultDto = new ResultDto();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try {
			// 是否记住密码
			token.setRememberMe(true);
			subject.login(token);
			// 授权
			if (!subject.hasRole("admin")) {
				resultDto.setStatusCode(ResultState.FAIL);
				resultDto.setResultType(ResultType.FAIL);
				resultDto.setMessage("没有admin权限"); 
			} else {
				resultDto.setStatusCode(ResultState.SUCCESS);
				resultDto.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			resultDto.setStatusCode(ResultState.FAIL);
			resultDto.setResultType(ResultType.FAIL);
		}
		return resultDto;
	}

	@RequestMapping(value = "/main.do")
	public String success(HttpServletRequest request, HttpServletResponse response) {
		
		return "main";
	}
	
	@RequestMapping(value = "/myhome.do")
	public String myhome(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "myhome";
	}
	
	/**
	 * 主体具有***角色
	 * @return
	 */
	@RequestMapping(value = "/testRole.do", method = RequestMethod.GET)
	//@RequiresRoles("admin")
	@ResponseBody
	public String testRole() {
		return "testRole success";
	}
	
	@RequestMapping(value = "/testRole1.do", method = RequestMethod.GET)
	//@RequiresRoles("admin1")
	@ResponseBody
	public String testRole1() {
		return "testRole1 success";
	}
	
	/**
	 * 主体具有***权限
	 * @return
	 */
	//@RequiresPermissions("user:select")
	@RequestMapping(value = "/testPerms.do", method = RequestMethod.GET)
	@ResponseBody
	public String testPermission() {
		return "testPermission success";
	}
	
	//@RequiresPermissions("user:select1")
	@RequestMapping(value = "/testPerms1.do", method = RequestMethod.GET)
	@ResponseBody
	public String testPermission1() {
		return "testPermission1 success";
	}


}
