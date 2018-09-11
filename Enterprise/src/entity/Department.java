package entity;

import java.util.List;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:04
 * @Description
 *
 */

public class Department {
	private int id;
	private String name;
	private int empCount;
	private List<Project> proList;
	private List<Employee> empList;
	public Department() {
	}

	public List<Project> getProList() {
		return proList;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public void setProList(List<Project> proList) {
		this.proList = proList;
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
