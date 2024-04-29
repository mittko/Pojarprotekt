package admin.sklad.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class NewExtRenderer2 extends DefaultTableCellRenderer {

	private Color background = null;
	private Color selectedBackground = null;

	public NewExtRenderer2() {
		this.background = Color.decode("0xD8E6FF");
		selectedBackground = Color.yellow;// Color.decode("0xAAB4FF");

	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean hasFocus, boolean isSelected, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, obj, hasFocus,
				isSelected, row, column);
		// set background
		if (row == table.getSelectedRow()) {
			this.setBackground(selectedBackground);
		} else if (row % 2 == 0) {
			this.setBackground(this.background);
		} else {
			this.setBackground(Color.white);
		}

		// set foreground
		if (column == 4) {
			String val = table.getValueAt(row, 4).toString();
			try {
				int quantity = Integer.parseInt(val);

				if (quantity > 5) {
					this.setForeground(Color.green.darker());
				} else {
					this.setForeground(Color.red.brighter());
				}
			} catch (Exception e) {

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
