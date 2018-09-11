package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {
       
    /**
     * @see Filter#Filter()
     */
    public LoginFilter() {
        super();
    }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		 //1.ǿת
		 HttpServletRequest req =(HttpServletRequest) request;
		 HttpServletResponse res =(HttpServletResponse) response;
		
		 if(req.getSession().getAttribute("username") != null) {
			 chain.doFilter(request, response);
		 }else {
			 res.sendRedirect("user?type=loginShow");
//			 req.getRequestDispatcher("WEB-INF/login/login.jsp").forward(req, res);
		 }
		
	}

	public void destroy() {
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
