package Invoice.worker;

import generators.AcquittanceGenerator;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import run.JDialoger;
import utility.BevelLabel;
import utility.MyMath;
import Invoice.PrintInvoiceDialog;
import db.AcquittanceDB.AcquittanceChildDB;
import db.AcquittanceDB.AcquittanceNumber;
import db.AcquittanceDB.AcuittanceParentDB;

public class SaveInAcquittanceWorker extends SwingWorker {

	private DefaultTableModel dftm;

	private String acquittanceNumber;
	private double value;
	private String saller;
	private String client;
	private String date;
	private JDialog jd;
	private int updateParent;
	private int updateChild;
	private int[] next_acquittance = null;
	private BevelLabel numLabel;

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
				next_acquittance = AcquittanceGenerator
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
		if (updateParent > 0 && updateChild > 0) {
			return true;
		}
		return false;
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
