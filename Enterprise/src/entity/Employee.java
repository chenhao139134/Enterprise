package entity;

/**
 * @Author ChenHao
 * @Date 2018-07-24 18:46
 * @Description
 *
 */

public class Employee {
	private int id;
	private String name;
	private String sex;
	private int age;
	private int dId;
	private Department dep;
	private Project pro;
	
	
	public Employee() {
		
	}


	public Employee(String name, String sex, int age,int dId) {
		
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.dId = dId;
	}
	

	public Employee(int id, String name, String sex, int age, int dId, Department dep) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.dep = dep;
		this.dId = dId;
	}


	public int getdId() {
		return dId;
	}


	public void setdId(int dId) {
		this.dId = dId;
	}


	public Project getPro() {
		return pro;
	}


	public void setPro(Project pro) {
		this.pro = pro;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public String toString() {
		return "姓名：" + this.getName() + "\t性别：" + this.getSex() + "\t年龄：" + this.getAge();
	}
}
