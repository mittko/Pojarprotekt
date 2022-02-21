package admin.Artikul.Workers;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.Artikul.AvailableArtikulsTable;
import db.Artikul.Artikuli_DB;

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

			data = Artikuli_DB.getAllAvailableArtikuls();
			AvailableArtikulsTable.helpSearchFieldList.clear();
			for (int d = 0; d < data.size(); d++) {
				Object[] obj = new Object[] { data.get(d)[0], data.get(d)[1],
						data.get(d)[2], data.get(d)[3], data.get(d)[4],
						data.get(d)[5], data.get(d)[6], data.get(d)[7],
						data.get(d)[8] };
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

						for (int d = 0; d < data.size(); d++) { // d <
																// data.size()

							Object[] obj = new Object[] { data.get(d)[0],
									data.get(d)[1], data.get(d)[2],
									data.get(d)[3], data.get(d)[4],
									data.get(d)[5], data.get(d)[6],
									data.get(d)[7], data.get(d)[8] };
							/*
							 * ps.println(data.get(d)[0] + "      " +
							 * data.get(d)[1] + " " + data.get(d)[2] +
							 * "          Цена  " + data.get(d)[3]); ps.println(
							 * "--------------------------------------------------------------------------------------------"
							 * );
							 */AvailableArtikulsTable.artikulTableModel
									.addRow(obj);
							// ArtikulsMainFrame.helpSearchFieldList.add(obj);
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
