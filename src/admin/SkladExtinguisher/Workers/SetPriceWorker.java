//package admin.SkladExtinguisher.Workers;
//
//import java.awt.Cursor;
//
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import utility.MainPanel;
//import db.NewExtinguisher.NewExtinguishers_DB;
//
//public class SetPriceWorker extends SwingWorker {
//
//	  private String type = null;
//	  private String wheight = null;
//	  private String category = null;
//	  private String brand = null;
//	  private String price = null;
//	  private int update = 0;
//	  private JDialog jd = null;
//	  
//	  public SetPriceWorker(String type,String wheight,String category,String brand,String price,JDialog jd) {
//		  this.type = type;
//		  this.wheight = wheight;
//		  this.category = category;
//		  this.brand = brand;
//		  this.price = price;
//		  this.jd = jd;
//	  }
//	@Override
//	protected Object doInBackground() throws Exception {
//		// TODO Auto-generated method stub
//		  
//		    jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		   
//		    try {
//		 
//			update = NewExtinguishers_DB.updatePrice(MainPanel.NEW_EXTINGUISHERS, type, wheight, category, brand, price);
//		    } finally {
//			SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					if(update > 0) {
//						JOptionPane.showMessageDialog(null, "Данните са записани успешно!");
//					}
//					 jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//				}
//				
//			});
//		}
//		return null;
//	}
//	
//}
//
