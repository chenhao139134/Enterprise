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
import util.PageInfo;

/**
 * @Author ChenHao
 * @Date 2018-07-30 14:00
 * @Description
 *
 */

public class EmployeeDao {
	DB db = new DB();

	public List<Employee> search(PageInfo page) {
		List<Employee> emps = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM employee"
				+ " limit " + page.getIndex() + "," + page.getLimit();
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));
				emp.setImg(rs.getString("img"));

				emps.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return emps;
	}
	public List<Employee> search(PageInfo pageInfo,Employee ee) {
		List<Employee> emps = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(ee, 2);
		sql += " limit " + pageInfo.getIndex() + "," + pageInfo.getLimit();
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));	
				emp.setImg(rs.getString("img"));

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

	public List<Employee> search(String ids) {
		// TODO Auto-generated method stub
		List<Employee> emps = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(
					"SELECT * FROM employee where id in("
							+ ids + ")");
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));
				emp.setImg(rs.getString("img"));
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
		PreparedStatement pstat = null;
		try {
			
			String sql = "insert into employee(name,sex,age,d_id,img) values(?,?,?,?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setObject(3, emp.getAge());
			pstat.setObject(4, emp.getdId());
			pstat.setObject(5, emp.getImg());
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

	public Employee selectById(int id) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		Employee emp = new Employee();

		try {
			state = conn.createStatement();
			rs = state.executeQuery(
					"select * from employee where id=" + id);
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAge(rs.getInt("age"));
				emp.setSex(rs.getString("sex"));
				emp.setdId(rs.getInt("d_id"));

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
			pstat.setObject(4, emp.getdId());
			pstat.setObject(5, emp.getId());
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

	public boolean updateBatch(List<Employee> list) {
		// TODO Auto-generated method stub

		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		int rs = 0;
		try {
			for (Employee emp : list) {
				String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, emp.getName());
				pstat.setString(2, emp.getSex());
				pstat.setInt(3, emp.getAge());
				pstat.setInt(4, emp.getdId());
				pstat.setInt(5, emp.getId());
				rs = pstat.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, pstat, null);
		}

		return rs > 0;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Connection conn = db.getConnection();
		Statement state = null;
		try {

			state = conn.createStatement();
			int rs = state.executeUpdate("delete from employee where id=" + id);
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

	public boolean delete(String ids) {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();
		Statement state = null;
		int rs = 0;
		try {
			state = conn.createStatement();
			rs = state.executeUpdate("delete from employee where id in(" + ids + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, null);
		}
		return rs > 0;
	}

	public String sqlConcat(Employee e, int type) {
		String condition1 = " count(1)";
		String condition2 = " *";
		String condition = type == 1 ? condition1 : condition2;

		String sql = "select " + condition + " from employee where 1=1";
		if (e.getName().length() > 0) {
			sql += " and name like '%" + e.getName() + "%'";
		}
		if (e.getSex().length() > 0) {
			sql += " and sex='" + e.getSex() + "'";
		}
		if (e.getAge()!=null && e.getAge() != 0) {
			sql += " and age=" + e.getAge();
		}
		if (e.getdId()!=null && e.getdId() != 0) {
			sql += " and d_id=" + e.getdId();
		}
		return sql;
	}

	public int count() {
		// TODO Auto-generated method stub
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int count = 0;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select count(1) as count from employee");
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return count;
	}

	public int searchCount(Employee emp) {
		// TODO Auto-generated method stub

		int rows = 0;
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(emp, 1);
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return rows;
	}

}
