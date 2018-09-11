package view.employee;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import util.CallBack;
import util.TableUtil;
import view.model.EmployeeTableModel;

/**
 * @Author ChenHao
 * @Date 2018-07-27 15:18
 * @Description
 *
 */

public class EmployeeView {

	List<Employee> list = new ArrayList<>();
	List<Department> depList = new ArrayList<>();
	EmployeeDao empDao = new EmployeeDao();
	DepartmentDao depDao = new DepartmentDao();
	JTable table;
	EmployeeTableModel model;

	JFrame frame;
	private static EmployeeView employeeView = null;

	public static EmployeeView getInstance() {
		if (employeeView == null) {
			synchronized (EmployeeView.class) {
				if (employeeView == null) {
					employeeView = new EmployeeView();
					employeeView.init();

				}
			}
		} else {
			employeeView.frame.dispose();
			employeeView.init();
			employeeView.frame.setVisible(true);

		}
		return employeeView;
	}

	private EmployeeView() {

	}

	public void init() {
		frame = new JFrame("员工信息管理");
		frame.setSize(650, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		// 整体布局，垂直分布
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);
		// 第一部分布局
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		// 第二部分布局
		JPanel panel2 = new JPanel();
		// 第三部分布局
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 10));

		// 向主窗口添加三块布局
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);

		// panel1内容部分
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);
		JTextField nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(80, 30));
		panel1.add(nameText);

		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel1.add(sexLabel);
		JComboBox<String> sexBox = new JComboBox<>();
		sexBox.addItem("");
		sexBox.addItem("男");
		sexBox.addItem("女");

		panel1.add(sexBox);

		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel1.add(ageLabel);
		JTextField ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(80, 30));
		panel1.add(ageText);

		depList = depDao.search();
		JLabel depLabel = new JLabel();
		depLabel.setText("部门");
		panel1.add(depLabel);
		JComboBox<String> depBox = new JComboBox<>();
		depBox.addItem("");
		for (Department d : depList) {
			depBox.addItem(d.getName());
		}

		panel1.add(depBox);

		JButton searchBtn = new JButton();
		searchBtn.setText("搜索");
		searchBtn.setPreferredSize(new Dimension(80, 30));
		panel1.add(searchBtn);

		// panel2内容部分
		empDao = new EmployeeDao();
		list = empDao.search();

		model = new EmployeeTableModel(list);
		table = new JTable();
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(550, 300));
		panel2.add(scroll);

		// panel3内容部分
		JButton addBtn = new JButton();
		addBtn.setText("添加");
		addBtn.setPreferredSize(new Dimension(80, 30));
		panel3.add(addBtn);

		JButton modifyBtn = new JButton();
		modifyBtn.setText("修改");
		modifyBtn.setPreferredSize(new Dimension(80, 30));
		panel3.add(modifyBtn);

		JButton deleteBtn = new JButton();
		deleteBtn.setText("删除");
		deleteBtn.setPreferredSize(new Dimension(80, 30));
		panel3.add(deleteBtn);
		frame.setVisible(true);

		/*
		 * 删除员工，先从主面板选中要删除的员工，点击删除按钮 获取选中员工的行号，从emps中获取对象，如果不为空则删除
		 */
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				delete();
			}
		});

		/*
		 * 修改员工，首先从主面板选中要修改的员工，点击修改，获取要修改的员工的行号 从emps中获取选中员工的对象，将对象中属性放在修改面板的对应位置
		 * 点击修改按钮，判断各项是否有为空项，若有则弹窗提出警告 没有则判断与原对象是否不同（修改了对象中属性的值）
		 * 若发生修改则将修改后的对象放入emps，保存，然后重新加载主界面
		 */
		modifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();

				if (row == -1) {
					JOptionPane.showMessageDialog(null, "请选择修改的员工");
				} else {
					int id = (int) table.getValueAt(row, 0);
					ModifyEmployeeView.getInstance(id, new CallBack() {

						@Override
						public void call() {
							// TODO Auto-generated method stub
							refreshTable();
						}
					});
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					int row = table.getSelectedRow();

					if (row == -1) {
						JOptionPane.showMessageDialog(null, "请选择修改的员工");
					} else {
						int id = (int) table.getValueAt(row, 0);
						ModifyEmployeeView.getInstance(id, new CallBack() {

							@Override
							public void call() {
								// TODO Auto-generated method stub
								refreshTable();
							}
						});
					}
				}
			}
		});

		/*
		 * 添加功能
		 */
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					AddEmployeeView.getInstance(new CallBack() {

						@Override
						public void call() {
							// TODO Auto-generated method stub
							refreshTable();

						}
					});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/*
		 * 搜索功能
		 */
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					String name = null;
					String sex = null;
					int age = 0;
					int index = 0;
					Department dep = new Department();
					name = nameText.getText();
					sex = (String) sexBox.getSelectedItem();
					age = Integer.parseInt(ageText.getText() + "0") / 10;
					index = depBox.getSelectedIndex();

					if (index > 0) {
						dep = depList.get(index - 1);
					}
					Employee emp = new Employee();
					emp.setName(name);
					emp.setAge(age);
					emp.setDep(dep);
					emp.setSex(sex);
					emp.setdId(dep.getId());

					List<Employee> emps = empDao.searchCondition(emp);

					refreshTable(emps);

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
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
				String empName = empDao.selectById(ids[i]).getName();
				int isDelete = JOptionPane.showConfirmDialog(null, "确定要删除员工 " + empName + " 吗？", "确认",
						JOptionPane.YES_NO_OPTION);

				if (isDelete == 0) {
					flag = empDao.delete(ids[i]);
				}
				if (flag) {
					System.out.println("刪除" + empName + "成功!");
					refreshTable();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "请选中要删除的员工");
		}
	}

	public void refreshTable() {
		list = empDao.search();
		model.setList(list);
		table.updateUI();

	}

	protected void refreshTable(List<Employee> emps) {
		// TODO Auto-generated method stub
		model.setList(emps);
		table.updateUI();
	}

	public static void main(String[] args) {
		EmployeeView ev = new EmployeeView();
		ev.init();
	}
}