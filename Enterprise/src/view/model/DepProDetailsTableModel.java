package view.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import entity.Project;

/**
 * @Author ChenHao
 * @Date 2018-08-05 10:18
 * @Description
 *
 */

public class DepProDetailsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Project> list = new ArrayList<>();
	private List<Integer> count = new ArrayList<>();
	private String[] columns = { "id", "项目名", "需要人数"};

	
	
	public DepProDetailsTableModel(Map<Project, Integer> map) {
		for(Project p : map.keySet()) {
			list.add(p);
			count.add(map.get(p));
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public String[] getColumns() {
		return columns;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return list.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return list.get(rowIndex).getName();
		} else if(columnIndex == 2) {
			return count.get(rowIndex);
		}
		else {
			return "";
		}
	}

	public String getColumnName(int column) {

		return columns[column];
	}

	public void setMap(Map<Project, Integer> map) {
		for(Project p : map.keySet()) {
			list.add(p);
			count.add(map.get(p));
		}
	}
	
	
}
