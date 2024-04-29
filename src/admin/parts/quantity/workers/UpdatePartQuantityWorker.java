package admin.parts.quantity.workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.Part_Quantity_DB.Part_Quantity;

public class UpdatePartQuantityWorker extends SwingWorker {

	String part;
	int quantity;
	JDialog jd;
	public UpdatePartQuantityWorker(String part,int quantity, JDialog jd) {
		this.part = part;
		this.quantity = quantity;
		this.jd = jd;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		int update = 0;
		try {
		
		update =  Part_Quantity.increaseQuantity(part, quantity);// this method insert quantity-> Part_Quantity.insertPartIntoQuantity(part, quantity);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
		}
		return update;
	}

}
