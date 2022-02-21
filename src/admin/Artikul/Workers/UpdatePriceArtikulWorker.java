package admin.Artikul.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.Artikul.Artikuli_DB;

public class UpdatePriceArtikulWorker extends SwingWorker {
	private String artikul = null;
	private String value = null;
	private String kontragent = null;
	private String invoiceByKontragent = null;
	private String newPercentProfit = null;

	private int updateIntoAvailableArtikuls = 0;
	private int updateIntoDeliveryArtikuls = 0;
	private JDialog jd = null;

	public UpdatePriceArtikulWorker(String artikul, String value,
			String newPercentProfit, String kontragent,
			String invoiceByKontragent, JDialog jd) {
		this.artikul = artikul;
		this.value = value;
		this.newPercentProfit = newPercentProfit;
		this.kontragent = kontragent;
		this.invoiceByKontragent = invoiceByKontragent;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			updateIntoAvailableArtikuls = Artikuli_DB
					.updateArtikul_ValueInAvailable(artikul, value,
							newPercentProfit, kontragent, invoiceByKontragent);
			// не е нужно да променяме стойностите в доставки , достатъчно е да
			// се изчисли нова цена и нов процент печалба
			// updateIntoDeliveryArtikuls = Artikuli_DB
			// .updateArtikul_ValueInDelivery(artikul, value, kontragent,
			// invoiceByKontragent);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (updateIntoAvailableArtikuls > 0) {
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