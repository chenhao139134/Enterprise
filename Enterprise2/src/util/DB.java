package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:17
 * @Description
 *
 */

public class DB {

	public Connection getConnection() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/company?characterEncoding=utf-8&&useSSL=false";
		String user = "root";
		String password = "123456";
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeAll(Connection conn, Statement state, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (state != null) {
				state.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
	}

}
