package view.department;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.DepDetailsDao;
import dao.EmployeeDao;
import entity.Employee;
import entity.Project;
import util.TableUtil;
import view.model.DepEmpDetailsModel;

/**
 * @Author ChenHao
 * @Date 2018-08-05 09:57 
 * @Description 
 *
 */

public class DepEmpDetailsView {
	String depName;
	int dId;
	DepEmpDetailsModel model;
	JTable table = new JTable();
	Map<Project, Integer> map = new HashMap<>();
	List<Employee> list = new ArrayList<>();
	EmployeeDao empDao = new EmployeeDao();
	DepDetailsDao depDetDao = new DepDetailsDao();
	public void init() {
		JFrame frame = new JFrame(depName + "人员详情");
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
		panel.setLayout(boxLayout);
		
		JPanel panel1 = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		panel.add(panel1);
		
		list = empDao.searchByDid(dId);
		table = new JTable();
		model = new DepEmpDetailsModel(list);
		
		table.setModel(model);
		TableUtil.setTableView(table, model.getColumns());
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400, 200));
		panel1.add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Employee emp = list.get(table.getSelectedRow());
				String empName = emp.getName();
				List<Project> proList = new ArrayList<>();
				if(e.getClickCount() >= 2) {
					JFrame empProFrame = new JFrame("分配项目给 " + empName);
					empProFrame.setSize(250, 200);
					empProFrame.setLocationRelativeTo(null);
					empProFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					JPanel panel = (JPanel) empProFrame.getContentPane();
					BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
					panel.setLayout(boxLayout);
					
					JPanel pan = new JPanel();
					pan.setLayout(new FlowLayout(FlowLayout.CENTER));
					
					panel.add(pan);
					
					JComboBox<String> proBox = new JComboBox<>();
					map = depDetDao.searchPro(dId);
					
					proBox.addItem("");
					for(Project p : map.keySet()) {
						proBox.addItem(p.getName());
						proList.add(p);
					}
					pan.add(proBox);
					
					
					JButton saveBtn = new JButton("保存");
					pan.add(saveBtn);
					saveBtn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int nowCount = 0;
							if(proBox.getSelectedIndex() > 0) {
								emp.setPro(proList.get(proBox.getSelectedIndex() - 1));
							}else {
								emp.getPro().setName("");
								emp.getPro().setId(0);
							}
							if(emp.getPro().getId() != 0) {
								nowCount = depDetDao.nowCount(emp.getPro().getId(),emp.getDep().getId());
								System.out.println(nowCount + "++++");
								System.out.println(map.get(emp.getPro()) + "++++");
								if(nowCount >= map.get(emp.getPro())) {
									JOptionPane.showMessageDialog(null, "本项目最多允许" + map.get(emp.getPro()) + "人参与");
									empProFrame.dispose();
								}
							}
							boolean flag = depDetDao.saveEmpPro(emp);
							if(flag) {
								list = empDao.searchByDid(dId);
								model.setList(list);
								empProFrame.dispose();
								table.updateUI();
							}
						}
					});
					
					empProFrame.setVisible(true);
					
				}
			}
		});
		
		frame.setVisible(true);
	}
	
	public DepEmpDetailsView(String depName, int dId) {
		// TODO Auto-generated constructor stub
		this.depName = depName;
		this.dId = dId;
	}
}


