package Reports.ReportsRenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ProtokolBrackTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;

	public ProtokolBrackTableRenderer() {
		// background = Color.decode("0xAAB4FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (row % 2 == 0) {
			this.setBackground(Color.decode("0xD8E6FF"));
		} else {
			this.setBackground(Color.WHITE);
		}
		// if (isSelected) {
		this.setForeground(Color.black);
		// }
		// set color of choiced with mouse
		if (table.getSelectedRow() >= 0
				&& table.getValueAt(row, 8)
						.toString()
						.equals(table.getValueAt(table.getSelectedRow(), 8)
								.toString())) {
			this.setBackground(Color.yellow);
		}

		if (column == 7) {
			this.setHorizontalAlignment(JLabel.LEFT);
		} else {
			this.setHorizontalAlignment(JLabel.CENTER);
		}

		return c;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
