package WorkingBook.Renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DoingRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private JTable refferenceTable = null;
	
	public DoingRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
		this.refferenceTable = refferenceTable;
		hideColumns(refferenceTable);
		setColumnsWidth(refferenceTable);
	
	}
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
			boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, column);
		
		if(row % 2 == 0) {
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
	table.getColumnModel().getColumn(0).setMinWidth(0); // hide clients
	table.getColumnModel().getColumn(0).setMaxWidth(0);
	
	table.getTableHeader().setReorderingAllowed(false);
	//table.getTableHeader().setResizingAllowed(false);

	}

	private void setColumnsWidth(JTable table) {
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(6).setPreferredWidth(65);
		table.getColumnModel().getColumn(7).setPreferredWidth(45);
		table.getColumnModel().getColumn(8).setPreferredWidth(45);
		table.getColumnModel().getColumn(9).setPreferredWidth(45);
	}
}
