package listener;

import javax.servlet.ServletContextEvent;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
	
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}

}
