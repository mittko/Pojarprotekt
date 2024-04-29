package workingbook.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import parts.ReasonsBrack;

public class ReasonsRenderer extends DefaultTableCellRenderer {

	Color background = null;
	
	public ReasonsRenderer() {
		background = getBackground();
		
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
			boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(table, 
				value, isSelected, hasFocus, row, column);
		c.setForeground(Color.BLACK);
		if(ReasonsBrack.boolReasons[row]) {
		c.setBackground(Color.RED);
		} else {
		c.setBackground(Color.LIGHT_GRAY);
		}
	
		return c;
	}
	

}
