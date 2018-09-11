package view.model;

import javax.swing.table.AbstractTableModel;

import entity.ProGroup;

/**
 * @Author ChenHao
 * @Date 2018-08-05 10:18
 * @Description
 *
 */

public class ProjectDetailsModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProGroup prog;
	private String[] columns = { "id", "部门名", "当前/人数/总人数" };

	public ProjectDetailsModel(ProGroup prog) {
		this.prog = prog;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return prog.getDeps().size();
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
			return prog.getDeps().get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return prog.getDeps().get(rowIndex).getName();
		} else if (columnIndex == 2) {
			return prog.getDeps().get(rowIndex).getEmpList().size() + "/"
					+ prog.getMap().get(prog.getDeps().get(rowIndex).getId()) + "/"
					+ prog.getDeps().get(rowIndex).getEmpCount();
		} else {
			return "";
		}
	}

	public String getColumnName(int column) {

		return columns[column];
	}

	public void setProg(ProGroup prog) {
		this.prog = prog;
	}

}
