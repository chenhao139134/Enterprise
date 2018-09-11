package servlet;

import java.io.IOException;
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
import entity.Employee;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageInfo;

public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeDao empDao = new EmployeeDao();
	DepartmentDao depDao = new DepartmentDao();
	List<Employee> list = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		List<Department> depList = depDao.search();
		session.setAttribute("depList", depList);
		String type = request.getParameter("type");

		if ("addShow".equals(type)) {
			show(request, response);
		} else if ("add".equals(type)) {
			add(request, response);
		} else if ("modifyShow".equals(type)) {
			show(request, response);
		} else if ("modify".equals(type)) {
			modify(request, response);
		} else if ("delete".equals(type)) {
			delete(request, response);
		} else if ("reset".equals(type)) {
			reset(request, response);
		} else {
			list(request, response);
		}
	}

	private void reset(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.removeAttribute("empSearch");
		list(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = false;
			String ids = request.getParameter("id");
			flag = empDao.delete(ids);
			if (flag) {
				response.sendRedirect("employee?page=1");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Integer> map = depDao.depMap();
			request.setAttribute("map", map);
			request.getRequestDispatcher("WEB-INF/employee/edit.jsp").forward(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void list(HttpServletRequest request, HttpServletResponse response) {
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			String data = request.getParameter("data");
			int rows = 0;
			List<Employee> tempList = new ArrayList<>();
			Employee emp = null;
			if (data != null) {
				JSONArray jsonArray = JSONArray.fromObject(data);
				tempList = (List<Employee>) jsonArray.toCollection(jsonArray, Employee.class);
				emp = tempList.get(0);
				rows = empDao.searchCount(emp);
				request.setAttribute("emp", emp);
			} else {
				rows = empDao.count();
			}
			PageInfo pageInfo = new PageInfo(page, rows, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			Map<String, Integer> map = depDao.depMap();
			list = emp == null ? empDao.search(pageInfo) : empDao.search(pageInfo, emp);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("emps", list);
			request.setAttribute("map", map);
			request.getRequestDispatcher("WEB-INF/employee/employee.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			Integer age = null; 
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			Integer dId = null; 
			if (request.getParameter("dId") != null && !"".equals(request.getParameter("dId"))) {
				dId = Integer.parseInt(request.getParameter("dId"));
			}
				
			Employee emp = new Employee();
			emp.setName(name);
			emp.setAge(age);
			emp.setSex(sex);
			emp.setdId(dId);
			boolean flag = empDao.add(emp);
			if (flag) {
				response.sendRedirect("employee?page=1");
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
			List<Employee> list = (List<Employee>) jsonArray.toCollection(jsonArray, Employee.class);

			flag = empDao.updateBatch(list);
			if (flag) {
				response.sendRedirect("employee?page=1");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
