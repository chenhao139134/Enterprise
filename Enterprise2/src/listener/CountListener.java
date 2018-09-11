package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import websocket.WebSocket;

/**
 * Application Lifecycle Listener implementation class CountListener
 *
 */
@WebListener
public class CountListener implements HttpSessionListener,ServletContextListener {
	public static Integer numNow = 0;
	/**
	 * Default constructor.
	 */
	public CountListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		Integer num = 0;
		
		ServletContext application = arg0.getSession().getServletContext();
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num++;
		numNow++;
		WebSocket.sendMessage(String.valueOf(numNow));
		application.setAttribute("num", num);
		arg0.getSession().setAttribute("num", num);
		System.out.println("session create");
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		numNow--;
		WebSocket.sendMessage(String.valueOf(numNow));

		System.out.println("session destory");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
