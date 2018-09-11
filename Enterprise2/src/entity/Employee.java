package entity;

import java.io.Serializable;

/**
 * @Author ChenHao
 * @Date 2018-07-24 18:46
 * @Description
 *
 */

public class Employee implements Serializable{
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	private Integer dId;
	private Integer pId;
	private String img;
	private Department dep;
	private Project pro;
	
	
	public Integer getpId() {
		return pId;
	}


	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}

	public Employee() {
		this.dep = new Department();
		this.dep.setName("");
	}


	public Employee(String name, String sex, Integer age,Integer dId) {
		
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.dId = dId;
	}
	

	public Employee(Integer id, String name, String sex, Integer age, Integer dId, Department dep) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.dep = dep;
		this.dId = dId;
	}


	public Integer getdId() {
		return dId;
	}


	public void setdId(Integer dId) {
		this.dId = dId;
	}


	public Project getPro() {
		return pro;
	}


	public void setPro(Project pro) {
		this.pro = pro;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public String toString() {
		return "姓名：" + this.getName() + "\t性别：" + this.getSex() + "\t年龄：" + this.getAge() + "\t图片" + this.getImg();
	}
}
