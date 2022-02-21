package admin.Artikul.Workers;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.Artikul.Artikuli_DB;

public class ChangeArtikulQuantityWorker extends SwingWorker {

	private String quantity = null;
	private String artikul = null;
	private String kontragent = null;
	private String invoiceByKontragent = null;
	private int update = 0;
	private JDialog jd = null;

	public ChangeArtikulQuantityWorker(String artikul, String quantity,
			String kontragent, String invoiceByKontragent) {
		this.artikul = artikul;
		this.quantity = quantity;
		this.kontragent = kontragent;
		this.invoiceByKontragent = invoiceByKontragent;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
			// artikul =
			// AvailableArtikulsTable.table.getValueAt(AvailableArtikulsTable.CURRENT_ROW,
			// 0).toString();// artikul
			// quantity =
			// AvailableArtikulsTable.table.getValueAt(AvailableArtikulsTable.CURRENT_ROW,
			// 1).toString(); // value;
			update = // Artikuli_DB.increaseArtikulQuantity(artikul,Integer.parseInt(quantity));
			Artikuli_DB.editArtikulQuantity(artikul, quantity, kontragent,
					invoiceByKontragent);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (update > 0) {
						JOptionPane.showMessageDialog(null,
								"Промените са записани успешно!");
					}
					// jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
