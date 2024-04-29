package workingbook.workers;

import workingbook.Brack;
import workingbook.WorkingBook;
import db.Brack.BrackNumber;
import db.Brack.ProtBrackTable;
import generators.ProtokolGenerator;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SaveInBrackWorker extends SwingWorker {
	private int result = 0;
	private JDialog jd = null;
	private String BRACK_NUMBER = null;
	private final ProtokolGenerator pg = new ProtokolGenerator();
	private final BrackNumber bn = new BrackNumber();
	private int[] next_brack_number = null;

	public SaveInBrackWorker(JDialog jd, String brackNumber) {
		this.jd = jd;
		this.BRACK_NUMBER = brackNumber;
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
			// update brack number
			next_brack_number = pg.updateProtokol(BRACK_NUMBER);
			

				for (int row = 0; row < Brack.dtm_Scrab.getRowCount(); row++) {
					result = ProtBrackTable.insertIntoBrackTableDB(
							Brack.dtm_Scrab.getValueAt(row, 0).toString(),// client
							Brack.dtm_Scrab.getValueAt(row, 1).toString(), // type
							Brack.dtm_Scrab.getValueAt(row, 2).toString(), // wheight
							Brack.dtm_Scrab.getValueAt(row, 3).toString(), // barcod
							Brack.dtm_Scrab.getValueAt(row, 4).toString(), // serial
							Brack.dtm_Scrab.getValueAt(row, 5).toString(), // category
							Brack.dtm_Scrab.getValueAt(row, 6).toString(), // brand
							getReasons(row), BRACK_NUMBER,
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
				if(result > 0) {
				int update = bn.updateBrackNumber(pg
						.digitsToString(next_brack_number));
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
						Brack.printServiceButton.setEnabled(true);
						Brack.brackNumberLabel.setName(pg
								.digitsToString(next_brack_number));
					}

				}

			});
		}
		return null;
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
