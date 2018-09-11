package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Department;
import entity.Employee;
import entity.Project;
import util.DB;
import util.PageInfo;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:16
 * @Description
 *
 */

public class DepartmentDao {

	DB db = new DB();

	public List<Department> search() {
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from department");
			while (rs.next()) {
				Department dep = new Department();

				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

				deps.add(dep);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return deps;
	}

	public List<Department> search(PageInfo pageInfo) {
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state
					.executeQuery("select * from department limit " + pageInfo.getIndex() + "," + pageInfo.getLimit());
			while (rs.next()) {
				Department dep = new Department();

				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				dep.setProCount(rs.getInt("pro_count"));
				deps.add(dep);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return deps;
	}

	public List<Department> search(PageInfo pageInfo, Department dep) {
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();
		String sql = "select * from department where 1=1";
		if (!dep.getName().equals("")) {
			sql += " and name='" + dep.getName() + "'";
		}
		if (dep.getEmpCount() != -1) {
			sql += " and emp_count=" + dep.getEmpCount();
		}
		sql += " limit " + pageInfo.getIndex() + "," + pageInfo.getLimit();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);

			while (rs.next()) {
				Department d = new Department();

				d.setId(rs.getInt("id"));
				d.setName(rs.getString("name"));
				d.setEmpCount(rs.getInt("emp_count"));

				deps.add(d);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return deps;
	}

	public boolean add(Department dep) {
		Connection conn = db.getConnection();

		Statement state = null;
		boolean flag = false;
		try {
			state = conn.createStatement();
			int rs = state.executeUpdate("insert into department(name,emp_count)values('" + dep.getName() + "',0)");
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public Department search(int id) {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		Department dep = new Department();

		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from department where id=" + id);
			while (rs.next()) {
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return dep;
	}

	public List<Department> search(String ids) {
		List<Department> deps = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;

		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from department where id in(" + ids + ")");
			while (rs.next()) {
				Department dep = new Department();

				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

				deps.add(dep);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return deps;
	}

	public boolean update(Department dep) {
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			int rs = state.executeUpdate(
					"update department set name='" + dep.getName() + "',emp_count=0 where id=" + dep.getId() + "");
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public boolean updateBatch(List<Department> list) {

		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		int rs = 0;
		try {
			for (Department dep : list) {
				String sql = "update department set name=? where id=?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, dep.getName());
				pstat.setInt(2, dep.getId());
				rs = pstat.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, pstat, null);
		}

		return rs > 0;
	}

	public boolean delete(int id) {

		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			conn.setAutoCommit(false);
			int rs = state.executeUpdate("delete from department where id=" + id + "");
			int flag1 = state.executeUpdate("update employee set d_id=null where d_id=" + id);
			int flag2 = state.executeUpdate("delete from r_dep_pro where d_id=" + id);
			conn.commit();

			if (rs > 0 && flag1 > 0 && flag2 > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public boolean delete(String ids) {
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			conn.setAutoCommit(false);
			int rs = state.executeUpdate("delete from department where id in (" + ids + ")");
			int flag1 = state.executeUpdate("update employee set d_id=null where d_id in (" + ids + ")");
			int flag2 = state.executeUpdate("delete from r_dep_pro where d_id in (" + ids + ")");
			conn.commit();

			if (rs > 0 && flag1 > 0 && flag2 > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public int searchCount(Department dep) {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int rows = 0;
		String sql = "select count(*) from department where 1=1";
		if (!dep.getName().equals("")) {
			sql += " and name='" + dep.getName() + "'";
		}
		if (dep.getEmpCount() != -1) {
			sql += " and emp_count=" + dep.getEmpCount();
		}
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return rows;
	}

	public Map<String, Integer> depMap() {
		Map<String, Integer> map = new HashMap<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from department");
			while (rs.next()) {
				map.put(rs.getString("name"), rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return map;
	}

	public int count() {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int count = 0;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select count(1) as count from department");
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public Map<String, Object> searchPro(Integer did) {

		Map<String, Object> map = new HashMap<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			List<Project> proList = new ArrayList<>();
			Map<Integer, Integer> proEmpCount = new HashMap<>();
			state = conn.createStatement();
			rs = state.executeQuery("select * from department where id=" + did);
			if (rs.next()) {
				map.put("depName", rs.getString("name"));
			}
			
			String sql1 = "select * from v_dep_pro where d_id=" + did;
			String sql2 = "SELECT COUNT(*) FROM employee e LEFT JOIN r_dep_pro r ON e.d_id=r.d_id AND e.p_id=r.p_id WHERE e.p_id=? AND e.d_id=?";
			rs = state.executeQuery(sql1);
			
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				proList.add(pro);
			}

			for (Project pro : proList) {
				PreparedStatement temp = conn.prepareStatement(sql2);
				temp.setInt(1, pro.getId());
				temp.setInt(2, did);
				ResultSet tempRs = temp.executeQuery();
				int empCount = 0;
				if (tempRs.next()) {
					empCount = tempRs.getInt(1);
				}
				proEmpCount.put(pro.getId(), empCount);
			}
			map.put("proList", proList);
			map.put("proEmpCount", proEmpCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	public Map<Object, Object> search(Integer did, Integer pid) {
		// TODO Auto-generated method stub
		Map<Object, Object> map = new HashMap<>();
		Connection conn = db.getConnection();
		Statement state = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from v_dep_pro where d_id=" + did + " and p_id=" + pid);
			while (rs.next()) {
				map.put("depName", rs.getString("d_name"));
				map.put("proName", rs.getString("p_name"));
			}
			List<Employee> inList = new ArrayList<>();
			List<Employee> outList = new ArrayList<>();
			String sql1 = "SELECT e.* FROM employee e LEFT JOIN r_dep_pro r ON e.d_id=r.d_id AND e.p_id=r.p_id WHERE e.p_id=? AND e.d_id=?";
			String sql2 = "SELECT * FROM employee e LEFT JOIN department d on e.d_id=d.id WHERE d.id=? AND e.id NOT IN (SELECT e.id FROM employee e LEFT JOIN r_dep_pro r ON e.d_id=r.d_id AND e.p_id=r.p_id WHERE e.p_id=? AND e.d_id=?)";
			pstat = conn.prepareStatement(sql1);
			pstat.setInt(1, pid);
			pstat.setInt(2, did);
			rs = pstat.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setdId(rs.getInt("d_id"));
				emp.setpId(rs.getInt("p_id"));

				inList.add(emp);
			}
			pstat = conn.prepareStatement(sql2);
			pstat.setInt(1, did);
			pstat.setInt(2, pid);
			pstat.setInt(3, did);
			rs = pstat.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setdId(rs.getInt("d_id"));
				emp.setpId(rs.getInt("p_id"));

				outList.add(emp);
			}

			map.put("inList", inList);
			map.put("outList", outList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	public boolean manageAdd(Integer pid, Integer did, Integer eid) {
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			conn.setAutoCommit(false);
			state.executeUpdate("update employee set p_id=" + pid + " where id=" + eid + " and d_id=" +  did);			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll(conn, state, null);
		}
	}

	public boolean manageRemove(Integer did, Integer eid) {
		// TODO Auto-generated method stub

		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {
			state = conn.createStatement();
			conn.setAutoCommit(false);
			state.executeUpdate("update employee set p_id=null where d_id=" + did + " and id=" + eid);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll(conn, state, null);
		}
	}
}
