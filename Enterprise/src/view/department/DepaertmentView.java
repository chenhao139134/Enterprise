package view.department;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import dao.DepartmentDao;
import entity.Department;
import util.CallBack;
import util.TableUtil;
import view.model.DepartmentTableModel;

/**
 * @Author ChenHao
 * @Date 2018-08-03 18:30
 * @Description
 *
 */

public class DepaertmentView {

	List<Department> list = new ArrayList<>();
	JTable table;
	DepartmentTableModel model;
	DepartmentDao depDao = new DepartmentDao();
	JFrame frame;
	private static DepaertmentView departmentView = null;

	public static DepaertmentView getInstance() {
		if (departmentView == null) {
			synchronized (DepaertmentView.class) {
				if (departmentView == null) {
					departmentView = new DepaertmentView();
					departmentView.init();
				}
			}
		} else {
			departmentView.frame.dispose();
			departmentView.init();
			departmentView.frame.setVisible(true);
		}
		return departmentView;
	}

	private DepaertmentView() {

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Point p = e.getPoint();
			int row = table.rowAtPoint(p);
			int column = table.columnAtPoint(p);
			// 从而获得双击时位于的单元格
			System.out.println(row + " " + column);
		}
	}

	public void init() {
		frame = new JFrame("部门管理");
		frame.setSize(750, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		// 流布局，横向排列
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(boxLayout);
		// 左部分，放置各种按钮文本框
		JPanel panel1 = new JPanel();
		// panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		BoxLayout panel1Layout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		panel1.setLayout(panel1Layout);

		// 右部分，放置信息
		JPanel panel2 = new JPanel();
		BoxLayout panel2Layout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
		panel2.setLayout(panel2Layout);
		// panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		mainPanel.add(panel1);
		mainPanel.add(panel2);

		JPanel panel11 = new JPanel();
		panel11.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

		JLabel nameLabel = new JLabel();
		nameLabel.setText("部门名		");
		panel11.add(nameLabel);
		JTextField nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(80, 30));
		panel11.add(nameText);

		JPanel panel12 = new JPanel();
		panel12.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		JLabel numLabel = new JLabel();
		numLabel.setText("人数");
		panel12.add(numLabel);
		JTextField numText = new JTextField();
		numText.setPreferredSize(new Dimension(80, 30));
		panel12.add(numText);

		JPanel panel13 = new JPanel();
		panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton searchBtn = new JButton();
		searchBtn.setText("搜索");
		searchBtn.setPreferredSize(new Dimension(80, 30));
		panel13.add(searchBtn);

		JPanel panel14 = new JPanel();
		panel14.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton addBtn = new JButton();
		addBtn.setText("添加");
		addBtn.setPreferredSize(new Dimension(80, 30));
		panel14.add(addBtn);

		JPanel panel15 = new JPanel();
		panel15.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton modifyBtn = new JButton();
		modifyBtn.setText("修改");
		modifyBtn.setPreferredSize(new Dimension(80, 30));
		panel15.add(modifyBtn);

		JPanel panel16 = new JPanel();
		panel16.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton deleteBtn = new JButton();
		deleteBtn.setText("删除");
		deleteBtn.setPreferredSize(new Dimension(80, 30));
		panel16.add(deleteBtn);

		panel1.add(panel11);
		panel1.add(panel12);
		panel1.add(panel13);
		panel1.add(panel14);
		panel1.add(panel15);
		panel1.add(panel16);

		list = depDao.search();
		model = new DepartmentTableModel(list);
		table = new JTable();
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(450, 300));
		JPanel panel21 = new JPanel();
		panel21.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		panel21.add(scroll);

		JPanel panel22 = new JPanel();
		panel22.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton depProBtn = new JButton();
		depProBtn.setText("部门项目管理");
		depProBtn.setPreferredSize(new Dimension(120, 30));
		panel22.add(depProBtn);
		panel2.add(panel21);
		panel2.add(panel22);

		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String depName = nameText.getText();
				int num = Integer.parseInt((numText.getText() + "0")) / 10;
				Department dep = new Department(depName, num);
				if (depName.length() > 0 || num != 0) {
					list = depDao.serchByCondition(dep);
					refreshTable(list);
				} else {
					refreshTable();
				}
			}
		});

		depProBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row != -1) {
					int preId = (int) table.getValueAt(row, 0);
					String depName = (String) table.getValueAt(row, 1);
					new DepProDetailsView(depName, preId, new CallBack() {

						@Override
						public void call() {
							// TODO Auto-generated method stub
							refreshTable();
						}
					}).init();
				}

			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int preId = (int) table.getValueAt(row, 0);
				String depName = (String) table.getValueAt(row, 1);
				if (e.getClickCount() == 2) {
					if (table.getSelectedColumn() == 2) {// 点击几次，这里是双击部门人数，弹出人数的详情
						new DepEmpDetailsView(depName, preId).init();
					} else {// 点击几次，这里双击不是部门人数，弹出项目详情
						new DepProDetailsView(depName, preId, new CallBack() {
							public void call() {
								// TODO Auto-generated method stub
								refreshTable();
							}
						}).init();
					}
				}
			}
		});

		// 添加功能
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddDepartmentView.getInstance(new CallBack() {

					public void call() {
						refreshTable();
					}
				});
			}
		});

		modifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "请选中修改的部门！");
				} else {
					int id = (int) table.getValueAt(row, 0);
					ModifyDepartmentView.getInstance(id, new CallBack() {

						@Override
						public void call() {
							// TODO Auto-generated method stub
							refreshTable();
						}
					});
				}
			}
		});

		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				delete();
			}
		});

		frame.setVisible(true);
	}

	public void delete() {
		int[] rows = table.getSelectedRows();
		boolean flag = false;
		if (rows.length > 0) {
			int[] ids = new int[rows.length];
			for (int i = rows.length - 1; i >= 0; i--) {
				ids[i] = (int) table.getValueAt(rows[i], 0);
			}
			for (int i = ids.length - 1; i >= 0; i--) {
				String depName = depDao.selectById(ids[i]).getName();
				int isDelete = JOptionPane.showConfirmDialog(null, "确定要删除 " + depName + " 吗？", "确认",
						JOptionPane.YES_NO_OPTION);

				if (isDelete == 0) {
					flag = depDao.delete(ids[i]);
				}
				if (flag) {
					System.out.println("刪除" + depName + "成功!");
					refreshTable();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "请选中要删除的员工");
		}
	}

	public void refreshTable() {
		list = depDao.search();
		model.setList(list);
		table.updateUI();

	}

	public void refreshTable(List<Department> list) {
		model.setList(list);
		table.updateUI();

	}

	public static void main(String[] args) {
		DepaertmentView de = new DepaertmentView();
		de.init();
	}
}
