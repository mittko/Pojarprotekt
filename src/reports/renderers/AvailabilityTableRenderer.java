package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AvailabilityTableRenderer extends DefaultTableCellRenderer {
	private Color background = null;

	public AvailabilityTableRenderer() {
		background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (isSelected) {
			this.setBackground(Color.yellow);
		} else if (row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.WHITE);
		}

		return c;
	}

}
