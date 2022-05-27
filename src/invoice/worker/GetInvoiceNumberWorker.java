package invoice.worker;

import javax.swing.SwingWorker;

import db.Invoice.InvoiceNumber;

public class GetInvoiceNumberWorker extends SwingWorker{

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		String invoiceNumber = InvoiceNumber.getInvoiceNumber();
		return invoiceNumber;
	}

}
