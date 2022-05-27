package admin.services;

import db.Artikul.Artikuli_DB;

import javax.swing.*;
import java.awt.*;

public class DeleteServicesWorker extends SwingWorker {

	private JDialog jd = null;
	private int delete = 0;
	private String item = null;
	private String kontragent;
	private String invoice;
    private String dbTable;
	public DeleteServicesWorker(JDialog jd, int[] selectedToDelete) {
		this.jd = jd;
	}

	public DeleteServicesWorker(String dbTable, JDialog jd, String item, String kontragent,
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

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (delete > 0) {
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
