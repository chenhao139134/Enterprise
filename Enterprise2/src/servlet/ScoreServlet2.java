package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ScoreDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.Constant;
import util.PageInfo;

public class ScoreServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ScoreDao scoDao = new ScoreDao();
	List<Score> list = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScoreServlet2() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		list(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void list(HttpServletRequest request, HttpServletResponse response) {
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			Integer pid = -1;
			Integer did = -1;
			boolean flag = false;
			String name = request.getParameter("name");
			String grade = request.getParameter("grade");
			if (name != null && name.length() > 0) {
				flag = true;
			}
			if (grade != null && grade.length() > 0) {
				flag = true;
			}
			if (request.getParameter("did") != null && request.getParameter("did").length() > 0) {
				did = Integer.parseInt(request.getParameter("did"));
				flag = true;
			}
			if (request.getParameter("pid") != null && request.getParameter("pid").length() > 0) {
				pid = Integer.parseInt(request.getParameter("pid"));
				flag = true;
			}
			Score sco = new Score();
			int rows = 0;
			if (flag) {
				Employee emp = new Employee();
				Department dep = new Department();
				Project pro = new Project();

				dep.setId(did);
				pro.setId(pid);
				emp.setDep(dep);
				emp.setName(name);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setGrade(grade);
				rows = scoDao.searchCount(sco);
			} else {
				rows = scoDao.count();
			}

			PageInfo pageInfo = new PageInfo(page, rows, Constant.SCO_NUM_IN_PAGE, Constant.SCO_NUM_OF_PAGE);
			Map<String, Object> map = new HashMap<>();
			map = scoDao.search();

			list = flag ? scoDao.search(pageInfo, sco) : scoDao.search(pageInfo);
			request.setAttribute("map", map);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("scos", list);
			request.setAttribute("sco", sco);
			request.getRequestDispatcher("WEB-INF/score/score2.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
