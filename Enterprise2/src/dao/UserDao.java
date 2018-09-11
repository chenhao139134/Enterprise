package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import util.DB;
import util.MD5;

/**
 * @Author ChenHao
 * @Date 2018-08-21 11:32
 * @Description
 *
 */

public class UserDao {

	DB db = new DB();

	public Map<String, String> search(User user) {
		Map<String, String> map = new HashMap<>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		conn = db.getConnection();
		try {
			String sql = "select * from user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			rs = ps.executeQuery();
			
			if (rs.next()) {
				User u = new User();
				u.setDate(rs.getString("date"));
				if (MD5.MD5(user.getPassword() + u.getDate()).equals(rs.getString("password"))) {
					map.put("type", "true");
				} else {
					map.put("type", "false");
					map.put("msg", "用户名或密码错误！");
				}
			}else {
				map.put("type", "false");
				map.put("msg", "用户 " + user.getUsername() + " 不存在");
				return map;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, ps, rs);
		}
		return map;
	}

	public int add(User user) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection conn = null;
		conn = db.getConnection();
		ResultSet rs = null;
		int id = -1;
		try {
			String sql = "insert into user(username,password,date) values(?,?,?)";
			ps = conn.prepareStatement(sql,1);
			ps.setString(1, user.getUsername());
			ps.setString(2, MD5.MD5(user.getPassword() + MD5.MD5(user.getDate())));
			ps.setString(3, MD5.MD5(user.getDate()));
			id = ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeAll(conn, ps, rs);
		}
		return id;
	}
}
