package admin.parts.quantity.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer{

	Color background;
	Color selectedBackground;
	public Renderer() {
		background = Color.decode("0xD8E6FF");
		selectedBackground = background.darker();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected,boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	
		if(isSelected) {
			c.setBackground(selectedBackground);
		}else
		if(row % 2 == 0) {
			c.setBackground(background);
		} else {
			c.setBackground(Color.white);
		}
		if(column == 1) {
			if(Integer.parseInt(table.getValueAt(row, 1).toString()) > 5) {
			c.setForeground(Color.green.darker().darker());
			} else {
				c.setForeground(Color.red);
			}
		}else {
			c.setForeground(Color.black);
		}

		
		return c;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
