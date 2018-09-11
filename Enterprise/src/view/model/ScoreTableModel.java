package view.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import entity.Score;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:04
 * @Description
 *
 */

public class ScoreTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Score> list;
	private Set<Score> set = new HashSet<>();
	private String[] columns = { "id", "姓名", "部门", "项目", "成绩", "等级" };

	public ScoreTableModel(List<Score> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	}

	public String[] getColumns() {
		return columns;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 4 && list.get(rowIndex).getPro().getName() != null) {
			return true;
		}
		return false;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String in = (String) aValue;
//		boolean flag = false;
		if (!in.equals("")) {

			list.get(rowIndex).setValue(Integer.parseInt((String) aValue));
			list.get(rowIndex).setGrade(setGrade(Integer.parseInt((String) aValue)));
			set.add(list.get(rowIndex));
		}

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return list.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return list.get(rowIndex).getEmp().getName();
		} else if (columnIndex == 2) {
			return list.get(rowIndex).getEmp().getDep().getName();
		} else if (columnIndex == 3) {
			return list.get(rowIndex).getPro().getName();
		} else if (columnIndex == 4) {
			return list.get(rowIndex).getValue();
		} else if (columnIndex == 5) {
			return list.get(rowIndex).getGrade();
		} else {
			return "";
		}
	}

	public String getColumnName(int column) {

		return columns[column];
	}

	public void setList(List<Score> list) {
		// TODO Auto-generated method stub
		this.list = list;
	}

	public String setGrade(int value) {
		switch (value / 10) {
		case 10:
			return "A*";
		case 9:
			return "A*";
		case 8:
			return "B*";
		case 7:
			return "C*";
		case 6:
			return "D*";

		default:
			return "E*";
		}
	}

	public Set<Score> getSet() {
		return set;
	}

}
