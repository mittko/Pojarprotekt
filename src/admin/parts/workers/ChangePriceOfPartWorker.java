package admin.parts.workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.PartsPrice.PriceTable;

public class ChangePriceOfPartWorker extends SwingWorker {

	 private String part;
	 private String type;
	 private String category;
	 private String wheight;
	 private double newPrice;
	 private int update = 0;
	 private JDialog jd = null;
	 
	 public ChangePriceOfPartWorker(String part,String type,String category,
			 String wheight,double newPrice,JDialog jd) {
		 this.part = part;
		 this.type = type;
		 this.category = category; 
		 this.wheight = wheight;
		 this.newPrice = newPrice;
		 this.jd = jd;
	 }
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		
		try {
			 update = PriceTable.updatePartsTableDB(part, type, category,wheight,newPrice);
			} finally {
			   SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(update > 0) {
						JOptionPane.showMessageDialog(null, "Промяната бе извършена успешно!");
					} else {
						JOptionPane.showMessageDialog(null, "Неуспешна операция!");
					}
				}
		   });
		}
		return update;
	}

}
