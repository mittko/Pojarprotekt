/*package Parts.PartsRenderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import CommonResources.MainPanel;

public class MyTableRenderer extends  DefaultTableCellRenderer{

	public MyTableRenderer() {
	   
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		table.setRowHeight(MainPanel.getFontSize() + 15);
		Component c =
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		this.setHorizontalAlignment(JLabel.CENTER);
		return c;
	}

}
*/