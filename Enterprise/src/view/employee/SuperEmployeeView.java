package view.employee;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;

/**
 * @Author ChenHao
 * @Date 2018-07-30 14:09
 * @Description
 *
 */

public class SuperEmployeeView {

	JFrame frame;
	JButton saveButton;
	JComboBox<String> sexBox;
	JTextField nameText;
	JTextField ageText;
	JComboBox<String> depBox;
	EmployeeDao empDao = new EmployeeDao();
	DepartmentDao depDao = new DepartmentDao();
	List<Department> listDep = depDao.search();
	public void init() {
		frame = new JFrame();
		frame.setSize(300, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel addPane = (JPanel) frame.getContentPane();
		BoxLayout layout = new BoxLayout(addPane, BoxLayout.Y_AXIS);
		addPane.setLayout(layout);
		
		JPanel addPanel1 = new JPanel();
		BoxLayout boxLayout = new BoxLayout(addPanel1, BoxLayout.Y_AXIS);
		addPanel1.setLayout(boxLayout);
		
		JPanel addPanel2 = new JPanel();

		JPanel addPanel11 = new JPanel();
		addPanel11.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		JPanel addPanel12 = new JPanel();
		addPanel12.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		JPanel addPanel13 = new JPanel();
		addPanel13.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		JPanel addPanel14 = new JPanel();
		addPanel14.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		addPanel1.add(addPanel11);
		addPanel1.add(addPanel12);
		addPanel1.add(addPanel13);
		addPanel1.add(addPanel14);

		addPane.add(addPanel1);
		addPane.add(addPanel2);
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		addPanel11.add(nameLabel);
		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(80, 30));
		addPanel11.add(nameText);

		JLabel addSexLabel = new JLabel();
		addSexLabel.setText("性别");
		addPanel12.add(addSexLabel);
		sexBox = new JComboBox<>();
		sexBox.addItem("男");
		sexBox.addItem("女");
		addPanel12.add(sexBox);

		JLabel addAgeLabel = new JLabel();
		addAgeLabel.setText("年龄");
		addPanel13.add(addAgeLabel);
		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(80, 30));
		addPanel13.add(ageText);

		listDep = depDao.search();
		JLabel addDepLabel = new JLabel();
		addDepLabel.setText("部门");
		addPanel14.add(addDepLabel);
		depBox = new JComboBox<>();
		for(Department d : listDep) {
			depBox.addItem(d.getName());
		}
		addPanel14.add(depBox);
		
		saveButton = new JButton();
		saveButton.setText("添加");
		saveButton.setPreferredSize(new Dimension(80, 30));
		addPanel2.add(saveButton);
		
		
	
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// SuperEmployeeView s1 = new SuperEmployeeView();
		// SuperEmployeeView s2 = new SuperEmployeeView();
		// SuperEmployeeView s3 = new SuperEmployeeView();
		//

	}
}
