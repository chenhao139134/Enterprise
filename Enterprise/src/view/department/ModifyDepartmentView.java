package view.department;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.DepartmentDao;
import entity.Department;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-08-03 20:36
 * @Description
 *
 */

public class ModifyDepartmentView extends SuperDepartmentView {

	private DepartmentDao depDao = new DepartmentDao();
	private int id;
	private CallBack callBack;
	private String type = "修改部门";

	private static ModifyDepartmentView modifyDepartmentView = null;

	public static ModifyDepartmentView getInstance(int id, CallBack callBack) {
		// TODO Auto-generated constructor stub
		if (modifyDepartmentView == null) {
			synchronized (ModifyDepartmentView.class) {
				if (modifyDepartmentView == null) {
					modifyDepartmentView = new ModifyDepartmentView(callBack);
					modifyDepartmentView.setId(id);
					modifyDepartmentView.init();
					System.out.println("创建modify");
				}
			}
		} else {
			System.out.println("modify已存在");
			modifyDepartmentView.setId(id);
			modifyDepartmentView.frame.dispose();
			modifyDepartmentView.init();
			modifyDepartmentView.frame.setVisible(true);

		}
		return modifyDepartmentView;
	}

	private void init() {
		// TODO Auto-generated method stub
		super.init(type);
		Department dep = depDao.selectById(id);
		String name = dep.getName();
		
		nameText.setText(name);
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Department dep = new Department();
				String name = null;
				name = nameText.getText();
				if(name.length() == 0) {
					JOptionPane.showMessageDialog(null, "请输入部门名");
				}
				dep.setName(name);
				dep.setId(id);
				DepartmentDao depDao = new DepartmentDao();
			   	boolean flag = depDao.update(dep);
			   	if(flag) {
			   		JOptionPane.showMessageDialog(null, "修改成功");
					modifyDepartmentView = null;
					nameText.setText("");
					
					frame.dispose();
					callBack.call();
			   	}
			}
		});
		
	}

	private ModifyDepartmentView(CallBack callBack) {
		// TODO Auto-generated constructor stub
		this.callBack = callBack;
	}

	private void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
}
