package admin.Discount.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.Discount.UpdateDiscount;
import db.Discount.DiscountDB;

public class changeDiscountOfWorker extends SwingWorker  {

	private JDialog jd;
	private String client;
	private String discount;
	private int update = 0;
	
	public changeDiscountOfWorker(JDialog jd,String client,String discount) {
		this.jd = jd;
		this.client = client;
		this.discount = discount;
	}
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			update = DiscountDB.updateDiscount(client, discount);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(update == 1) {
						UpdateDiscount.changeField.setText("");
						UpdateDiscount.viewLabel.setText("");
						JOptionPane.showMessageDialog(null, "Промените бяха записани успешно!");
					} 
				}
				
			});
		}
		return update;
	}
	

}
