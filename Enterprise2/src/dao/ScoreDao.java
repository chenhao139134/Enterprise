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
import entity.Score;
import entity.Project;
import util.DB;
import util.PageInfo;

/**
 * @Author ChenHao
 * @Date 2018-07-30 14:00
 * @Description
 *
 */

public class ScoreDao {
	DB db = new DB();

	public List<Score> search(PageInfo page) {
		List<Score> scos = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM v_emp_sc" + " limit " + page.getIndex() + "," + page.getLimit();
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Score sco = new Score();
				Employee emp = new Employee();
				Department dep = new Department();
				Project pro = new Project();

				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setValue(rs.getInt("value"));
				sco.setGrade(rs.getString("grade"));
				sco.setId(rs.getInt("s_id"));
				scos.add(sco);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return scos;
	}

	public List<Score> search(PageInfo pageInfo, Score s) {
		List<Score> scos = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(s, 2);
		sql += " limit " + pageInfo.getIndex() + "," + pageInfo.getLimit();
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Score sco = new Score();
				Employee emp = new Employee();
				Department dep = new Department();
				Project pro = new Project();

				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);
				sco.setValue(rs.getInt("value"));
				sco.setGrade(rs.getString("grade"));
				sco.setId(rs.getInt("s_id"));
				scos.add(sco);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return scos;
	}

	public boolean add(int eid, int pid, int value) {
		boolean flag = false;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		try {

			String sql = "insert into score(e_id,p_id,value)values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, eid);
			ps.setInt(2, pid);
			ps.setInt(3, value);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, ps, null);
		}
		return flag;
	}

	public boolean update(int id, int value) {
		boolean flag = false;
		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		try {

			String sql = "update score set value=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, value);
			pstat.setInt(2, id);
			int rs = pstat.executeUpdate();
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

	public String sqlConcat(Score sco, int type) {
		String condition1 = " count(1)";
		String condition2 = " *";
		String condition = type == 1 ? condition1 : condition2;

		String sql = "select " + condition + " from v_emp_sc where 1=1";
		if (sco.getEmp().getName() != null && sco.getEmp().getName().length() > 0) {
			sql += " and e_name like '%" + sco.getEmp().getName() + "%'";
		}
		if (sco.getEmp().getDep().getId() != -1) {
			sql += " and d_id=" + sco.getEmp().getDep().getId();
		}
		if (sco.getPro().getId() != -1) {
			sql += " and p_id=" + sco.getPro().getId();
		}
		if (sco.getGrade() != null && sco.getGrade().length() > 0) {
			sql += " and grade='" + sco.getGrade() + "'";
		}
		return sql;
	}

	public int count() {
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int count = 0;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select count(1) as count from v_emp_sc");
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
		}

		return count;
	}

	public int searchCount(Score sco) {

		int rows = 0;
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(sco, 1);
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

	public Map<String, Object> search() {

		Map<String, Object> map = new HashMap<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql1 = "select d_id,d_name from v_emp_sco where d_id is not null group by d_id";
		String sql2 = "select p_id,p_name from v_emp_sco where p_id is not null group by p_id";
		String sql3 = "select grade from score v_emp_sco";
		try {
			List<Department> dep = new ArrayList<>();
			List<Project> pro = new ArrayList<>();
			List<String> grade = new ArrayList<>();
			state = conn.createStatement();
			rs = state.executeQuery(sql1);
			while (rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt(1));
				d.setName(rs.getString(2));
				dep.add(d);
			}
			rs.close();
			rs = state.executeQuery(sql2);
			while (rs.next()) {
				Project d = new Project();
				d.setId(rs.getInt(1));
				d.setName(rs.getString(2));
				pro.add(d);
			}
			rs.close();
			rs = state.executeQuery(sql3);
			while (rs.next()) {
				grade.add(rs.getString(1));
			}
			map.put("dep", dep);
			map.put("pro", pro);
			map.put("grade", grade);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return map;
	}

}
