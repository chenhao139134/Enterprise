package view.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.ProjectDao;
import entity.Project;
import util.CallBack;

/**
 * @Author ChenHao
 * @Date 2018-08-03 20:36
 * @Description
 *
 */

public class ModifyProjectView extends SuperProjectView {

	private ProjectDao depDao = new ProjectDao();
	private int id;
	private CallBack callBack;
	private String type = "修改项目";

	private static ModifyProjectView modifyProjectView = null;

	public static ModifyProjectView getInstance(int id, CallBack callBack) {
		// TODO Auto-generated constructor stub
		if (modifyProjectView == null) {
			synchronized (ModifyProjectView.class) {
				if (modifyProjectView == null) {
					modifyProjectView = new ModifyProjectView(callBack);
					modifyProjectView.setId(id);
					modifyProjectView.init();
					System.out.println("创建modify");
				}
			}
		} else {
			System.out.println("modify已存在");
			modifyProjectView.setId(id);
			modifyProjectView.frame.dispose();
			modifyProjectView.init();
			modifyProjectView.frame.setVisible(true);

		}
		return modifyProjectView;
	}

	private void init() {
		// TODO Auto-generated method stub
		super.init(type);
		Project dep = depDao.selectById(id);
		String name = dep.getName();
		
		nameText.setText(name);
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Project dep = new Project();
				String name = null;
				name = nameText.getText();
				if(name.length() == 0) {
					JOptionPane.showMessageDialog(null, "请输入项目名");
				}
				dep.setName(name);
				dep.setId(id);
				ProjectDao depDao = new ProjectDao();
			   	boolean flag = depDao.update(dep);
			   	if(flag) {
			   		JOptionPane.showMessageDialog(null, "修改成功");
					modifyProjectView = null;
					nameText.setText("");
					
					frame.dispose();
					callBack.call();
			   	}
			}
		});
		
	}

	private ModifyProjectView(CallBack callBack) {
		// TODO Auto-generated constructor stub
		this.callBack = callBack;
	}

	private void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
}
