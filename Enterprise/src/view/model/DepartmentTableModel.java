package view.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Department;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:04
 * @Description
 *
 */

public class DepartmentTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Department> list;
	private String[] columns = { "id", "部门名", "人数", "项目数" };

	public DepartmentTableModel(List<Department> list) {
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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return list.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return list.get(rowIndex).getName();
		} else if (columnIndex == 2) {
			return list.get(rowIndex).getEmpCount();
		} else if(columnIndex == 3) {
			return list.get(rowIndex).getProList().size();
		} else {
			return "";
		}
	}

	public String getColumnName(int column) {

		return columns[column];
	}

	public void setList(List<Department> list) {
		// TODO Auto-generated method stub
		this.list = list;
	}
}
