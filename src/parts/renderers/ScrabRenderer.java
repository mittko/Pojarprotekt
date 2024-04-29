package parts.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ScrabRenderer extends DefaultTableCellRenderer {
   
	private Color background = null;
	private JTable refferenceTable = null;
	
	public ScrabRenderer(JTable refferenceTable) {	
		background = Color.decode("0xD8E6FF");
		this.refferenceTable = refferenceTable;
		hideColumns(refferenceTable);
	}
	
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
			boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if(row %2 == 0) {
			c.setBackground(background);
		} else {
			c.setBackground(Color.white);
		}
		if(isSelected) {
			c.setBackground(background.darker());
		} 
		this.setHorizontalAlignment(JLabel.CENTER);
		return c;
	}
	
	private void hideColumns(JTable table) {
		 // hide column 0
		 table.getColumnModel().getColumn(0).setMinWidth(0);
		 table.getColumnModel().getColumn(0).setMaxWidth(0);
	}

}
