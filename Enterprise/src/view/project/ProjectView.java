package view.project;

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
import dao.ProjectDao;
import entity.Project;
import util.CallBack;
import util.TableUtil;
import view.model.ProjectTableModel;

/**
 * @Author ChenHao
 * @Date 2018-08-03 18:30
 * @Description
 *
 */

public class ProjectView {

	List<Project> list = new ArrayList<>();
	JTable table;
	ProjectTableModel model;
	ProjectDao proDao = new ProjectDao();
	JFrame frame;
	private static ProjectView projectView = null;

	public static ProjectView getInstance() {
		if (projectView == null) {
			synchronized (ProjectView.class) {
				if (projectView == null) {
					projectView = new ProjectView();
					projectView.init();
				}
			}
		} else {
			projectView.frame.dispose();
			projectView.init();
			projectView.frame.setVisible(true);
		}
		return projectView;
	}

	private ProjectView() {

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
		frame = new JFrame("项目管理");
		frame.setSize(800, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		// 流布局，横向排列
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(boxLayout);
		// 左部分，放置各种按钮文本框
		JPanel panel1 = new JPanel();
		//panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		BoxLayout panel1Layout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		panel1.setLayout(panel1Layout);

		// 右部分，放置信息
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		mainPanel.add(panel1);
		mainPanel.add(panel2);

		JPanel panel11 = new JPanel();
		panel11.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("项目名		");
		panel11.add(nameLabel);
		JTextField nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(80, 30));
		panel11.add(nameText);

		JPanel panel12 = new JPanel();
		panel12.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton searchBtn = new JButton();
		searchBtn.setText("搜索");
		searchBtn.setPreferredSize(new Dimension(80, 30));
		panel12.add(searchBtn);

		JPanel panel13 = new JPanel();
		panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton addBtn = new JButton();
		addBtn.setText("添加");
		addBtn.setPreferredSize(new Dimension(80, 30));
		panel13.add(addBtn);

		JPanel panel14 = new JPanel();
		panel14.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton modifyBtn = new JButton();
		modifyBtn.setText("修改");
		modifyBtn.setPreferredSize(new Dimension(80, 30));
		panel14.add(modifyBtn);

		JPanel panel15 = new JPanel();
		panel15.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JButton deleteBtn = new JButton();
		deleteBtn.setText("删除");
		deleteBtn.setPreferredSize(new Dimension(80, 30));
		panel15.add(deleteBtn);

		panel1.add(panel11);
		panel1.add(panel12);
		panel1.add(panel13);
		panel1.add(panel14);
		panel1.add(panel15);

		list = proDao.search();
		model = new ProjectTableModel(list);
		table = new JTable();
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(550, 300));
		panel2.add(scroll);

		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String proName = nameText.getText();
				
				Project pro = new Project(proName);
				if(proName.length() > 0) {
					list = proDao.serchByCondition(pro);
					refreshTable(list);
				}else {
					refreshTable();
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
					int row = table.getSelectedRow();
					int preId = (int) table.getValueAt(row, 0);
					String proName = (String) table.getValueAt(row, 1);
					new ProjectDetailsView(proName, preId, new CallBack() {
						
						@Override
						public void call() {
							// TODO Auto-generated method stub
							refreshTable();
						}
					}).init();
				}
			}
		});

		// 添加功能
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddProjectView.getInstance(new CallBack() {

					@Override
					public void call() {
						// TODO Auto-generated method stub
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
					JOptionPane.showMessageDialog(null, "请选中修改的项目！");
				} else {
					int id = (int) table.getValueAt(row, 0);
					ModifyProjectView.getInstance(id, new CallBack() {

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
				String proName = proDao.selectById(ids[i]).getName();
				int isDelete = JOptionPane.showConfirmDialog(null, "确定要删除 " + proName + " 吗？", "确认",
						JOptionPane.YES_NO_OPTION);

				if (isDelete == 0) {
					flag = proDao.delete(ids[i]);
				}
				if (flag) {
					System.out.println("刪除" + proName + "成功!");
					refreshTable();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "请选中要删除的员工");
		}
	}

	public void refreshTable() {
		list = proDao.search();
		model.setList(list);
		table.updateUI();

	}
	
	public void refreshTable(List<Project> list) {
		model.setList(list);
		table.updateUI();

	}


	public static void main(String[] args) {
		ProjectView de = new ProjectView();
		de.init();
	}
}
