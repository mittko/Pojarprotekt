package admin.sklad.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class NewExtRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private Color selectedBackground = null;

	public NewExtRenderer() {
		background = Color.decode("0xD8E6FF");
		selectedBackground = Color.decode("0xAAB4FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean hasFocus, boolean isSelected, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				hasFocus, isSelected, row, column);

		// set background
		if (row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.white);
		}
		if (row == table.getSelectedRow()) {
			this.setBackground(selectedBackground);
		}
		Boolean isSel = (Boolean) table.getValueAt(row, 7);
		if (isSel) {
			this.setBackground(Color.orange);
		}

		// set foreground
		if (column == 4) {
			int quantity = Integer
					.parseInt(table.getValueAt(row, 4).toString());

			if (quantity > 5) {
				this.setForeground(Color.green.darker());
			} else {
				this.setForeground(Color.red.brighter());
			}
		} else if (column == 5) {
			this.setForeground(Color.blue);
		} else {
			this.setForeground(Color.black);
		}

		// set cell content aligment
		if (column > 0) {
			this.setHorizontalAlignment(JLabel.CENTER);
		} else {
			this.setHorizontalAlignment(JLabel.LEFT);
		}

		return c;
	}

}
