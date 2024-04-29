package reports.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DeliveryTableRenderer extends DefaultTableCellRenderer {
	private Color background = null;

	public DeliveryTableRenderer() {
		background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (value.toString().contains("Фактура No")
				|| value.toString().contains("Касов бон")
				|| value.toString().contains("Дата")
				|| value.toString().contains("Контрагент")) {
			this.setForeground(Color.RED);
		} else {
			this.setForeground(Color.BLACK);
		}
		if (isSelected) {
			this.setBackground(Color.yellow);
		} else if (row % 2 != 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.WHITE);
		}
		return c;
	}

}
