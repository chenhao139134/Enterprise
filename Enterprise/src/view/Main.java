package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.department.DepaertmentView;
import view.employee.EmployeeView;
import view.project.ProjectView;
import view.score.ScoreView;

/**
 * @Author ChenHao
 * @Date 2018-08-04 11:33
 * @Description
 *
 */

public class Main {
	public void init() {
		JFrame frame = new JFrame("首页");

		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = (JPanel) frame.getContentPane();

		JPanel panela = new JPanel();
		panela.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JPanel panelb = new JPanel();
		panelb.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JPanel panelc = new JPanel();
		panelc.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(200, 35));

		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(100, 35));

		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(100, 35));

		JPanel panel4 = new JPanel();
		panel4.setPreferredSize(new Dimension(100, 35));

		JPanel panel5 = new JPanel();
		panel5.setPreferredSize(new Dimension(100, 35));

		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);
		int width = 100;
		int height = 30;
		JLabel label = new JLabel();
		label.setText("欢迎使用三国集团管理系统");
		label.setPreferredSize(new Dimension(200, height));
		label.setFont(new Font("宋体", Font.BOLD, 14));
		JButton empButton = new JButton("员工管理");
		empButton.setPreferredSize(new Dimension(width, height));
		JButton depButton = new JButton("部门管理");
		depButton.setPreferredSize(new Dimension(width, height));
		JButton proButton = new JButton("项目管理");
		proButton.setPreferredSize(new Dimension(width, height));
		JButton scoButton = new JButton("绩效管理");
		scoButton.setPreferredSize(new Dimension(width, height));

		panela.add(panel1);
		panelb.add(panel2);
		panelb.add(panel3);
		panelc.add(panel4);
		panelc.add(panel5);

		panel1.add(label);
		panel2.add(empButton);
		panel3.add(depButton);
		panel4.add(proButton);
		panel5.add(scoButton);

		panel.add(panela);
		panel.add(panelb);
		panel.add(panelc);

		frame.setVisible(true);

		empButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				EmployeeView.getInstance();
			}
		});

		depButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				DepaertmentView.getInstance();
			}
		});

		proButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ProjectView.getInstance();
			}
		});

		scoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				ScoreView.getInstance();
			}
		});
	}

	public static void main(String[] args) {
		new Main().init();
	}
}
