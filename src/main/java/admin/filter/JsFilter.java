package admin.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsFilter implements Filter {
	public static final Logger logger = LoggerFactory.getLogger("JsFilter");
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String key ="";
		String value = "";
		Enumeration<?> keys = request.getParameterNames();
		String regEx = "<script.*?>.*?<\\/script>"; 
		Pattern p = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		Matcher m = null;
		boolean b = false;
		while (keys.hasMoreElements()) {
		  key = keys.nextElement().toString();
		  value = request.getParameter(key);
		  m = p.matcher(value);
		  if(m.find()){
			  b = true;
			  break;
		  }
		}
		if(b){
			response.setHeader("contextPath", request.getContextPath());
			response.setStatus(406);
        	if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with")
                            .equalsIgnoreCase("XMLHttpRequest")) {
        		return;
        	} else {
        		response.sendRedirect("http://"+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/error406.jsp");
        		return;
        	}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
