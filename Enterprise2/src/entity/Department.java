package entity;

import java.io.Serializable;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:04
 * @Description
 *
 */

public class Department implements Serializable{
	private int id;
	private String name;
	private int empCount;
	private int proCount;
	public Department() {
	}

	public Department(String name, int emp_count) {
		this.name = name;
		this.empCount = emp_count;
	}

	public Department(int id, String name, int emp_count) {
		super();
		this.id = id;
		this.name = name;
		this.empCount = emp_count;
	}

	public int getProCount() {
		return proCount;
	}

	public void setProCount(int proCount) {
		this.proCount = proCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmpCount() {
		return empCount;
	}

	public void setEmpCount(int emp_count) {
		this.empCount = emp_count;
	}

}
