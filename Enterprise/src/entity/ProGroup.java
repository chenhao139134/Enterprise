package entity;

import java.util.List;
import java.util.Map;

/**
 * @Author ChenHao
 * @Date 2018-08-11 11:24
 * @Description
 *
 */

public class ProGroup {
	private Project pro;
	private List<Department> deps;
	private Map<Integer, Integer> map;

	public Project getPro() {
		return pro;
	}

	public void setPro(Project pro) {
		this.pro = pro;
	}

	public List<Department> getDeps() {
		return deps;
	}

	public void setDeps(List<Department> deps) {
		this.deps = deps;
	}

	public Map<Integer, Integer> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Integer> map) {
		this.map = map;
	}

}
