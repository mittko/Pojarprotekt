package reports.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AcquittanceTableRender extends DefaultTableCellRenderer {

	Color background = null;
	Color blue = null;

	public AcquittanceTableRender(JTable referenceTable) {
		this.background = referenceTable.getBackground();
		// background = Color.decode("0xD8E6FF");
	}

	public AcquittanceTableRender() {
		this.background = Color.decode("0xD8E6FF");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (table.getSelectedRow() >= 0
				&& table.getValueAt(row, 5)
						.toString()
						.equals(table.getValueAt(table.getSelectedRow(), 5)
								.toString())) {
			this.setBackground(Color.yellow);
		}
		// else {

		// if(ReportTableAcquittance.MOUSE_MOTION_ROW >= 0 &&
		// table.getValueAt(row, 5).toString().equals(
		// table.getValueAt(ReportTableAcquittance.MOUSE_MOTION_ROW,5).toString()))
		// {
		//
		// this.setBackground(Color.yellow);
		//
		else {
			if (row % 2 == 0) {
				this.setBackground(background);
			} else {
				this.setBackground(Color.WHITE);
			}
		}

		// setColumnsWidth(table);
		table.getTableHeader().setReorderingAllowed(false);
		return c;
	}

	/*
	 * private void setColumnsWidth(JTable table) { TableColumnModel cm =
	 * table.getColumnModel(); cm.getColumn(0).setPreferredWidth(500);
	 * cm.getColumn(1).setPreferredWidth(20);
	 * cm.getColumn(2).setPreferredWidth(5);
	 * cm.getColumn(3).setPreferredWidth(20);
	 * cm.getColumn(4).setPreferredWidth(20);
	 * cm.getColumn(5).setPreferredWidth(50); }
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
