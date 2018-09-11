package view.department;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.DepartmentDao;
import entity.Department;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-08-03 20:01
 * @Description
 *
 */

public class AddDepartmentView extends SuperDepartmentView {

	CallBack callBack;
	String type = "添加部门";
	private static AddDepartmentView addDepartmentView = null;

	public static AddDepartmentView getInstance(CallBack callBack) {
		if(addDepartmentView == null) {
			synchronized (AddDepartmentView.class) {
				if(addDepartmentView == null) {
					addDepartmentView = new AddDepartmentView(callBack);
					addDepartmentView.init();
					System.out.println("创建add");
				}
			}
		}else {
			addDepartmentView.frame.setVisible(true);
			System.out.println("add已存在");
		}
		return addDepartmentView;
	}
	
	private AddDepartmentView(CallBack callBack) {
		this.callBack = callBack;
	}
	
	public void init() {
		super.init(type);
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Department dep = new Department();
				String name = nameText.getText();
				int num = 0;
				dep.setName(name);
				dep.setEmpCount(num);
				DepartmentDao depDao = new DepartmentDao();
				depDao.add(dep);
				
				frame.dispose();
				callBack.call();
			}
		});
		
	}
	
}
