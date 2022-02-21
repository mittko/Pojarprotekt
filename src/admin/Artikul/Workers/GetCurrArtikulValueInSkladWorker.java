package admin.Artikul.Workers;

import javax.swing.SwingWorker;

import db.Artikul.Artikuli_DB;

public class GetCurrArtikulValueInSkladWorker extends SwingWorker {
	private String artikul;

	/*
	 * private String kontragent; private String invoiceByKontragent;
	 */

	public GetCurrArtikulValueInSkladWorker(String artikul/*
														 * , String kontragent,
														 * String
														 * invoiceByKontragent
														 */) {
		super();
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
		return Artikuli_DB.getCurrValueForArtikulInAvailables(artikul/*
																	 * ,
																	 * kontragent
																	 * ,
																	 * invoiceByKontragent
																	 */);
	}

}
