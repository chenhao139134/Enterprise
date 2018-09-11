package view.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.ProjectDao;
import entity.Project;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-08-03 20:01
 * @Description
 *
 */

public class AddProjectView extends SuperProjectView {

	CallBack callBack;
	String type = "�����Ŀ";
	private static AddProjectView addProjectView = null;

	public static AddProjectView getInstance(CallBack callBack) {
		if(addProjectView == null) {
			synchronized (AddProjectView.class) {
				if(addProjectView == null) {
					addProjectView = new AddProjectView(callBack);
					addProjectView.init();
					System.out.println("����add");
				}
			}
		}else {
			addProjectView.frame.setVisible(true);
			System.out.println("add�Ѵ���");
		}
		return addProjectView;
	}
	
	private AddProjectView(CallBack callBack) {
		this.callBack = callBack;
	}
	
	public void init() {
		super.init(type);
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Project dep = new Project();
				String name = nameText.getText();
				dep.setName(name);
				ProjectDao depDao = new ProjectDao();
				depDao.add(dep);
				
				frame.dispose();
				callBack.call();
			}
		});
		
	}
	
}
