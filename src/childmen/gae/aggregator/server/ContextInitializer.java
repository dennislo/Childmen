package childmen.gae.aggregator.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//ServletContext is created at start. ServletContext used by all servlets and jsp files in the same application 
public class ContextInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
/*		ObjectifyService.register(Review.class);
		ObjectifyService.register(Game.class);*/

	}

}
