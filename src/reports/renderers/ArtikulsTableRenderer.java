package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ArtikulsTableRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private Color selectedBackground = null;

	public ArtikulsTableRenderer() {
		background = Color.decode("0xD8E6FF");
		selectedBackground = Color.yellow;// Color.decode("0x11E6FF");
	};

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (isSelected) {
			c.setBackground(selectedBackground);
		} else if (row % 2 == 0) {
			c.setBackground(background);
		} else {
			c.setBackground(Color.WHITE);
		}

		if (column > 0) {
			this.setHorizontalAlignment(JLabel.CENTER);
		} else {
			this.setHorizontalAlignment(JLabel.LEFT);
		}

		return c;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
