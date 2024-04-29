package admin.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer {

	private Color foreground = null;
	private Color background = null;
	private Color selectedBackground = null;
	private Font priceFont = null;
	
	public MyTableCellRenderer() {
		foreground = Color.RED;
		background = Color.decode("0xD8E6FF");
		selectedBackground = Color.decode("0xAAB4FF");
		priceFont = new Font(Font.DIALOG,Font.BOLD,22);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,
			boolean isSelected,boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent
				(table, value, isSelected, hasFocus, row, column);
	
		 if(row % 2 == 0) {
			 c.setBackground(background);
		 } else {
			 c.setBackground(Color.white);
		 }
		 if(isSelected) {
				this.setBackground(selectedBackground);
				if(column == table.getSelectedColumn()) {
					this.setBackground(Color.yellow);
				}
		 }
	
		 if(column == 4) {
		 c.setForeground(foreground);
		 c.setFont(priceFont);
		 } else {
		c.setForeground(Color.black);
		 }
		 if(column == 0) {
			 this.setHorizontalAlignment(JLabel.LEFT);
		 } else {
			 this.setHorizontalAlignment(JLabel.CENTER);
		 }
		return c;
		}


}
