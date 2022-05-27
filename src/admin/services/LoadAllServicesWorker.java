package admin.services;

import db.Artikul.Artikuli_DB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static utility.MainPanel.AVAILABLE_SERVICES;

public class LoadAllServicesWorker extends SwingWorker {
	private JDialog jd = null;
	private ArrayList<Object[]> data = null;

	public LoadAllServicesWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			data = Artikuli_DB.getAllAvailableArtikuls(AVAILABLE_SERVICES);
			AvailableServicesTable.helpSearchFieldList.clear();
			for (Object[] datum : data) {
				Object[] obj = new Object[]{datum[0], datum[1],
						datum[2], datum[3], datum[4],
						datum[5], datum[6], datum[7],
						datum[8]};
				AvailableServicesTable.helpSearchFieldList.add(obj);
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (data != null && data.size() > 0) {
						// first clear content
						if (AvailableServicesTable.serviceTableModel
								.getRowCount() > 0)
							AvailableServicesTable.serviceTableModel
									.setRowCount(0);


						for (Object[] datum : data) { // d <
							// data.size()

							Object[] obj = new Object[]{datum[0],
									datum[1], datum[2],
									datum[3], datum[4],
									datum[5], datum[6],
									datum[7], datum[8]};

							AvailableServicesTable.serviceTableModel
									.addRow(obj);
						}

						// ps.close();
					} else {
						JOptionPane.showMessageDialog(null,
								"Няма резултат от търсенето!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
