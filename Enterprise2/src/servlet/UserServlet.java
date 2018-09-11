package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import net.sf.json.JSONObject;
import util.RandomCode;
import util.ValidateCode;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDao();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if ("logout".equals(type)) {
			doLogout(request, response);
		} else if ("randcode".equals(type)) {
			randomCode(request, response);
		} else if ("registerShow".equals(type)) {
			ragisterShow(request, response);
		}else if ("loginShow".equals(type)) {
			loginShow(request, response);
		} else if ("register".equals(type)) {
			register(request, response);
		} else {
			doLogin(request, response);
		}

	}

	private void ragisterShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	private void loginShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String pwd = request.getParameter("pwd");
			User user = new User();
			user.setUsername(username);
			user.setPassword(pwd);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			user.setDate(sdf.format(date));
			if(userDao.add(user) != -1) {
				request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void randomCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			RandomCode rc = new RandomCode();

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			ValidateCode vc = rc.getValdateCode();
			request.getSession().setAttribute("randcode", vc.getCode());
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(vc.getImage(), "JPEG", sos);
			sos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			session.removeAttribute("username");
			response.sendRedirect("index");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = (String) request.getSession().getAttribute("randcode");
			
			Map<String, String> map = new HashMap<>();
			String username = request.getParameter("username");
			String pwd = request.getParameter("pwd");
			String randcode = request.getParameter("randcode");
			User user = new User();
			user.setUsername(username);
			user.setPassword(pwd);
			
			if (code.equalsIgnoreCase(randcode)) {

				map = userDao.search(user);

				if (map.get("type").equals("true")) {
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
				}
			}else {
				map.put("type", "false");
				map.put("msg", "验证码不正确！");
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			out.println(jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
