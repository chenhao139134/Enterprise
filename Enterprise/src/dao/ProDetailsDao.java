package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Department;
import entity.Employee;
import entity.ProGroup;
import entity.Project;
import util.DB;

/**
 * @Author ChenHao
 * @Date 2018-08-11 11:14
 * @Description
 *
 */

public class ProDetailsDao {
	DB db = new DB();

	// 在项目管理模块，看到选中项目的所属部门
	public List<Department> searchDep(int pId) {
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();
		String sql = "select v.*,d.emp_count from v_dep_pro v left join department d on v.d_id=d.id where p_id=" + pId;
		System.out.println(sql + "**********");
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department(rs.getInt("d_id"), rs.getString("d_name"), rs.getInt("emp_count"));
				deps.add(dep);
			}
			for (Department d : deps) {
				List<Employee> empList = new ArrayList<>();
				rs = state.executeQuery("select * from employee where d_id=" + d.getId() + " and p_id=" + pId);
				while (rs.next()) {
					Employee emp = new Employee();
					emp.setId(rs.getInt("id"));
					emp.setName(rs.getString("name"));
					emp.setSex(rs.getString("sex"));
					emp.setAge(rs.getInt("age"));
					empList.add(emp);
				}
				d.setEmpList(empList);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			db.closeAll(conn, state, rs);
		}

		return deps;
	}

	public Map<Integer, Integer> searchCount(int pId) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Connection conn = db.getConnection();
		String sql = "select * from r_dep_pro where p_id=" + pId;
		System.out.println(sql + "+++++++++++");
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getInt("d_id"), rs.getInt("count"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			db.closeAll(conn, state, rs);
		}

		return map;
	}

	public ProGroup searchPro(int pId) {
		ProGroup prog = new ProGroup();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from project where id=" + pId);
			while (rs.next()) {

				List<Department> deps = new ArrayList<>();
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				Project p = new Project();

				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				deps = searchDep(pId);
				map = searchCount(pId);
				prog.setPro(p);
				prog.setDeps(deps);
				prog.setMap(map);
			}
			System.out.println("---------");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return prog;
	}

	public boolean addDepPro(int d_id, int p_id, int count) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		int r = 0;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from r_dep_pro where d_id=" + d_id + " and p_id=" + p_id);
			if (rs.next()) {
				r = state.executeUpdate(
						"update r_dep_pro set count=" + count + " where d_id=" + d_id + " and p_id=" + p_id);
				System.out.println("111111111111111");
			} else {
				
				r = state.executeUpdate(
						"insert into r_dep_pro(d_id, p_id, count)values(" + d_id + "," + p_id + ", "+ count +")");
				System.out.println("222222222222222");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return r > 0;
	}

	public List<Department> searchNotEquals(int id) {
		List<Department> deps = new ArrayList<>();
		Connection conn = db.getConnection();

		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("SELECT * FROM department d WHERE d.id NOT IN(" + "	SELECT d_id FROM r_dep_pro r"
					+ "	WHERE p_id=" + id + ")");
			while (rs.next()) {
				Department dep = new Department();

				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

				deps.add(dep);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return deps;
	}

	public boolean deleteDepPro(int p_id, int d_id) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();

		Statement state = null;
		boolean flag = false;
		try {
			state = conn.createStatement();
			int rs = state.executeUpdate("delete from r_dep_pro where d_id=" + d_id + " and p_id=" + p_id);
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

}
