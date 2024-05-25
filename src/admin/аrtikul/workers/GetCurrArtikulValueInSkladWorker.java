package admin.àrtikul.workers;

import javax.swing.SwingWorker;

import db.àrtikul.Artikuli_DB;

public class GetCurrArtikulValueInSkladWorker extends SwingWorker {
	private String artikul;
	private String dbTable;

	/*
	 * private String kontragent; private String invoiceByKontragent;
	 */

	public GetCurrArtikulValueInSkladWorker(String dbTable,String artikul/*
														 * , String kontragent,
														 * String
														 * invoiceByKontragent
														 */) {
		super();
		this.dbTable = dbTable;
		this.artikul = artikul;
		/*
		 * this.kontragent = kontragent; this.invoiceByKontragent =
		 * invoiceByKontragent;
		 */
	}

	@Override
	public Double doInBackground() {
		// TODO Auto-generated method stub
		// do request from db
		return Artikuli_DB.getCurrValueForArtikulInAvailables(dbTable,artikul/*
																	 * ,
																	 * kontragent
																	 * ,
																	 * invoiceByKontragent
																	 */);
	}

}
