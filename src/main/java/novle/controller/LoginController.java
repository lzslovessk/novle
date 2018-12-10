package novle.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping(value = "/main.do")
	public String success(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "main";
	}
	
	@RequestMapping(value = "/myhome.do")
	public String myhome(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "myhome";
	}

}
