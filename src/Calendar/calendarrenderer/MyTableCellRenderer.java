package Calendar.calendarrenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer{

	private Color background = null;
	private JTable refferenceTable = null;
	
	public MyTableCellRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
		this.refferenceTable = refferenceTable;
	//	setColumnsWidth(refferenceTable);
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
	
//	private void setColumnsWidth(JTable table) {
//
//		table.getColumnModel().getColumn(0).setMinWidth(400);
//		table.getColumnModel().getColumn(0).setMaxWidth(400);
//		
//		table.getTableHeader().setReorderingAllowed(false);
//         table.getTableHeader().setResizingAllowed(false);
//     
//	}
	
}
