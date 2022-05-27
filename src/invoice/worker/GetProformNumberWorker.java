package invoice.worker;

import javax.swing.SwingWorker;

import db.Invoice.InvoiceNumber;

public class GetProformNumberWorker extends SwingWorker {

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		String proformNumber = InvoiceNumber.getProformNumber();
		return proformNumber;
	}

}
