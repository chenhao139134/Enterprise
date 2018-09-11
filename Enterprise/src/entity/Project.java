package entity;

import java.util.List;
import java.util.Map;

/**
 * @Author ChenHao
 * @Date 2018-08-07 16:21
 * @Description
 *
 */

public class Project {
	private int id;
	private String name;
	private List<Department> deps;
	private Map<Department, Integer> map;
	public Project() {
	}

	public Project(String name) {
		this.name = name;
	}

	public Project(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Map<Department, Integer> getMap() {
		return map;
	}

	public void setMap(Map<Department, Integer> map) {
		this.map = map;
	}

	public int getId() {
		return id;
	}

	public List<Department> getDeps() {
		return deps;
	}

	public void setDeps(List<Department> deps) {
		this.deps = deps;
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

}
