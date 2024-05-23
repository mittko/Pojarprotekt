package invoice.workers;

import db.Invoice.InvoiceChildDB;
import db.Invoice.InvoiceParent_DB;
import mydate.MyGetDate;
import utils.BevelLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static utils.MainPanel.INVOICE_CHILD;
import static utils.MainPanel.INVOICE_PARENT;

public class SaveFiskalBonInInvoiceDBWorker extends SwingWorker {

	private int child = 0;
	private JDialog jd = null;
	private String payment = null;
	private String discount = null;
	private String sum = null;
	private String currentClient = null;
	private String personName = null;
	private String date = null;
	private String PROTOKOL_NUMBER = null;
	private final DefaultTableModel dftm;

	public SaveFiskalBonInInvoiceDBWorker(
			JDialog jd, String payment,
			String discount, String sum, String currentClient,
			String personName, String date,
			String protokolNumber, DefaultTableModel dftm) {
		this.jd = jd;
		this.payment = payment;
		this.discount = discount;
		this.sum = sum;
		this.currentClient = currentClient;// taken from combobox
		this.personName = personName;
		this.date = date;
		this.PROTOKOL_NUMBER = protokolNumber;
		this.dftm = dftm;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		String fiskalOfficialNumber = MyGetDate
				.generateFiskalBonNumber();

		int parent = 0;
		try {

			// save data in invoice parent

			parent = InvoiceParent_DB.insertIntoInvoiceParent(getParentTable(),
					fiskalOfficialNumber,// invoice  number
					PROTOKOL_NUMBER, payment, // payment
					discount, // discount
					sum, // final sum
					currentClient, // client
					personName, // saller
					date); // date

			// save data in invoice child
			if (parent > 0) {
				for (int row = 0; row < dftm.getRowCount(); row++) {

					child = InvoiceChildDB.insertIntoInvoiceChild(getChildTable(),
							fiskalOfficialNumber, dftm.getValueAt(row, 0).toString(), // make
							dftm.getValueAt(row, 1).toString(), // med
							dftm.getValueAt(row, 2).toString(), // quantity
							dftm.getValueAt(row, 3).toString(), // one price
							dftm.getValueAt(row, 4).toString(), // value
							currentClient, dftm.getValueAt(row, 6).toString(), // kontragent
							dftm.getValueAt(row, 7).toString()); // invoiceByKontragent

				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return parent > 0 && child > 0;
	}

	private String getParentTable() {
		return INVOICE_PARENT;
	}
	private String getChildTable() {
		return INVOICE_CHILD;
	}


}
