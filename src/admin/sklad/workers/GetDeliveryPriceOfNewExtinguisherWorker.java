package admin.sklad.workers;

import javax.swing.SwingWorker;

import db.àrtikul.Artikuli_DB;

public class GetDeliveryPriceOfNewExtinguisherWorker extends SwingWorker {

	private String artikulInDeliveryDB;

	/*
	 * private String kontragent; private String invoiceByKontragent;
	 */

	public GetDeliveryPriceOfNewExtinguisherWorker(String artikulInDeliveryDB,
			String kontragent, String invoiceByKontragent) {
		super();
		this.artikulInDeliveryDB = artikulInDeliveryDB;
		/*
		 * this.kontragent = kontragent; this.invoiceByKontragent =
		 * invoiceByKontragent;
		 */
	}

	@Override
	public String doInBackground() {
		// TODO Auto-generated method stub
		String deliveryPrice = Artikuli_DB
				.getDeliveryValueForArtikul(artikulInDeliveryDB/*
																 * , kontragent,
																 * invoiceByKontragent
																 */);
		return deliveryPrice;
	}

}
