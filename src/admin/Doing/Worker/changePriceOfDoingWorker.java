package admin.Doing.Worker;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.Doing.UpdateWorkPrice;
import db.WorkPrice.WorkPriceDB;

public class changePriceOfDoingWorker extends SwingWorker {
	  String work;
	  double price;
	  int result = 0;
	  JDialog jd;
	  public changePriceOfDoingWorker(JDialog jd,String work,double price) {
		  this.jd = jd;
		  this.work = work;
		  this.price = price;
	  }

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
	
		result = WorkPriceDB.updateWorkValue(work, price);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				if(result > 0) {
					JOptionPane.showMessageDialog(null, "Промените са записани успешно!");
					UpdateWorkPrice.viewField.setText("");
				}
			}
			
		});
		return null;
	}
}
