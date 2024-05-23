package workingbook.workers;

import db.Protokol.ProtokolNumber;
import workingbook.Brack;
import workingbook.WorkingBook;
import db.Brack.ProtBrackTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SaveInBrackWorker extends SwingWorker {
	private int result = 0;
	private JDialog jd = null;




	public SaveInBrackWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub

		final String protocolBrackNumberAsString = ProtokolNumber.getProtokolCount(MainPanel.BRACK);

		try {


				for (int row = 0; row < Brack.dtm_Scrab.getRowCount(); row++) {
					result = ProtBrackTable.insertIntoBrackTableDB(
							Brack.dtm_Scrab.getValueAt(row, 0).toString(),// client
							Brack.dtm_Scrab.getValueAt(row, 1).toString(), // type
							Brack.dtm_Scrab.getValueAt(row, 2).toString(), // wheight
							Brack.dtm_Scrab.getValueAt(row, 3).toString(), // barcod
							Brack.dtm_Scrab.getValueAt(row, 4).toString(), // serial
							Brack.dtm_Scrab.getValueAt(row, 5).toString(), // category
							Brack.dtm_Scrab.getValueAt(row, 6).toString(), // brand
							getReasons(row), protocolBrackNumberAsString,
							MainPanel.personName, MyGetDate.getReversedSystemDate());
					if (result <= 0) {
						break;
					} else {
						String del = Brack.dtm_Scrab.getValueAt(row, 4)
								.toString();
						ProtBrackTable.deleteExtinguisher(del,
								MainPanel.SERVICE);
						ProtBrackTable.deleteExtinguisher(del,
								MainPanel.PROTOKOL);
					}
				}
				// update to new brack number

				
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (result > 0) {
						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");
						Brack.printServiceButton.setEnabled(true);
					}

				}

			});
		}
		return protocolBrackNumberAsString;
	}

	public String getReasons(int row) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Object> value = WorkingBook.reasons_map.get(getKey(row));
		for (int i = 0; i < value.size(); i++) {
			sb.append(value.get(i));
			if (i + 1 < value.size()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	public String getKey(int row) {
		StringBuilder sb = new StringBuilder();
		sb.append(Brack.dtm_Scrab.getValueAt(row, 3));// barcod
		return sb.toString();

	}
}
