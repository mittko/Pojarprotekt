package admin.àrtikul.Workers;

import javax.swing.SwingWorker;

import db.àrtikul.Artikuli_DB;

public class GetDeliveryValueForArtikul extends SwingWorker {

	private String artikul;

	/*
	 * private String kontragent; private String invoiceByKontragent;
	 */

	public GetDeliveryValueForArtikul(String artikul/*
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
	public String doInBackground() {
		// TODO Auto-generated method stub
		// do request from db
		return Artikuli_DB.getDeliveryValueForArtikul(artikul/*
															 * , kontragent,
															 * invoiceByKontragent
															 */);
	}

}
