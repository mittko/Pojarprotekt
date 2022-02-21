package admin.Parts.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import admin.Parts.Price.TablePanel;
import db.PartsPrice.PriceTable;

public class seeQuantityAndPriceOfPartWorker extends SwingWorker {
	   
	 private String part = null; 
	 private String type = null;
	 private String category = null;
	 private String wheight = null;
	 private double price = 0;
	 private JDialog jd = null;
	 
	 public seeQuantityAndPriceOfPartWorker(String part,String type,String category,
			 String wheight,JDialog jd) {
		 this.part = part;
		 this.type = type;
		 this.category = category;
		 this.wheight = wheight;
		 this.jd = jd;
	 }
	@Override
	protected Double doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
	
		try {
		 price = PriceTable.getPartPriceFromDB(part, type, category, wheight);
		} finally {
		   SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TablePanel.jTable.setValueAt(price,TablePanel.index, 4);
				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			   
		   });
		}
		 
		return price;
	}
	 
}
