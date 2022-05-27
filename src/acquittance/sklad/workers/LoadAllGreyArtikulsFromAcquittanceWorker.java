package acquittance.sklad.workers;

import acquittance.sklad.GreySkladArtikulFrame2;
import db.Artikul.Artikuli_DB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static utility.MainPanel.GREY_AVAILABLE_ARTIKULS;

public class LoadAllGreyArtikulsFromAcquittanceWorker extends SwingWorker {

	private ArrayList<Object[]> result = null;
	private final JDialog jd;

	public LoadAllGreyArtikulsFromAcquittanceWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		// do request from db
		try {

			result = Artikuli_DB.getAvailableArtikuls(GREY_AVAILABLE_ARTIKULS);
			if (result == null) {
				return null;
			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// if is success
					if (result.size() > 0) {

						// sorting
						Collections.sort(result, new Comparator<Object[]>() {
							@Override
							public int compare(Object[] fruit2, Object[] fruit1) {

								return fruit2[0]
										.toString()
										.toLowerCase()
										.compareTo(
												fruit1[0].toString()
														.toLowerCase());
							}
						});

						GreySkladArtikulFrame2.skladModel.setRowCount(0);
						GreySkladArtikulFrame2.DATA_LIST.clear();

						for (Object[] objects : result) {
							// set in the table imediatelly
							// String newValue = String.format(Locale.ROOT,
							// "%.2f",
							// Double.parseDouble(result.get(row)[3].toString().replace(",",
							// ".")));// format to two decimal places after
							// points
							//
							GreySkladArtikulFrame2.skladModel.addRow(new Object[]{
									objects[0], // артикул
									objects[1], // количество
									objects[2], // м.ед
									objects[3], // ед.цена
									"", // value to add
									false, objects[4],// фактура
									objects[5] // контрагент});
							});
							// store data in list to change table dinamically
							GreySkladArtikulFrame2.DATA_LIST.add(new Object[]{
									objects[0], // артикул
									objects[1], // количество
									objects[2], // м.ед
									objects[3], // ед.цена
									"", // value to add
									false, objects[4],// фактура
									objects[5] // контрагент});
							});
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Няма намерени резултати!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return null;
	}

}

