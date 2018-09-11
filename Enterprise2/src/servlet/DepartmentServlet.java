package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Project;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Constant;
import util.PageInfo;

public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeDao empDao = new EmployeeDao();
	DepartmentDao depDao = new DepartmentDao();
	List<Department> list = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DepartmentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		List<Department> depList = depDao.search();
		session.setAttribute("depList", depList);
		String type = request.getParameter("type");

		if ("addShow".equals(type)) {
			request.getRequestDispatcher("WEB-INF/department/edit.jsp").forward(request, response);
		} else if ("add".equals(type)) {
			add(request, response);
		} else if ("modifyShow".equals(type)) {
			modifyShow(request, response);
		} else if ("modify".equals(type)) {
			modify(request, response);
		} else if ("delete".equals(type)) {
			delete(request, response);
		} else if ("proShow".equals(type)) {
			proShow(request, response);
		} else if ("emp2pro".equals(type)) {
			emp2pro(request, response);
		} else if ("manageRemove".equals(type)) {
			manage(request, response);
		} else if ("manageAdd".equals(type)) {
			manage(request, response);
		} else {
			list(request, response);
		}
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			Integer pid = Integer.parseInt(request.getParameter("pid"));
			Integer did = Integer.parseInt(request.getParameter("did"));
			Integer eid = Integer.parseInt(request.getParameter("eid"));

			boolean flag = "manageAdd".equals(type) ? depDao.manageAdd(pid, did, eid) : depDao.manageRemove(did, eid);
			if (flag) {
				Map<Object, Object> map = depDao.search(did, pid);
				JSONObject jsonObject = JSONObject.fromObject(map);
				PrintWriter out = response.getWriter();
				out.println(jsonObject);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void emp2pro(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer did = Integer.parseInt(request.getParameter("did"));
			Integer pid = Integer.parseInt(request.getParameter("pid"));

			Map<Object, Object> map = depDao.search(did, pid);
			request.setAttribute("depName", map.get("depName"));
			request.setAttribute("proName", map.get("proName"));
			request.setAttribute("inList", map.get("inList"));
			request.setAttribute("outList", map.get("outList"));

			request.setAttribute("did", did);
			request.setAttribute("pid", pid);

			request.getRequestDispatcher("/WEB-INF/department/manage.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	private void proShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer did = Integer.parseInt(request.getParameter("id"));
			Map<String, Object> map = depDao.searchPro(did);

			request.setAttribute("depName", map.get("depName"));
			request.setAttribute("pros", map.get("proList"));
			request.setAttribute("dId", did);
			request.setAttribute("proEmpCount", map.get("proEmpCount"));
			request.getRequestDispatcher("WEB-INF/department/dep_pro.jsp").forward(request, response);
		} catch (ServletException | IOException e) {

			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = false;
			String ids = request.getParameter("id");
			flag = depDao.delete(ids);
			if (flag) {
				response.sendRedirect("department?page=1");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void modifyShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			Department dep = new Department();
			Integer id = Integer.parseInt(request.getParameter("id"));
			dep = depDao.search(id);
			request.setAttribute("dep", dep);
			request.getRequestDispatcher("WEB-INF/department/edit.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
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
			String data = request.getParameter("data");
			int rows = 0;
			List<Department> tempList = new ArrayList<>();
			Department dep = null;
			if (data != null) {
				JSONArray jsonArray = JSONArray.fromObject(data);
				tempList = (List<Department>) jsonArray.toCollection(jsonArray, Department.class);
				dep = tempList.get(0);
				rows = depDao.searchCount(dep);
				request.setAttribute("dep", dep);
			} else {
				rows = depDao.count();
			}
			PageInfo pageInfo = new PageInfo(page, rows, Constant.DEP_NUM_IN_PAGE, Constant.DEP_NUM_OF_PAGE);

			list = dep == null ? depDao.search(pageInfo) : depDao.search(pageInfo, dep);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("deps", list);

			request.getRequestDispatcher("WEB-INF/department/department.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");

			Department dep = new Department();
			dep.setName(name);
			boolean flag = depDao.add(dep);
			if (flag) {
				response.sendRedirect("department?page=1");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {
		try { // TODO Auto-generated method stub
			boolean flag = false;
			String data = request.getParameter("data");
			JSONArray jsonArray = JSONArray.fromObject(data);
			List<Department> list = (List<Department>) jsonArray.toCollection(jsonArray, Department.class);

			flag = depDao.updateBatch(list);

			response.sendRedirect("department?page=1");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
