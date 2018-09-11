package view.project;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @Author ChenHao
 * @Date 2018-08-03 19:42 
 * @Description 
 *
 */

public class SuperProjectView {
	JFrame frame;
	JButton confirmBtn;
	JTextField nameText;
	
	public void init(String type) {
		frame = new JFrame(type);
		frame.setSize(300, 180);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel1 = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		panel1.setLayout(boxLayout);
		
		JPanel addPanel11 = new JPanel();
		addPanel11.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
		JPanel addPanel12 = new JPanel();
		addPanel12.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
		
		panel1.add(addPanel11);
		panel1.add(addPanel12);
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("项目名");
		addPanel11.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(160, 30));
		addPanel11.add(nameText);
		
		confirmBtn = new JButton();
		confirmBtn.setText("确认");
		confirmBtn.setPreferredSize(new Dimension(80, 30));
		addPanel12.add(confirmBtn);
		
		frame.setVisible(true);
	
	}
	
}
