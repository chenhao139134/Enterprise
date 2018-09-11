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
					System.out.println("����add");
				}
			}
		} else {
			addEmployeeView.frame.setVisible(true);
			System.out.println("add�Ѵ���");
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
					// �ж��Ƿ���ֵΪ0
					if (name.length() == 0) {
						JOptionPane.showMessageDialog(null, "������Ա������");
					} else if (sex.length() == 0) {
						JOptionPane.showMessageDialog(null, "��ѡ��Ա���Ա�");
					} else if (age == 0) {
						JOptionPane.showMessageDialog(null, "������Ա������");
					} else { // �����µ�Ա�����󣬷���emps
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
