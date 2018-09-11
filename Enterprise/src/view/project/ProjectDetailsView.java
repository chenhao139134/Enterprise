package view.project;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import dao.DepDetailsDao;
import dao.ProDetailsDao;
import entity.Department;
import entity.ProGroup;
import util.CallBack;
import util.TableUtil;
import view.model.ProjectDetailsModel;

/**
 * @Author ChenHao
 * @Date 2018-08-05 09:57 
 * @Description 
 *
 */

public class ProjectDetailsView {
	String proName;
	int pId;
	ProjectDetailsModel model;
	ProGroup prog = new ProGroup();
	JTable table = new JTable();
	List<Department> notList = new ArrayList<>();
	ProDetailsDao proDetDao = new ProDetailsDao();
	DepDetailsDao depDetDao = new DepDetailsDao();
	JComboBox<String> depBox = new JComboBox<>();
	private CallBack callBack;
	public void init() {
		JFrame frame = new JFrame(proName);
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		
		
		prog = proDetDao.searchPro(pId);
		table = new JTable();
		model = new ProjectDetailsModel(prog);
		
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(450, 200));
		panel1.add(scroll);

		JPanel panel21 = new JPanel();
		panel21.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		JButton addBtn = new JButton();
		addBtn.setText("添加");
		addBtn.setPreferredSize(new Dimension(80, 30));
		panel21.add(addBtn);

		JPanel panel22 = new JPanel();
		panel22.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		JButton deleteBtn = new JButton();
		deleteBtn.setText("删除");
		deleteBtn.setPreferredSize(new Dimension(80, 30));
		panel22.add(deleteBtn);

		panel2.add(panel21);
		panel2.add(panel22);
		
		JLabel label = new JLabel();
		label.setText("待添加");
		panel3.add(label);

		setBox(depBox);
		panel3.add(depBox);

		JLabel emplabel = new JLabel();
		emplabel.setText("人数");
		panel3.add(emplabel);

		JTextField countText = new JTextField();
		countText.setPreferredSize(new Dimension(60, 20));
		panel3.add(countText);

		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = depBox.getSelectedIndex();
				boolean flag = false;
				int count = 0;
				int max = notList.get(depBox.getSelectedIndex() - 1).getEmpCount();
				try {
					count = Integer.parseInt(countText.getText());
					if (index > 0 && count <= max) {
						Department dep = notList.get(index - 1);
						flag = proDetDao.addDepPro(dep.getId(), pId, count);
					}else {
						JOptionPane.showMessageDialog(null,	"请输入 1 ~ " + max + " 中的数字");
					}
					if (flag) {
						refreshTable();
						callBack.call();
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,	"请输入 1 ~ " + max + " 中的数字");
				}

				
			}
		});

		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean flag = false;
				int index = table.getSelectedRow();
				int d_id = (int) table.getValueAt(index, 0);

				if (index > -1) {
					flag = proDetDao.deleteDepPro(pId, d_id);
				}
				if (flag) {
					JOptionPane.showMessageDialog(null, "删除成功！");
					refreshTable();
					callBack.call();
				}
			}
		});
		frame.setVisible(true);
	}
	private void setBox(JComboBox<String> box) {
		// TODO Auto-generated method stub
		box.removeAllItems();
		box.addItem("");
		notList = proDetDao.searchNotEquals(pId);
		for (Department d : notList) {
			box.addItem(d.getName());
		}
	}

	public void refreshTable() {
		prog = proDetDao.searchPro(pId);
		notList = proDetDao.searchNotEquals(pId);
		setBox(depBox);
		model.setProg(prog);
		table.updateUI();
	}

	public ProjectDetailsView(String proName, int pId, CallBack callBack) {
		// TODO Auto-generated constructor stub
		this.proName = proName;
		this.pId = pId;
		this.callBack = callBack;
	}
}
