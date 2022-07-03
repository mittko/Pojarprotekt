package admin.SkladExtinguisher.Workers;

import db.NewExtinguisher.NewExtinguishers_DB;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SeeAllNewExtinguisherWorker extends SwingWorker {

	private JDialog jd = null;

	public SeeAllNewExtinguisherWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	public ArrayList<Object[]> doInBackground() throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Object[]> data = null;
		try {
			String command = "select * from "
					+ MainPanel.NEW_EXTINGUISHERS + " where quantitiy > 0 order by CAST(date as DATE) desc";
			data = NewExtinguishers_DB.getNewExtinguishers(command);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					/*
					 * if(data != null) { if(data.size() > 0) {
					 * 
					 * // clear table // tModel.setRowCount(0);
					 * 
					 * Object[] obj = null; int j = 0; for(int i = 0;i <
					 * data.size();i++) { obj = new Object[data.get(i).length +
					 * 2]; for(j = 0;j < data.get(i).length;j++) { obj[j] =
					 * data.get(i)[j]; } obj[j] = ""; obj[j+1] = false; //
					 * tModel.addRow(obj); }
					 * 
					 * } else { JOptionPane.showMessageDialog(null,
					 * "Няма резултат от търсенето!"); } }
					 */

					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return data;
	}

}
