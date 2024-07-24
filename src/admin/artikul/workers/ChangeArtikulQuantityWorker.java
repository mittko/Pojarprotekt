package admin.artikul.workers;

import db.artikul.Artikuli_DB;

import javax.swing.*;

public class ChangeArtikulQuantityWorker extends SwingWorker {

	private String quantity = null;
	private String artikul = null;
	private String kontragent = null;
	private String invoiceByKontragent = null;
	private int update = 0;
	private final JDialog jd = null;
    private String dbTable;
	public ChangeArtikulQuantityWorker(String dbTable,String artikul, String quantity,
			String kontragent, String invoiceByKontragent) {
		this.dbTable = dbTable;
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
			Artikuli_DB.editArtikulQuantity(dbTable,artikul, quantity, kontragent,
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
