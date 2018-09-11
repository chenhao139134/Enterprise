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
		JTableHeader head = table.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
		head.setFont(new Font("宋体", Font.BOLD, 14));// 设置表格字体
		table.setRowHeight(18);// 设置表格行宽

		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
		
		for (int i = 0; i < columns.length; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(50);
			}
		}

		DefaultTableCellRenderer ter = new DefaultTableCellRenderer()// 设置表格间隔色
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
		table.setFont(new Font("宋体", Font.PLAIN, 14));
		for (int i = 0; i < columns.length; i++) {
			table.getColumn(columns[i]).setCellRenderer(ter);
		}

	}
}
