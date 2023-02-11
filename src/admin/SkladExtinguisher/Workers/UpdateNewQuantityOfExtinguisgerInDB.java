package admin.SkladExtinguisher.Workers;

import db.NewExtinguisher.NewExtinguishers_DB;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;

public class UpdateNewQuantityOfExtinguisgerInDB extends SwingWorker {

	private String kontragent = null;
	private String invoiceByKontragent = null;
	private String type = null;
	private String wheight = null;
	private String category = null;
	private String brand = null;
	private int quantity = 0;
	private JDialog jd = null;
	private int update = 0;

	public UpdateNewQuantityOfExtinguisgerInDB(String kontragent,
			String invoiceByKontragent, String type, String wheight,
			String category, String brand, int quantity, JDialog jDialog) {
		this.kontragent = kontragent;
		this.invoiceByKontragent = invoiceByKontragent;
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.quantity = quantity;
		this.jd = jDialog;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			update = NewExtinguishers_DB.updateQuantity(
					MainPanel.NEW_EXTINGUISHERS, kontragent,
					invoiceByKontragent, type, wheight, category, brand,
					quantity);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (update > 0) {
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
