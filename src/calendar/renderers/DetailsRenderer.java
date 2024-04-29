package calendar.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DetailsRenderer extends DefaultTableCellRenderer {
	
	private Color background = null;

	public DetailsRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellRendererComponent(table, obj, 
				isSelected, hasFocus, row, column);
	
		if(row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.white);
		}
		if(isSelected) {
			this.setBackground(background.darker());
		}
		// set aligment
		if(column == 0) {
			this.setHorizontalAlignment(JLabel.LEFT);
		} else {
			this.setHorizontalAlignment(JLabel.CENTER);
		}
	
		return this;
	}


}
