package view.department;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.DepDetailsDao;
import dao.ProDetailsDao;
import entity.Project;
import util.CallBack;
import util.TableUtil;
import view.model.DepProDetailsTableModel;

/**
 * @Author ChenHao
 * @Date 2018-08-05 09:57 
 * @Description 
 *
 */

public class DepProDetailsView {
	String depName;
	int id;
	CallBack callBack;
	JTable table;
	JComboBox<String> proBox = new JComboBox<>();
	
	DepProDetailsTableModel model;
	Map<Project, Integer> map = new HashMap<>();
	List<Project> notList = new ArrayList<>();
	DepDetailsDao depDetDao = new DepDetailsDao();
	ProDetailsDao proDetDao = new ProDetailsDao();
	public void init() {
		JFrame frame = new JFrame(depName + "项目管理");
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
		panel.setLayout(boxLayout);
		
		JPanel panel1 = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panel2 = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		
		JPanel panel3 = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		
		map = depDetDao.searchPro(id);
		table = new JTable();
		model = new DepProDetailsTableModel(map);
		
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400, 200));
		panel1.add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 2) {
					new DepEmpDetailsView(depName, id).init();
				}
			}
			
		});
		
//		JPanel panel21 = new JPanel();
//		panel21.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
//		JButton addBtn = new JButton();
//		addBtn.setText("添加");
//		addBtn.setPreferredSize(new Dimension(80, 30));
//		panel21.add(addBtn);
//		
//		JPanel panel22 = new JPanel();
//		panel22.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
//		JButton deleteBtn = new JButton();
//		deleteBtn.setText("删除");
//		deleteBtn.setPreferredSize(new Dimension(80, 30));
//		panel22.add(deleteBtn);
//		
//		panel2.add(panel21);
//		panel2.add(panel22);
//		
//		JLabel label = new JLabel();
//		label.setText("待添加");
//		panel3.add(label);
		
//		setBox(proBox);
//		panel3.add(proBox);
		
//		addBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				int index = proBox.getSelectedIndex();
//				boolean flag = false;
//				System.out.println(index);
//				if(index > 0) {
//					Project project = notList.get(index - 1);
//					flag = proDetDao.addDepPro(id,project.getId());
//				}
//				
//				if(flag) {
//					refreshTable();
//					callBack.call();
//				}
//			}
//		});
//		
//		deleteBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				boolean flag = false;
//				int index = table.getSelectedRow();
//				int p_id = (int) table.getValueAt(index, 0);
//				
//				if(index > -1) {
//					flag = depDetDao.deleteDepPro(p_id, id);
//				}
//				if(flag) {
//					JOptionPane.showMessageDialog(null, "删除成功！");
//					refreshTable();
//					callBack.call();
//				}
//			}
//		});
		
		frame.setVisible(true);
	}
//	private void setBox(JComboBox<String> box) {
//		// TODO Auto-generated method stub
//		box.removeAllItems();
//		box.addItem("");
//		notList = proDetDao.searchNotEquals(id);
//		for(Project p : notList) {
//			box.addItem(p.getName());
//		}
//	}
//	public void refreshTable() {
//		map = depDetDao.searchPro(id);
//		setBox(proBox);
//		model.setMap(map);
//		table.updateUI();
//
//	}
//
	public DepProDetailsView(String depName, int id, CallBack callBack) {
		// TODO Auto-generated constructor stub
		this.depName = depName;
		this.id = id;
		this.callBack = callBack;
	}
}
