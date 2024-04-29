package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class InvoiceTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;

	public InvoiceTableRenderer() {
		background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		// setColumnsWidth(table);

		if (column == 0) {
			this.setHorizontalAlignment(JLabel.LEFT);
		} else {
			this.setHorizontalAlignment(JLabel.CENTER);
		}

		if (table.getSelectedRow() >= 0
				&& table.getValueAt(row, 0)
						.toString()
						.equals(table.getValueAt(table.getSelectedRow(), 0)
								.toString())) {
			this.setBackground(Color.yellow);
		} else {
			if (row % 2 == 0) {
				this.setBackground(background);
			} else {
				this.setBackground(Color.WHITE);
			}
		}

		return c;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
