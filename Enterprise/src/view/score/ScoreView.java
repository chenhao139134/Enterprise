package view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import dao.DepartmentDao;
import dao.ProjectDao;
import dao.ScoreDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.TableUtil;
import view.model.ScoreTableModel;

/**
 * @Author ChenHao
 * @Date 2018-07-27 15:180
 * @Description
 *
 */

public class ScoreView {

	List<Score> list = new ArrayList<>();
	List<Department> depList = new ArrayList<>();
	List<Project> proList = new ArrayList<>();

	ScoreDao scoDao = new ScoreDao();
	DepartmentDao depDao = new DepartmentDao();
	ProjectDao proDao = new ProjectDao();
	JTable table;
	ScoreTableModel model;
	JFrame frame;

	private static ScoreView scoloyeeView = null;

	public static ScoreView getInstance() {
		if (scoloyeeView == null) {
			synchronized (ScoreView.class) {
				if (scoloyeeView == null) {
					scoloyeeView = new ScoreView();
					scoloyeeView.init();
				}
			}
		} else {
			scoloyeeView.frame.dispose();
			scoloyeeView.init();
			scoloyeeView.frame.setVisible(true);
		}
		return scoloyeeView;
	}

	private ScoreView() {

	}

	public void init() {
		frame = new JFrame("员工绩效管理");
		frame.setSize(800, 500);
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

		proList = proDao.search();
		JLabel proLabel = new JLabel();
		proLabel.setText("项目");
		panel1.add(proLabel);
		JComboBox<String> proBox = new JComboBox<>();
		proBox.addItem("");
		for (Project p : proList) {
			proBox.addItem(p.getName());
		}
		panel1.add(proBox);

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

		JLabel gradeLabel = new JLabel();
		gradeLabel.setText("等级");
		panel1.add(gradeLabel);
		JComboBox<String> gradeBox = new JComboBox<>();
		gradeBox.addItem("");
		gradeBox.addItem("A");
		gradeBox.addItem("B");
		gradeBox.addItem("C");
		gradeBox.addItem("D");
		gradeBox.addItem("E");
		panel1.add(gradeBox);

		JButton searchBtn = new JButton();
		searchBtn.setText("搜索");
		searchBtn.setPreferredSize(new Dimension(80, 30));
		panel1.add(searchBtn);

		// panel2内容部分
		scoDao = new ScoreDao();
		list = scoDao.search();
		model = new ScoreTableModel(list);
		table = new JTable();
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(700, 300));
		panel2.add(scroll);

		JButton saveBtn = new JButton();
		saveBtn.setText("保存");
		saveBtn.setPreferredSize(new Dimension(80, 30));
		panel3.add(saveBtn);
		frame.setVisible(true);

		/*
		 * 保存
		 */
		saveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Set<Score> set = model.getSet();
				for (Score s : set) {
					if (s.getId() == 0) {
						scoDao.insert(s);
					} else {
						scoDao.update(s);
					}
				}
				refreshTable();
			}
		});

		/*
		 * 搜索功能
		 */
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Score sco = new Score();
					sco.setGrade("");
					Employee emp = new Employee();
					emp.setName("");
					Department dep = new Department();
					dep.setName("");
					Project pro = new Project();
					pro.setName("");
					String name = nameText.getText();
					emp.setName(name);
					int proIndex = proBox.getSelectedIndex();
					int depIndex = depBox.getSelectedIndex();
					if (proIndex > 0) {
						pro = proList.get(proIndex - 1);
					}
					if (depIndex > 0) {
						dep = depList.get(depIndex - 1);
					}
					if (gradeBox.getSelectedIndex() > 0) {
						sco.setGrade((String) gradeBox.getSelectedItem());
					}

					emp.setDep(dep);
					sco.setPro(pro);
					sco.setEmp(emp);

					List<Score> scos = scoDao.searchCondition(sco);

					refreshTable(scos);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

	public void refreshTable() {
		list = scoDao.search();
		model.setList(list);
		table.updateUI();
	}

	protected void refreshTable(List<Score> scos) {
		model.setList(scos);
		table.updateUI();
	}

	public static void main(String[] args) {
		ScoreView ev = new ScoreView();
		ev.init();
	}

}