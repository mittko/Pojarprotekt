package admin.аrtikul.workers;

import db.аrtikul.Artikuli_DB;

import javax.swing.*;
import java.awt.*;

public class DeleteArtikulWorker extends SwingWorker {

	private JDialog jd = null;
	private int delete = 0;
	private int delete2 = 0;
	private int[] selectedToDelete = null;
	private String item = null;
	private String kontragent;
	private String invoice;
    private String dbTable;
	public DeleteArtikulWorker(JDialog jd, int[] selectedToDelete) {
		this.jd = jd;
		this.selectedToDelete = selectedToDelete;
	}

	public DeleteArtikulWorker(String dbTable,JDialog jd, String item, String kontragent,
			String invoice) {
		this.dbTable = dbTable;
		this.jd = jd;
		this.item = item;
		this.kontragent = kontragent;
		this.invoice = invoice;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			// System.out.println(item);
			delete = Artikuli_DB.deleteArtikulFromAvailbale(dbTable,item, kontragent,
					invoice);
			delete2 = Artikuli_DB.deleteArtikulFromDelivery(item, kontragent,
					invoice);
			// for(int i = 0;i < selectedToDelete.length;i++) {
			// String itemToDelete =
			// ArtikulsMainFrame.artikulTableModel.getValueAt(selectedToDelete[i],
			// 0).toString();
			// delete = Artikuli_DB.deleteArtikuls(itemToDelete);
			// }
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (delete > 0 && delete2 > 0) {
						JOptionPane.showMessageDialog(null,
								"Артикулът е изтрит успешно!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}
}
