package admin.SkladExtinguisher.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.NewExtinguisher.NewExtinguishers_DB;

public class UpdatePriceNewExtinguisherWorker extends SwingWorker {

	private String price = null;
	private String percentProfit = null;
	private String type = null;
	private String wheight = null;
	private String category = null;
	private String brand = null;
	private String kontragent = null;
	private String invoiceByKontragent = null;

	private int updateIntoNewExtinguishers = 0;
	private int updateIntoDeliveryArtikuls = 0;
	private JDialog jd = null;

	public UpdatePriceNewExtinguisherWorker(String price, String percentProfit,
			String type, String wheight, String category, String brand,
			String kontragent, String invoiceByKontragent, JDialog jd) {
		super();
		this.price = price;
		this.percentProfit = percentProfit;
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.kontragent = kontragent;
		this.invoiceByKontragent = invoiceByKontragent;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			updateIntoNewExtinguishers = NewExtinguishers_DB
					.updatePriceInNewExtinguishers(type, wheight, category,
							brand, price, percentProfit, kontragent,
							invoiceByKontragent);

			// не е необходимо да се променя стойност в доставки , достатъчно е
			// да се промени стойноста и процент печалба в налични !!!
			// updateIntoDeliveryArtikuls = NewExtinguishers_DB
			// .updatePriceNewExtinguishersInDelivery(type + " ( Нов ) "
			// + wheight, price, kontragent, invoiceByKontragent);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (updateIntoNewExtinguishers > 0) {
						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}
}