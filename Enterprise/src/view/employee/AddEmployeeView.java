package view.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import entity.Department;
import entity.Employee;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-07-30 10:13
 * @Description
 *
 */

public class AddEmployeeView extends SuperEmployeeView {

	CallBack callBack;
	public static AddEmployeeView addEmployeeView = null;

	public static AddEmployeeView getInstance(CallBack callBack) {
		if (addEmployeeView == null) {
			synchronized (AddEmployeeView.class) {
				if (addEmployeeView == null) {
					addEmployeeView = new AddEmployeeView(callBack);
					addEmployeeView.init();
					System.out.println("创建add");
				}
			}
		} else {
			addEmployeeView.frame.setVisible(true);
			System.out.println("add已存在");
		}
		return addEmployeeView;
	}

	private AddEmployeeView(CallBack callBack) {
		this.callBack = callBack;
	}

	public void init() {

		super.init();

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = null;
					String sex = null;
					int age = 0;
					int index = 0;

					name = nameText.getText();
					sex = (String) sexBox.getSelectedItem();
					age = Integer.parseInt(ageText.getText() + "0") / 10;
					index = depBox.getSelectedIndex();
					Department dep = listDep.get(index);
					// 判断是否有值为0
					if (name.length() == 0) {
						JOptionPane.showMessageDialog(null, "请输入员工姓名");
					} else if (sex.length() == 0) {
						JOptionPane.showMessageDialog(null, "请选中员工性别");
					} else if (age == 0) {
						JOptionPane.showMessageDialog(null, "请输入员工年龄");
					} else { // 创建新的员工对象，放入emps
						Employee emp = new Employee();
						emp.setName(name);
						emp.setAge(age);
						emp.setSex(sex);
						emp.setdId(dep.getId());
						empDao.add(emp);
						nameText.setText("");
						ageText.setText("");
						frame.dispose();
						callBack.call();
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

}
