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
import entity.Project;
import util.DB;
import util.PageInfo;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:16
 * @Description
 *
 */

public class ProjectDao {

	DB db = new DB();

	public List<Project> search() {
		List<Project> pros = new ArrayList<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project");
			while (rs.next()) {
				Project pro = new Project();

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));

				pros.add(pro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pros;
	}

	public List<Project> search(PageInfo pageInfo) {
		List<Project> pros = new ArrayList<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project limit " + pageInfo.getIndex() + "," + pageInfo.getLimit());
			while (rs.next()) {
				Project pro = new Project();

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				pros.add(pro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pros;
	}

	public List<Project> search(PageInfo pageInfo, Project pro) {
		List<Project> pros = new ArrayList<>();
		Connection conn = db.getConnection();
		String sql = "select * from project where 1=1";
		if (!pro.getName().equals("")) {
			sql += " and name='" + pro.getName() + "'";
		}
		sql += " limit " + pageInfo.getIndex() + "," + pageInfo.getLimit();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);

			while (rs.next()) {
				Project d = new Project();

				d.setId(rs.getInt("id"));
				d.setName(rs.getString("name"));
				pros.add(d);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return pros;
	}

	public boolean add(Project pro) {
		Connection conn = db.getConnection();

		Statement state = null;
		boolean flag = false;
		try {
			state = conn.createStatement();
			int rs = state.executeUpdate("insert into project(name,emp_count)values('" + pro.getName() + "',0)");
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

	public Project search(int id) {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		Project pro = new Project();

		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project where id=" + id);
			while (rs.next()) {
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pro;
	}

	public List<Project> search(String ids) {
		List<Project> pros = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;

		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project where id in(" + ids + ")");
			while (rs.next()) {
				Project pro = new Project();

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				pros.add(pro);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pros;
	}

	public boolean update(Project pro) {
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			int rs = state.executeUpdate(
					"update project set name='" + pro.getName() + "',emp_count=0 where id=" + pro.getId() + "");
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

	public boolean updateBatch(List<Project> list) {

		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		int rs = 0;
		try {
			for (Project pro : list) {
				String sql = "update project set name=? where id=?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, pro.getName());
				pstat.setInt(2, pro.getId());
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
			int rs = state.executeUpdate("delete from project where id=" + id + "");
			int flag1 = state.executeUpdate("update employee set d_id=null where d_id=" + id);
			int flag2 = state.executeUpdate("delete from r_pro_pro where d_id=" + id);
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
			int rs = state.executeUpdate("delete from project where id in (" + ids + ")");
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

	public int searchCount(Project pro) {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int rows = 0;
		String sql = "select count(*) from project where 1=1";
		if (!pro.getName().equals("")) {
			sql += " and name='" + pro.getName() + "'";
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

	public Map<String, Integer> proMap() {
		Map<String, Integer> map = new HashMap<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project");
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
			rs = state.executeQuery("select count(1) as count from project");
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	// 查在本项目及不在本项目的部门
	public Map<String, List> searchDep(Integer id) {
		// TODO Auto-generated method stub
		Map<String, List> map = new HashMap<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			List<Department> inList = new ArrayList<>();
			List<Department> outList = new ArrayList<>();
			String sql1 = "select v.*,d.emp_count from v_dep_pro v left join department d on v.d_id=d.id where p_id="
					+ id;
			String sql2 = "SELECT * FROM department d WHERE d.id NOT IN(" + "	SELECT d_id FROM r_dep_pro r"
					+ "	WHERE p_id=" + id + ")";
			rs = state.executeQuery(sql1);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				inList.add(dep);
			}

			rs = state.executeQuery(sql2);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				outList.add(dep);
			}
			map.put("inList", inList);
			map.put("outList", outList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return map;
	}

	public boolean manageRemove(Integer pid, Integer did) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			conn.setAutoCommit(false);
			state.executeUpdate("delete from employee where p_id=" + pid);
			state.executeUpdate("delete from r_dep_pro where d_id=" + did + " and p_id=" + pid);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll(conn, state, null);
		}

	}

	public boolean manageAdd(Integer pid, Integer did) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = db.getConnection();
		PreparedStatement pstat = null;
		try {

			conn.setAutoCommit(false);
			String sql = "insert into r_dep_pro(d_id,p_id) values(?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, did);
			pstat.setInt(2, pid);
			int rs = pstat.executeUpdate();
			conn.commit();

			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, pstat, null);
		}
		return flag;
	}
}
