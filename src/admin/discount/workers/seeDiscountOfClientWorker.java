package admin.discount.workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.discount.UpdateDiscount;
import db.Discount.DiscountDB;

public class seeDiscountOfClientWorker extends SwingWorker {

	private JDialog jd;
	private String client;
	private String discount = "";

	public seeDiscountOfClientWorker(JDialog jd,String client) {
		this.jd = jd;
		this.client = client;
	}

	@Override
	protected String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
           
			discount = DiscountDB.getDiscount(client);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					if (!discount.equals("")) {
						UpdateDiscount.viewLabel.setText(" " + discount + "%");
					}
					  jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return discount;
	}

}
