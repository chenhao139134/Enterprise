package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import entity.Employee;
import entity.Project;
import util.DB;

/**
 * @Author ChenHao
 * @Date 2018-07-30 14:00
 * @Description
 *
 */

public class EmployeeDao {
	DB db = new DB();

	public List<Employee> search() {
		List<Employee> emps = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(
					"SELECT e.*,d.name as depname,emp_count FROM employee e LEFT JOIN department d ON e.d_id=d.id");
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));
				Department dep = new Department();
				dep.setName(rs.getString("depName"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				emps.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return emps;
	}

	public boolean add(Employee emp) {
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {
			state = conn.createStatement();
			int rs = state.executeUpdate("insert into employee(name,sex,age,d_id)values('" + emp.getName() + "','"
					+ emp.getSex() + "'," + emp.getAge() + "," + emp.getdId() + ")");
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

	public Employee selectById(int id) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		Employee emp = new Employee();

		try {
			state = conn.createStatement();
			rs = state.executeQuery(
					"select e.*,d.name as depname,emp_count from employee e left join department d on e.d_id=d.id where e.id="
							+ id);
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));
				Department dep = new Department();
				dep.setName(rs.getString("depname"));
				dep.setId(rs.getInt("d_id"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return emp;
	}

	public boolean update(Employee emp) {
		// TODO Auto-generated method stub
		boolean flag = false;
		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		try {

			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setInt(4, emp.getdId());
			pstat.setInt(5, emp.getId());
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, pstat, null);
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
			int rs = state.executeUpdate("delete from employee where id=" + id + "");
			state.executeUpdate("delete from score where e_id=" + id);
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

	public List<Employee> searchCondition(Employee ee) {
		List<Employee> emps = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(ee);
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("empname"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				Department dep = new Department();
				dep.setName(rs.getString("depname"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				emps.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return emps;
	}

	// 部门详情页查找该部门员工
	public List<Employee> searchByDid(int dId) {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		List<Employee> emps = new ArrayList<>();

		try {
			state = conn.createStatement();
			rs = state.executeQuery(
					"select e.*,p.id as pro_id,p.name as p_name, d.name as d_name from employee e left join project p on e.p_id=p.id left join department d on e.d_id=d.id where d_id="
							+ dId);
			while (rs.next()) {
				Employee emp = new Employee();
				Project pro = new Project();
				Department dep = new Department();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				pro.setId(rs.getInt("pro_id"));
				pro.setName(rs.getString("p_name"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);
				emp.setPro(pro);
				emps.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return emps;
	}

	public String sqlConcat(Employee e) {
		String dep1 = "";
		String dep2 = "";
		dep1 = ", d.name as depname,emp_count";
		dep2 = " left join department d on" + " d.id=e.d_id";

		String sql = "select e.id,e.name as empname, e.sex, e.age" + dep1 + " from employee e" + dep2 + " where 1=1";
		if (e.getName().length() != 0) {
			sql += " and e.name like '%" + e.getName() + "%'";
		}
		if (e.getSex().length() != 0) {
			sql += " and sex='" + e.getSex() + "'";
		}
		if (e.getAge() != 0) {
			sql += " and age=" + e.getAge();
		}
		if (e.getdId() != 0) {
			sql += " and d.id=" + e.getdId();
		}

		System.out.println(sql);
		return sql;
	}
}
