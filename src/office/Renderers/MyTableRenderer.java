package office.Renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;



public class MyTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private Color selectedColor = null;
	private JTable refferenceTable = null;
	
	public MyTableRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
		selectedColor = Color.decode("0xAAB4FF");
		this.refferenceTable = refferenceTable;
	    hideColumns(refferenceTable);
		setColumnsWidth(refferenceTable);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellRendererComponent(table, obj,
				isSelected, hasFocus, row, column);
		if(hasFocus) {
			this.setForeground(Color.red);
		} else {
			this.setForeground(Color.black);
		}
		if (isSelected) {
			this.setBackground(selectedColor);
		} else if (row % 2 == 0) {
			this.setBackground(background);//("0x84C1FF"));//(Color.decode("0xEFF3FF"));
		} else if (row % 2 != 0) {
		this.setBackground(Color.WHITE);
	}
		if(row == table.getSelectedRow() && column == table.getSelectedColumn()) {
			this.setBackground(Color.yellow);
		}
		this.setHorizontalAlignment(JLabel.CENTER);
		
		return c;
	}
	private void hideColumns(JTable table) {
		int hide = 6;
		table.getColumnModel().getColumn(hide).setMinWidth(0);
		table.getColumnModel().getColumn(hide).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 1).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 1).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 2).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 2).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 3).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 3).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 4).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 4).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 5).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 5).setMaxWidth(0);
		table.getColumnModel().getColumn(hide + 6).setMinWidth(0);
		table.getColumnModel().getColumn(hide + 6).setMaxWidth(0);
		
		
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
	}

	private void setColumnsWidth(JTable table) {
		int width = this.WIDTH;
		table.getColumnModel().getColumn(0).setPreferredWidth(width / 12); // type
		table.getColumnModel().getColumn(1).setPreferredWidth(width / 24); // wheight
		table.getColumnModel().getColumn(2).setPreferredWidth(width / 8); // barcod
		table.getColumnModel().getColumn(3).setPreferredWidth(width / 8); // serial
		table.getColumnModel().getColumn(4).setPreferredWidth(0); // category
		table.getColumnModel().getColumn(5).setPreferredWidth(width / 6); // brand
	}


}
