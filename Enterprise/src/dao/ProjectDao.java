package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import entity.Project;
import util.DB;

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
			for(Project p : pros) {
				rs = state.executeQuery("select * from v_dep_pro where p_id=" + p.getId());
				List<Department> deps = new ArrayList<>();
				while(rs.next()) {
					Department d = new Department();
					d.setId(rs.getInt("d_id"));
					d.setName(rs.getString("d_name"));
					deps.add(d);
				}
				p.setDeps(deps);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pros;
	}

	public boolean add(Project pro) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();

		Statement state = null;
		boolean flag = false;
		try {
			state = conn.createStatement();
			int rs = state.executeUpdate("insert into project(name)values('" + pro.getName() + "')");
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public Project selectById(int id) {
		// TODO Auto-generated method stub
		System.out.println(id + "***********");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return pro;
	}

	public boolean update(Project pro) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = db.getConnection();
		;
		Statement state = null;
		try {

			state = conn.createStatement();
			int rs = state
					.executeUpdate("update project set name='" + pro.getName() + "' where id=" + pro.getId() + "");
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Statement state = null;
		Connection conn = db.getConnection();
		try {

			state = conn.createStatement();
			conn.setAutoCommit(false);
			int rs = state.executeUpdate("delete from project where id=" + id);
			rs = state.executeUpdate("delete from r_dep_pro where p_id=" + id);

			conn.commit();

			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public List<Project> serchByCondition(Project pro) {
		// TODO Auto-generated method stub
		List<Project> pros = new ArrayList<>();
		Connection conn = db.getConnection();
		String sql = "select * from project where 1=1";
		if (!pro.getName().equals("")) {
			sql += " and name like '%" + pro.getName() + "%'";
		}

		System.out.println(sql);
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);

			while (rs.next()) {
				Project proj = new Project();

				proj.setId(rs.getInt("id"));
				proj.setName(rs.getString("name"));

				pros.add(proj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return pros;
	}

}
