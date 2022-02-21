package Calendar.CalendarModel;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Calendar.Extinguishers;

public class DetailsModel extends DefaultTableModel {

	public DetailsModel(Object[] columnNames) {
		super(columnNames, 0);
	}

	public void appendData(ArrayList<Extinguishers> value) {
		if (this.getRowCount() > 0) {
			while (this.getRowCount() > 0) {
				this.removeRow(0);
			}
		}

		for (Extinguishers ext : value) {
			Object[] o = new Object[] { ext.type + " " + ext.wheight,
					ext.TO_date, ext.P_Date, ext.HI_Date, ext.additional_data };
			this.addRow(o);
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 4) {
			return true;
		}
		return false;
	}

}
