package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import entity.Employee;
import entity.Project;
import util.DB;

/**
 * @Author ChenHao
 * @Date 2018-08-11 11:10
 * @Description
 *
 */

public class DepDetailsDao {
	DB db = new DB();

	// 删除部门中的项目，从关系表中删除
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

	public Map<Project, Integer> searchPro(int dId) {
		Map<Project, Integer> map = new HashMap<>();
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from v_dep_pro where d_id=" + dId);
			while (rs.next()) {
				Project p = new Project();
				int count  = rs.getInt("count");
				p.setId(rs.getInt("p_id"));
				p.setName(rs.getString("p_name"));
				
				map.put(p, count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, state, rs);
		}

		return map;
	}
	
	public boolean saveEmpPro(Employee emp) {
		Connection conn = db.getConnection();
		Statement state = null;
		int rs = 0;
		try {
			state = conn.createStatement();
			if(!emp.getPro().getName().equals("")) {
				rs = state.executeUpdate("update employee set p_id=" + emp.getPro().getId() + " where id=" + emp.getId());
			}else {
				rs = state.executeUpdate("update employee set p_id=null where id=" + emp.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rs > 0;
	}

	public int nowCount(int i, int j) {
		// TODO Auto-generated method stub
		
		Connection conn = db.getConnection();
		Statement state = null;
		ResultSet rs = null;
		int num = 0;
		try {
			String sql = "select count(id) as num from employee where p_id=" + i + " and d_id=" + j;
			System.out.println(sql);
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next()) {
				num = rs.getInt("num");
			}
			System.out.println(num + "/*/*/*");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return num;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
