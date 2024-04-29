package admin.sklad.workers;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.аrtikul.Artikuli_DB;
import db.NewExtinguisher.NewExtinguishers_DB;

public class DeleteExtinguisherWorker extends SwingWorker {

	private String type;
	private String wheight;
	private String category;
	private String brand;
	private String invoice;
	private String kontragent;
	private int result = 0;
	private int result2 = 0;

	public DeleteExtinguisherWorker(String type, String wheight,
			String category, String brand, String invoice, String kontragent) {
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.invoice = invoice;
		this.kontragent = kontragent;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		result = NewExtinguishers_DB.deleteExtinguisher(type, wheight,
				category, brand, invoice, kontragent);

		result2 = Artikuli_DB.deleteArtikulFromDelivery(type + " ( Нов ) "
				+ wheight, kontragent, invoice);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (result > 0 && result2 > 0) {
					JOptionPane.showMessageDialog(null,
							"Пожарогасителят е изтрит успешно!\n");
				} else {
					JOptionPane.showMessageDialog(null,
							"Пожарогасителят не бе изтрит!\n");
				}
			}

		});

		return null;
	}

}
