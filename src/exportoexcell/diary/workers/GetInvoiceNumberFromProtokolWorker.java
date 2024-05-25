package exportoexcell.diary.workers;

import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.SwingWorker;

import db.Invoice.InvoiceParent_DB;

public class GetInvoiceNumberFromProtokolWorker extends SwingWorker  {

	private TreeSet<String> protokolNumbers = null;
	
	public GetInvoiceNumberFromProtokolWorker(TreeSet<String> protokolNumbers) {
		// TODO Auto-generated constructor stub
		this.protokolNumbers = protokolNumbers;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap<String,String> doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return InvoiceParent_DB.getInvoiceNumbersFromProtokol(protokolNumbers);
	}

}
