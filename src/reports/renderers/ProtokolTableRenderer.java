package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ProtokolTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;

	public ProtokolTableRenderer() {
		background = Color.decode("0xD8E6FF");
	};

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (row % 2 == 0) {
			c.setBackground(background);
		} else {
			c.setBackground(Color.WHITE);
		}

		// set color of choiced with mouse
		int selectedIndex = table.getSelectedRow();

		if (selectedIndex >= 0) {
			String num = table.getValueAt(selectedIndex, 12).toString();
			if (table.getValueAt(row, 12).toString().equals(num)) {
				c.setBackground(Color.yellow);
			}
		}

		if (column == 10) {
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
