package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * @Author ChenHao
 * @Date 2018-08-06 21:10 
 * @Description 
 *
 */

public class TableUtil {
	
	public static void setTableView(JTable table, String[] columns) {
		TableColumn column;
		JTableHeader head = table.getTableHeader(); // �������������
		head.setPreferredSize(new Dimension(head.getWidth(), 35));// ���ñ�ͷ��С
		head.setFont(new Font("����", Font.BOLD, 14));// ���ñ������
		table.setRowHeight(18);// ���ñ���п�

		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// �������ñ���п�
		
		for (int i = 0; i < columns.length; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(50);
			}
		}

		DefaultTableCellRenderer ter = new DefaultTableCellRenderer()// ���ñ����ɫ
		{
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
				if (row % 2 == 0)
					setBackground(new Color(160,220,220));
				else if (row % 2 == 1)
					setBackground(Color.white);
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		ter.setHorizontalAlignment(JLabel.CENTER);
		table.setRowHeight(30);
		table.setFont(new Font("����", Font.PLAIN, 14));
		for (int i = 0; i < columns.length; i++) {
			table.getColumn(columns[i]).setCellRenderer(ter);
		}

	}
}
