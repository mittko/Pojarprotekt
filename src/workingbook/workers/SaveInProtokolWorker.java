package workingbook.workers;

import workingbook.View;
import workingbook.WorkingBook;
import db.Part_Quantity_DB.Part_Quantity;
import db.Protokol.ProtokolNumber;
import db.Protokol.ProtokolTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class SaveInProtokolWorker extends SwingWorker {
	private int result = 0; // 1 -> success 0 -> failure
	private final JDialog jd;
	private final TreeMap<Object,Integer> partQuantityMap;

	public SaveInProtokolWorker(JDialog jd, TreeMap<Object,Integer> partQuantityMap) {
		this.jd = jd;
		this.partQuantityMap = partQuantityMap;
	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub

		final String protocolNumberAsString = ProtokolNumber.getProtokolCount(MainPanel.PROTOKOL);

		try {

			int[] results = ProtokolTable.insertIntoProtokolTableDB(View.dtm_Extinguisher,
					protocolNumberAsString, // protokolNumber
					MainPanel.personName,
					MyGetDate.getReversedSystemDate());


			ArrayList<String> updateExtinguishersList = new ArrayList<String>();
			for (int row = 0; row < results.length; row++) {
				if (results[row] == 1) {
					updateExtinguishersList.add(View.dtm_Extinguisher.getValueAt(row, 3).toString());
					result = 1;
				}
			}


			if (result > 0) {

				// ???? Update Service Order
				ProtokolTable.setExtinguisherDoneWithBatch(updateExtinguishersList);// in service table

				//  UPDATE PARTS QUANTITY
				Part_Quantity.decreaseQuantityWithBatch(partQuantityMap);

			}

		} finally {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (result > 0) {
						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");
						View.printProtokolButton.setEnabled(true);
						WorkingBook.CURRENT_CLIENT = "";
					}

				}

			});
		}
		return protocolNumberAsString;
	}
}
