package admin.doing.worker;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.doing.UpdateWorkPrice;
import db.WorkPrice.WorkPriceDB;

public class seePriceOfDoingWorker extends SwingWorker {
	  String work;
	  double price;
	  JDialog jd;
	  public seePriceOfDoingWorker(JDialog jd,String work) {
		  this.jd = jd;
		  this.work = work;
	  }

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
		
		price = WorkPriceDB.getWorkValue(work);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					UpdateWorkPrice.viewField.setText(" " + price);
				}
				
			});
		}
		return null;
	}
}
