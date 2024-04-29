package invoice.workers;

import db.AcquittanceDB.AcquittanceChildDB;
import db.AcquittanceDB.AcquittanceNumber;
import db.AcquittanceDB.AcuittanceParentDB;
import generators.AcquittanceGenerator;
import invoice.PrintInvoiceDialog;
import run.JDialoger;
import utils.BevelLabel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SaveInAcquittanceWorker extends SwingWorker {

	private final DefaultTableModel dftm;

	private final String acquittanceNumber;
	private final double value;
	private final String saller;
	private final String client;
	private final String date;
	private final JDialog jd;
	private int updateParent;
	private int updateChild;
	private final BevelLabel numLabel;


	public SaveInAcquittanceWorker(DefaultTableModel dftm, String number,
			double value, String saller, String client, String date,
			BevelLabel numLabel, JDialog jd) {
		this.dftm = dftm;
		this.acquittanceNumber = number;
		this.value = value;
		this.saller = saller;
		this.client = client;
		this.date = date;
		this.numLabel = numLabel;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			// insert into parent acquittance
			updateParent = AcuittanceParentDB.insertIntoAcquittanceParrentDB(
					acquittanceNumber, value + "", client, saller, date);

			// insert into child acquittance
			if (updateParent > 0) {
				for (int row = 0; row < dftm.getRowCount(); row++) {
					updateChild = AcquittanceChildDB
							.insertIntoAcquittanceChildDB(acquittanceNumber, // acquittance
																				// number
									dftm.getValueAt(row, 0).toString(), // artikul
									dftm.getValueAt(row, 1).toString(), // med
									dftm.getValueAt(row, 2).toString(), // quantity
									dftm.getValueAt(row, 3).toString(), // price
									dftm.getValueAt(row, 4).toString(), // value
									client); // current client
				}
			}

			// update acquittance number
			if (updateParent > 0 && updateChild > 0) {
				int[] next_acquittance = AcquittanceGenerator
						.updateAcquittance(acquittanceNumber);
				int update = AcquittanceNumber
						.updateAcquittanceNumber(AcquittanceGenerator
								.digitsToString(next_acquittance));
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					if (updateParent > 0 && updateChild > 0) {
						if (numLabel != null) {
							numLabel.setName(acquittanceNumber);
						}
						// invoke print dialog
						showPrintDialog();

					}
				}

			});
		}
		return updateParent > 0 && updateChild > 0;
	}

	private void showPrintDialog() {
		// call print dialog
		PrintInvoiceDialog pd = new PrintInvoiceDialog(dftm, null, // invoice
																	// number
				null, // proform number
				acquittanceNumber, // acquittance number
				client, date, MyMath.round(value, 2), "заплащане", // payment
																	// this
																	// is
																	// no
																	// needed
																	// for
																	// acquittance
				false, // invoice
				false, // proform
				true); // acquittance

		JDialoger jDialoger = new JDialoger();
		jDialoger.setContentPane(pd);
		jDialoger.Show();
	}
}
