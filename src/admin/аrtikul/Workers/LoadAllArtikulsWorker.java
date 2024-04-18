package admin.àrtikul.Workers;

import admin.àrtikul.AvailableArtikulsTable;
import db.àrtikul.Artikuli_DB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static utils.MainPanel.AVAILABLE_ARTIKULS;

public class LoadAllArtikulsWorker extends SwingWorker {
	private JDialog jd = null;
	private ArrayList<Object[]> data = null;

	public LoadAllArtikulsWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			data = Artikuli_DB.getAllAvailableArtikuls(AVAILABLE_ARTIKULS);
			AvailableArtikulsTable.helpSearchFieldList.clear();
			for (Object[] datum : data) {
				Object[] obj = new Object[]{/*datum[9],*/datum[0], datum[1],
						datum[2], datum[3], datum[4],
						datum[5], datum[6], datum[7],
						datum[8]};
				AvailableArtikulsTable.helpSearchFieldList.add(obj);
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (data != null && data.size() > 0) {
						// first clear content
						if (AvailableArtikulsTable.artikulTableModel
								.getRowCount() > 0)
							AvailableArtikulsTable.artikulTableModel
									.setRowCount(0);
						/*
						 * Collections.sort(data, new Comparator<Object[]>() {
						 * 
						 * @Override public int compare(Object[] arg0, Object[]
						 * arg1) { // TODO Auto-generated method stub return
						 * arg0[0].toString().toLowerCase().
						 * compareTo(arg1[0].toString().toLowerCase()); }
						 * 
						 * }); PrintStream ps = null; try { ps = new
						 * PrintStream("artikuli.txt"); } catch
						 * (FileNotFoundException e) { // TODO Auto-generated
						 * catch block e.printStackTrace(); }
						 */
						// init values
						// ArtikulsMainFrame.helpSearchFieldList.clear();

						// try to optimize to split painting of parts

						for (Object[] datum : data) { // d <
							// data.size()

							Object[] obj = new Object[]{
								/*	datum[9],*/
									datum[0],
									datum[1], datum[2],
									datum[3], datum[4],
									datum[5], datum[6],
									datum[7], datum[8]};
							/*
							 * ps.println(data.get(d)[0] + "      " +
							 * data.get(d)[1] + " " + data.get(d)[2] +
							 * "          Öåíà  " + data.get(d)[3]); ps.println(
							 * "--------------------------------------------------------------------------------------------"
							 * );
							 */
							AvailableArtikulsTable.artikulTableModel
									.addRow(obj);
							// ArtikulsMainFrame.helpSearchFieldList.add(obj);
						}

						// ps.close();
					} else {
						JOptionPane.showMessageDialog(null,
								"Íÿìà ðåçóëòàò îò òúðñåíåòî!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
