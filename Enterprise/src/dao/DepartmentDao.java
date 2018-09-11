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
			for (Department d : deps) {
				List<Project> pro = new ArrayList<>();
				rs = state.executeQuery("select * from v_dep_pro where d_id=" + d.getId());
				while (rs.next()) {
					Project p = new Project();
					p.setId(rs.getInt("p_id"));
					p.setName(rs.getString("p_name"));
					pro.add(p);
				}
				d.setProList(pro);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, rs);
		}
		return deps;
	}
	

	public boolean add(Department dep) {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public Department selectById(int id) {
		// TODO Auto-generated method stub
		System.out.println(id + "***********");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, rs);
		}
		return dep;
	}

	public boolean update(Department dep) {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, null);
		}
		return flag;
	}

	public List<Department> serchByCondition(Department dep) {
		// TODO Auto-generated method stub
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();
		String sql = "select * from department where 1=1";
		if (!dep.getName().equals("")) {
			sql += " and name='" + dep.getName() + "'";
		}
		if (dep.getEmpCount() != -1) {
			sql += " and emp_count=" + dep.getEmpCount();
		}
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeAll(conn, state, rs);
		}

		return deps;
	}

}
