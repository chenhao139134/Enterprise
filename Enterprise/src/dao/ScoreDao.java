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
import entity.Score;
import util.DB;

/**
 * @Author ChenHao
 * @Date 2018-07-30 14:00
 * @Description
 *
 */

public class ScoreDao {
	DB db = new DB();
	public List<Score> search() {
		List<Score> scos = new ArrayList<>();
		
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from v_emp_sco";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Score sco = new Score();

				sco.setId(rs.getInt("s_id"));
				sco.setGrade(rs.getString("grade"));
				sco.setValue((Integer) rs.getObject("value"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));
				emp.setdId(rs.getInt("d_id"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				sco.setEmp(emp);
				sco.setPro(pro);
				scos.add(sco);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return scos;
	}

	public boolean insert(Score sco) {
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		int rs = 0;
		try {
			String sql = "insert into score(e_id,p_id,value)values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sco.getEmp().getId());
			ps.setInt(2, sco.getPro().getId());
			ps.setInt(3, sco.getValue());
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rs > 0;
	}

	public boolean update(Score sco) {
		// TODO Auto-generated method stub
		boolean flag = false;
		PreparedStatement pstat = null;
		Connection conn = db.getConnection();
		try {
			String sql = "update score set value=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sco.getValue());
			pstat.setInt(2, sco.getId());
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

	public List<Score> searchCondition(Score ee) {
		List<Score> scos = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		String sql = sqlConcat(ee);
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Score sco = new Score();
				sco.setId(rs.getInt("s_id"));
				sco.setGrade(rs.getString("grade"));
				sco.setValue((Integer) rs.getObject("value"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));
				emp.setdId(rs.getInt("d_id"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				sco.setEmp(emp);
				sco.setPro(pro);
				scos.add(sco);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}
		return scos;
	}

	public String sqlConcat(Score sco) {

		String sql = "select * from v_emp_sco where 1=1";
		if (sco.getEmp().getName().length() > 0) {
			sql += " and e_name like '%" + sco.getEmp().getName() + "%'";
		}
		if (sco.getPro().getName().length() > 0) {
			sql += " and p_name='" + sco.getPro().getName() + "'";
		}
		if (sco.getEmp().getDep().getName().length() > 0) {
			sql += " and d_name='" + sco.getEmp().getDep().getName() + "'";
		}
		if (sco.getGrade().length() > 0) {
			sql += " and grade='" + sco.getGrade() + "'";
		}
		return sql;
	}


}
