//package admin.Artikul.Workers;
//
//import java.awt.Cursor;
//
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import db.admin.Artikul.Artikuli_DB;
//
//public class DecreaseArtikulWorker extends SwingWorker {
//
//	private String artikulItem;
//	private int quantity;
//	private JDialog jd;
//    private int decrease = 0;
//    
//	public DecreaseArtikulWorker(String artikulItem, int quantity, JDialog jd) {
//		this.artikulItem = artikulItem;
//		this.quantity = quantity;
//		this.jd = jd;
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected Object doInBackground() throws Exception {
//		// TODO Auto-generated method stub
//		try {
//			
//			decrease = Artikuli_DB.decreaseArtikul_Quantity(artikulItem,
//					quantity);
//		} finally {
//			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			
//			if (decrease > 0) {
//				SwingUtilities.invokeLater(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						JOptionPane.showMessageDialog(null,
//								"Данните са записани успешно!");
//					}
//
//				});
//			}
//		}
//		return null;
//	}
//
//}
