package Invoice.worker;

import generators.InvoiceGenerator;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import run.JDialoger;
import utility.BevelLabel;
import Invoice.PrintInvoiceDialog;
import db.Invoice.InvoiceChildDB;
import db.Invoice.InvoiceNumber;
import db.Invoice.InvoiceParent_DB;

public class SaveInInvoiceDBWorker extends SwingWorker {
	private int updateInvoiceNumber = 0;
	private int parent = 0;
	private int child = 0;
	private final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
	private JDialog jd = null;
	private String payment = null;
	private String discount = null;
	private String sum = null;
	private String currentClient = null;
	private String personName = null;
	private String date = null;
	private String INVOICE_NUMBER = null;
	private String PROTOKOL_NUMBER = null;
	private int[] next_invoice = null;
	private DefaultTableModel dftm;
	private BevelLabel numLabel;

	public SaveInInvoiceDBWorker(JDialog jd, String payment, String discount,
			String sum, String currentClient, String personName, String date,
			String invoiceNumber, String protokolNumber,
			DefaultTableModel dftm, BevelLabel numLabel) {
		this.jd = jd;
		this.payment = payment;
		this.discount = discount;
		this.sum = sum;
		this.currentClient = currentClient;// taken from combobox
		this.personName = personName;
		this.date = date;
		this.INVOICE_NUMBER = invoiceNumber;
		this.PROTOKOL_NUMBER = protokolNumber;
		this.dftm = dftm;
		this.numLabel = numLabel;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			// save data in invoice parent

			parent = InvoiceParent_DB.insertIntoInvoiceParent(INVOICE_NUMBER,// invoice
																				// number
					PROTOKOL_NUMBER, payment, // payment
					discount, // discount
					sum, // final sum
					currentClient, // client
					personName, // saller
					date); // date

			// save data in invoice child
			if (parent > 0) {
				for (int row = 0; row < dftm.getRowCount(); row++) {

					child = InvoiceChildDB.insertIntoInvoiceChild(
							INVOICE_NUMBER, dftm.getValueAt(row, 0).toString(), // make
							dftm.getValueAt(row, 1).toString(), // med
							dftm.getValueAt(row, 2).toString(), // quantity
							dftm.getValueAt(row, 3).toString(), // one price
							dftm.getValueAt(row, 4).toString(), // value
							currentClient, dftm.getValueAt(row, 6).toString(), // kontragent
							dftm.getValueAt(row, 7).toString()); // invoiceByKontragent

				}
			}
			if (parent > 0 && child > 0) {
				// update invoice number
				next_invoice = invoiceGenerator.updateInvocie(INVOICE_NUMBER);

				updateInvoiceNumber = InvoiceNumber
						.updateInvoiceNumber(invoiceGenerator
								.digitsToString(next_invoice));
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (parent > 0 && child > 0) {

						if (updateInvoiceNumber > 0) {
							if (numLabel != null) { // ако се извиква само от
													// артикули може да е null
								numLabel.setName(invoiceGenerator
										.digitsToString(next_invoice));
							}
							// if it called from search from protokol panel
							// clearProtokolDetails();

							showPrintDialog(INVOICE_NUMBER);

						}
					}

				}

			});
		}
		if (parent > 0 && child > 0) {
			return true;
		}
		return false;
	}

	private void showPrintDialog(String invoiceNumber) {
		PrintInvoiceDialog pd = new PrintInvoiceDialog(dftm, invoiceNumber, // invoice
																			// number
				null, // proform number
				null, // acquittance number
				currentClient, date, Double.parseDouble(sum), payment, true, // print
																				// protokol
				false,// print proform
				false); // print acquittance

		JDialoger jDialoger = new JDialoger();
		jDialoger.setContentPane(pd);
		jDialoger.Show();
	}

}
