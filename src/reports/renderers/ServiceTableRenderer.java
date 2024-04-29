package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ServiceTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;

	public ServiceTableRenderer() {
		background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (isSelected) {
			// this.setBackground(Color.yellow);
		} else if (row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.WHITE);
		}
		/*
		 * if(table.getValueAt(row, 11).equals("да")) { // ако е презаписан
		 * this.setForeground(Color.gray); } else { // ако не е презаписан
		 */if (table.getValueAt(row, 10).equals("не")) { // ако е не е обработен
			this.setForeground(Color.red);
		} else if (table.getValueAt(row, 10).equals("да")) { // ако е обработен
			this.setForeground(Color.black);
		}

		//colorSelectedRows(table,row);
		// set color of choiced with mouse

		if (table.getSelectedRow() >= 0
				&& table.getValueAt(row, 11)
				.toString()
				.equals(table.getValueAt(table.getSelectedRow(), 11)
						.toString())) {
			this.setBackground(Color.yellow);
		}

		this.setHorizontalAlignment(JLabel.CENTER);

		return c;
	}

	public void colorSelectedRows(JTable table, int row) {}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
