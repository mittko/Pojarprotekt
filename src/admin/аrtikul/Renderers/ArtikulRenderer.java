package admin.àrtikul.Renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ArtikulRenderer extends DefaultTableCellRenderer {
	Color background = null;

	public ArtikulRenderer() {
		background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (column == 3) {
			if (value.toString().contains(",")) {
				value = value.toString().replace(",", "."); // english comma
			}
			if (value.toString().contains(",")) { // bulgarian comma
				value = value.toString().replace(",", ".");
			}
			try {
				value = String.format(Locale.ROOT, "%.2f",
						Double.parseDouble(value.toString())); // set two
																// decimals
																// after points
			} catch (NumberFormatException ne) {
				value = "";
			}
		}
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (column != 0) {
			this.setHorizontalAlignment(JLabel.CENTER);
		} else {
			this.setHorizontalAlignment(JLabel.LEFT);
		}
		if (isSelected) {
			// this.setBackground(background.darker());
			// if(column == table.getSelectedColumn()) {
			this.setBackground(Color.yellow);
			// }
		} else if (row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.white);
		}

		if (column == 3) {
			this.setForeground(Color.BLUE);
		} else if (column == 1) {
			int quantity = 0;
			try {
				quantity = Integer.parseInt(value.toString());
				if (quantity <= 5) {
					this.setForeground(Color.red);
				} else if (quantity > 5) {
					this.setForeground(Color.green.darker().darker());
				} else {
					this.setForeground(Color.black);
				}
			} catch (Exception e) {

			}
			// if(quantity <= 5) {
			// this.setForeground(Color.red);
			// } else if(quantity > 5) {
			// this.setForeground(Color.green.darker().darker());
			// } else {
			// this.setForeground(Color.black);
			// }

		} else {
			this.setForeground(Color.black);
		}

		return c;
	}
}
