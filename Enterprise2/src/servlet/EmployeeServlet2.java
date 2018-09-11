package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageInfo;

public class EmployeeServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeDao empDao = new EmployeeDao();
	DepartmentDao depDao = new DepartmentDao();
	List<Employee> list = new ArrayList<>();
	FileItemFactory factory = null;
	ServletFileUpload upload = null;
	List<FileItem> items = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			List<Department> depList = depDao.search();
			session.setAttribute("depList", depList);
			String type = request.getParameter("type");
			if (type != null) {
				if ("addShow".equals(type)) {
					show(request, response);
				} else if ("modifyShow".equals(type)) {
					show(request, response);
				} else if ("delete".equals(type)) {
					delete(request, response);
				} else if ("reset".equals(type)) {
					reset(request, response);
				} else if ("modify".equals(type)) {
					modify(request, response);
				} else if ("add".equals(type)) {
					add(request, response);
				} else if ("upload".equals(type)) {
					upload(request, response);
				} else if ("removeImg".equals(type)) {
					removeImg(request, response);
				} else {
					list(request, response);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void removeImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String imgName = request.getParameter("imgName").substring(request.getParameter("imgName").lastIndexOf("/") + 1,request.getParameter("imgName").length());
		System.out.println(imgName);
		PrintWriter out = response.getWriter();
		File img = new File("d:/upload/" + imgName);
		if(img.exists()) {
			img.delete();
			out.println(true);
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
				response.sendRedirect("employee2?type=list&page=1");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Integer> map = depDao.depMap();
			request.setAttribute("map", map);
			request.getRequestDispatcher("WEB-INF/employee/edit2.jsp").forward(request, response);
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
			request.getRequestDispatcher("WEB-INF/employee/employee2.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "D:/upload/";
		String fileName = "";
		factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
		upload = new ServletFileUpload(factory);
		items = upload.parseRequest(request);

		FileItem item = items.get(0);
		if (item.getFieldName().equals("img")) {
			UUID uuid = UUID.randomUUID();
			String subfix = item.getName().substring(item.getName().lastIndexOf("."));
			fileName = uuid.toString() + subfix;
			File savedFile = new File(path, fileName);
			item.write(savedFile);
			PrintWriter out = response.getWriter();
			out.print(fileName);
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {

			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			Integer age = null;
			Integer dId = null;
			String fileName = request.getParameter("imgName");
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			if (request.getParameter("dId") != null && !"".equals(request.getParameter("dId"))) {
				dId = Integer.parseInt(request.getParameter("dId"));
			}

			Employee emp = new Employee();
			emp.setName(name);
			emp.setAge(age);
			emp.setSex(sex);
			emp.setdId(dId);
			emp.setImg(fileName);
			System.out.println(emp.toString());
			boolean flag = empDao.add(emp);
			if (flag) {
				response.sendRedirect("employee2?type=list&page=1");
			}
		} catch (Exception e) {
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
				response.sendRedirect("employee2?type=list&page=1");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
