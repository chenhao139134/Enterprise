package view.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Employee;

/**
 * @Author ChenHao
 * @Date 2018-08-05 10:18
 * @Description
 *
 */

public class DepEmpDetailsModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Employee> list = new ArrayList<>();
	private String[] columns = { "id", "姓名", "性别", "年龄", "所属项目" };

	public DepEmpDetailsModel(List<Employee> list) {
		this.list = list;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
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
			return list.get(rowIndex).getSex();
		} else if (columnIndex == 3) {
			return list.get(rowIndex).getAge();
		} else if (columnIndex == 4) {
			return list.get(rowIndex).getPro().getName();
		} else {
			return "";
		}
	}

	public String getColumnName(int column) {

		return columns[column];
	}

	public void setList(List<Employee> list) {
		// TODO Auto-generated method stub
		this.list = list;
	}

}
