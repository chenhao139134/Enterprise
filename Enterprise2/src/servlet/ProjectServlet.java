package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProjectDao;
import entity.Department;
import entity.Project;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Constant;
import util.PageInfo;

public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProjectDao proDao = new ProjectDao();
	List<Project> list = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet() {
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
		List<Project> proList = proDao.search();
		session.setAttribute("proList", proList);
		String type = request.getParameter("type");

		if ("addShow".equals(type)) {
			request.getRequestDispatcher("WEB-INF/project/edit.jsp").forward(request, response);
		} else if ("add".equals(type)) {
			add(request, response);
		} else if ("modifyShow".equals(type)) {
			modifyShow(request, response);
		} else if ("modify".equals(type)) {
			modify(request, response);
		} else if ("delete".equals(type)) {
			delete(request, response);
		} else if ("update".equals(type)) {
			update(request, response);
		} else if ("manageShow".equals(type)) {
			manageShow(request, response);
		} else if ("manageShow2".equals(type)) {
			manageShow2(request, response);
		}else if ("manageShow3".equals(type)) {
			manageShow3(request, response);
		}else if ("manageShow4".equals(type)) {
			manageShow4(request, response);
		} else if ("manageAdd".equals(type)) {
			manage(request, response);
		} else if ("manageRemove".equals(type)) {
			manage(request, response);
		} else if ("manageAdd2".equals(type)) {
			manage2(request, response);
		} else if ("manageRemove2".equals(type)) {
			manage2(request, response);
		} else if ("manageAdd3".equals(type)) {
			manage3(request, response);
		} else if ("manageRemove3".equals(type)) {
			manage3(request, response);
		} else {
			list(request, response);
		}
	}

	private void manageShow4(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> map = new HashMap<>();
			Integer id = Integer.parseInt(request.getParameter("id"));
			Project pro = proDao.search(id);
			map = proDao.searchDep(id);

			List<Department> inList = map.get("inList");
			List<Department> outList = map.get("outList");

			request.setAttribute("pro", pro);
			request.setAttribute("inList", inList);
			request.setAttribute("outList", outList);

			request.getRequestDispatcher("/WEB-INF/project/manage4.jsp").forward(request, response);
//			Map<String, List> map = new HashMap<>();
//			Map<String, Object> resultMap = new HashMap<>();
//			Integer id = Integer.parseInt(request.getParameter("id"));
//			Project pro = proDao.search(id);
//			map = proDao.searchDep(id);
//
//			
//			response.setCharacterEncoding("utf-8");
//
//			List<Department> inList = map.get("inList");
//			List<Department> outList = map.get("outList");
//
//			resultMap.put("pro", pro);
//			resultMap.put("inList", inList);
//			resultMap.put("outList", outList);
//			
//			
//			JSONObject jsonObject = JSONObject.fromObject(resultMap);
//			PrintWriter out = response.getWriter();
//			out.println(jsonObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			Integer pid = Integer.parseInt(request.getParameter("pid"));
			Integer did = Integer.parseInt(request.getParameter("did"));
			String type = request.getParameter("type");
			boolean flag = "manageAdd".equals(type) ? proDao.manageAdd(pid, did) : proDao.manageRemove(pid, did);
			if (flag) {
				Map<String, List> map = new HashMap<>();
				map = proDao.searchDep(pid);
				JSONObject jsonObject = JSONObject.fromObject(map);
				PrintWriter out = response.getWriter();
				out.println(jsonObject);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void manage2(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			Integer pid = Integer.parseInt(request.getParameter("pid"));
			String dids = request.getParameter("did");
			String[] did = dids.split(",");
			List<Integer> ids = new ArrayList<>();
			for(String s : did) {
				if(!",".equals(s)) {
					ids.add(Integer.parseInt(s));
				}
			}
			String type = request.getParameter("type");
			boolean flag = false;
			for(Integer id : ids) {
				flag = "manageAdd2".equals(type) ? proDao.manageAdd(pid, id) : proDao.manageRemove(pid, id);
			}

			PrintWriter out = response.getWriter();
			out.println(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void manage3(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			Integer pid = Integer.parseInt(request.getParameter("pid"));
			String dids = request.getParameter("did");
			String[] did = dids.split(",");
			List<Integer> ids = new ArrayList<>();
			for(String s : did) {
				if(!",".equals(s)) {
					ids.add(Integer.parseInt(s));
				}
			}
			String type = request.getParameter("type");
			boolean flag = false;
			for(Integer id : ids) {
				flag = "manageAdd3".equals(type) ? proDao.manageAdd(pid, id) : proDao.manageRemove(pid, id);
			}

			PrintWriter out = response.getWriter();
			out.println(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void manageShow(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> map = new HashMap<>();
			Integer id = Integer.parseInt(request.getParameter("id"));
			Project pro = proDao.search(id);
			map = proDao.searchDep(id);

			List<Department> inList = map.get("inList");
			List<Department> outList = map.get("outList");

			request.setAttribute("pro", pro);
			request.setAttribute("inList", inList);
			request.setAttribute("outList", outList);

			request.getRequestDispatcher("/WEB-INF/project/manage.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void manageShow2(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> map = new HashMap<>();
			Integer id = Integer.parseInt(request.getParameter("id"));
			Project pro = proDao.search(id);
			map = proDao.searchDep(id);

			List<Department> inList = map.get("inList");
			List<Department> outList = map.get("outList");

			request.setAttribute("pro", pro);
			request.setAttribute("inList", inList);
			request.setAttribute("outList", outList);

			request.getRequestDispatcher("/WEB-INF/project/manage2.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void manageShow3(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> map = new HashMap<>();
			Integer id = Integer.parseInt(request.getParameter("id"));
			Project pro = proDao.search(id);
			map = proDao.searchDep(id);

			List<Department> inList = map.get("inList");
			List<Department> outList = map.get("outList");

			request.setAttribute("pro", pro);
			request.setAttribute("inList", inList);
			request.setAttribute("outList", outList);

			request.getRequestDispatcher("/WEB-INF/project/manage3.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");

			Project pro = new Project();
			pro.setName(name);
			boolean flag = proDao.update(pro);
			if (flag) {
				response.sendRedirect("project?page=1");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = false;
			String ids = request.getParameter("id");
			flag = proDao.delete(ids);
			if (flag) {
				response.sendRedirect("project?page=1");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void modifyShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project pro = new Project();
			Integer id = Integer.parseInt(request.getParameter("id"));
			pro = proDao.search(id);
			request.setAttribute("pro", pro);
			request.getRequestDispatcher("WEB-INF/project/edit.jsp").forward(request, response);
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
			List<Project> tempList = new ArrayList<>();
			Project pro = null;
			if (data != null) {
				JSONArray jsonArray = JSONArray.fromObject(data);
				tempList = (List<Project>) jsonArray.toCollection(jsonArray, Project.class);
				pro = tempList.get(0);
				rows = proDao.searchCount(pro);
				request.setAttribute("pro", pro);
			} else {
				rows = proDao.count();
			}
			PageInfo pageInfo = new PageInfo(page, rows, Constant.DEP_NUM_IN_PAGE, Constant.DEP_NUM_OF_PAGE);

			list = pro == null ? proDao.search(pageInfo) : proDao.search(pageInfo, pro);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("pros", list);

			request.getRequestDispatcher("WEB-INF/project/project.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");

			Project pro = new Project();
			pro.setName(name);
			boolean flag = proDao.add(pro);
			if (flag) {
				response.sendRedirect("project?page=1");
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
			List<Project> list = (List<Project>) jsonArray.toCollection(jsonArray, Project.class);

			flag = proDao.updateBatch(list);

			response.sendRedirect("project?page=1");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
