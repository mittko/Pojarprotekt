package invoice.worker;

import generators.InvoiceGenerator;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import run.JDialoger;
import utility.BevelLabel;
import invoice.PrintInvoiceDialog;
import db.Invoice.InvoiceNumber;
import db.Proform.ProformChildDB;
import db.Proform.ProformParent_DB;

public class SaveInProformDBWorker extends SwingWorker {
	private int updateProformNumber = 0;
	private int parentProform = 0;
	private int childProform = 0;
	private final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
	private JDialog jd = null;
	private String payment = null;
	private String discount = null;
	private String sum = null;
	private String currentClient = null;
	private String personName = null;
	private String date = null;
	private final String invoiceName;
	private String PROFORM_NUMBER = null;
	private String PROTOKOL_NUMBER = null;
	private int[] next_proform = null;
	private DefaultTableModel dftm;
	private BevelLabel numLabel;

	public SaveInProformDBWorker(JDialog jd, String payment, String discount,
			String sum, String currentClient, String personName, String date,
								 String invoiceName, String proformNumber,
								 String protokolNumber, DefaultTableModel dftm, BevelLabel numLabel) {
		this.jd = jd;
		this.payment = payment;
		this.discount = discount;
		this.sum = sum;
		this.currentClient = currentClient;
		this.personName = personName;
		this.date = date;
		this.invoiceName = invoiceName;
		this.PROFORM_NUMBER = proformNumber;
		this.PROTOKOL_NUMBER = protokolNumber;
		this.dftm = dftm;
		this.numLabel = numLabel;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			parentProform = ProformParent_DB.insertIntoProformParent(
					PROFORM_NUMBER, //
					PROTOKOL_NUMBER, payment, // начин на плащане
					discount, // отстъпка
					sum, // крайна цена
					currentClient, // клиент
					personName, // продавач
					date,invoiceName); // дата

			// save data in invoice child
			if (parentProform > 0) {
				for (int row = 0; row < dftm.getRowCount(); row++) {
					childProform = ProformChildDB.insertIntoProformChild(
							PROFORM_NUMBER,
							dftm.getValueAt(row, 0).toString(), // make
							dftm.getValueAt(row, 1).toString(), // med
							dftm.getValueAt(row, 2).toString(), // quantity
							dftm.getValueAt(row, 3).toString(), // one
																// price
							dftm.getValueAt(row, 4).toString(), // value
							currentClient, dftm.getValueAt(row, 6).toString(),
							dftm.getValueAt(row, 7).toString()); //
					// client
				}
			}

			// update proform number
			if (parentProform > 0 && childProform > 0) {
				next_proform = invoiceGenerator
						.updateProformInvoice(PROFORM_NUMBER);
				updateProformNumber = InvoiceNumber
						.updateProformNumber(invoiceGenerator
								.digitsToString(next_proform));
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					if (parentProform > 0 && childProform > 0) {

						if (updateProformNumber > 0) {

							if (numLabel != null) { // ако се извиква само от
													// артикули може да е null
								numLabel.setName(invoiceGenerator
										.digitsToString(next_proform));
							}

							showPrintDialog(PROFORM_NUMBER);

						}

					}
				}

			});

		}
		return parentProform > 0 && childProform > 0;
	}

	private void showPrintDialog(String proformNumber) {
		// call print dialog
		PrintInvoiceDialog pd = new PrintInvoiceDialog(dftm, null, // invoice
																	// number
				proformNumber, // proform number
				null, // acquittance number
				currentClient, date,"invoiceName", Double.parseDouble(sum), payment, false, // print
																				// protokol
				true, // print proform
				false); // print acquittance

		JDialoger jDialoger = new JDialoger();
		jDialoger.setContentPane(pd);
		jDialoger.Show();
	}

}
