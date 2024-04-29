package admin.àrtikul.workers;

import javax.swing.SwingWorker;

import db.àrtikul.Artikuli_DB;

public class BiggestPriceForInvoiceWorker extends SwingWorker {

	private String artikul;

	public BiggestPriceForInvoiceWorker(String artikul) {
		this.artikul = artikul;
	}

	@Override
	public Double doInBackground() {
		// TODO Auto-generated method stub
		// do request from db
		return Artikuli_DB.getBigPriceForArtikulInInvoice(artikul);
	}

}
