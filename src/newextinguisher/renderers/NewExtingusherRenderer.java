package newextinguisher.renderers;

import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class NewExtingusherRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private Color selectedColor = null;

	public  NewExtingusherRenderer() {
		background = Color.decode("0xD8E6FF");
		selectedColor = Color.decode("0xAAB4FF");
	}
	public NewExtingusherRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
		selectedColor = Color.decode("0xAAB4FF");
		setColumnsWidth(refferenceTable);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
			boolean hasFocus,int row,int column) {
	
		Component c = super.getTableCellRendererComponent(table, value,
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
		} else {
		this.setBackground(Color.WHITE);
	}
		if(row == table.getSelectedRow() && column == table.getSelectedColumn()) {
			this.setBackground(Color.yellow);
		}
		if(column > 0) {
		this.setHorizontalAlignment(JLabel.CENTER);
		} else {
			this.setHorizontalAlignment(JLabel.LEFT);
		}
	
		return c;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private void setColumnsWidth(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(new MainPanel().WIDTH  / 4);

		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
         table.getColumnModel().getColumn(6).setPreferredWidth(20);
		table.getTableHeader().setReorderingAllowed(false);
	}
}
