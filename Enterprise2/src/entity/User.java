package entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import util.MD5;

public class User {
	private String username;
	private String password;
	private String date;
	
	public User() {
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
