package view.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import entity.Department;
import entity.Employee;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-07-30 10:23
 * @Description
 *
 */

public class ModifyEmployeeView extends SuperEmployeeView {

	private int id;

	private CallBack callBack;

	private static ModifyEmployeeView modifyEmployeeView = null;

	public static ModifyEmployeeView getInstance(int id, CallBack callBack) {

		if (modifyEmployeeView == null) {
			synchronized (ModifyEmployeeView.class) {
				if (modifyEmployeeView == null) {
					modifyEmployeeView = new ModifyEmployeeView(callBack);
					modifyEmployeeView.setId(id);
					modifyEmployeeView.init();
					System.out.println("创建modify");
				}
			}
		} else {
			System.out.println("modify已存在");
			modifyEmployeeView.setId(id);
			modifyEmployeeView.frame.dispose();
			modifyEmployeeView.init();
			modifyEmployeeView.frame.setVisible(true);
		}
		return modifyEmployeeView;
	}

	public void setId(int id) {
		this.id = id;
		System.out.println("id : " + id);
	}

	private ModifyEmployeeView(CallBack callBack) {
		// TODO Auto-generated constructor stub
		this.callBack = callBack;
	}

	public void init() {
		super.init();

		Employee emp = empDao.selectById(id);
		String name = emp.getName();
		String sex = emp.getSex();
		int age = emp.getAge();
		String depName = emp.getDep().getName();
		nameText.setText(name);
		sexBox.setSelectedItem(sex);
		ageText.setText(String.valueOf(age));
		depBox.setSelectedItem(depName);
		// 监听修改面板修改按钮
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Employee ee = new Employee();
					String name = null;
					String sex = null;
					int age = 0;
					int dId = 0;
					name = nameText.getText();
					sex = (String) sexBox.getSelectedItem();
					age = Integer.parseInt(ageText.getText() + "0") / 10;
					dId = depBox.getSelectedIndex();
					Department dep = listDep.get(dId);
					//depName = (String) depBox.getSelectedItem();
					// 判断是否有值为0
					if (name.length() == 0) {
						JOptionPane.showMessageDialog(null, "请输入员工姓名");
					} else if (sex.length() == 0) {
						JOptionPane.showMessageDialog(null, "请选择员工性别");
					} else if (age == 0) {
						JOptionPane.showMessageDialog(null, "请输入员工年龄");
					} else {
						ee.setId(id);
						ee.setName(name);
						ee.setAge(age);
						ee.setSex(sex);
						ee.setdId(dep.getId());
						
						boolean flag = empDao.update(ee);
						if (flag) {
							JOptionPane.showMessageDialog(null, "修改成功");
							modifyEmployeeView = null;
							nameText.setText("");
							ageText.setText("");
							frame.dispose();
							callBack.call();
						}

					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

}
