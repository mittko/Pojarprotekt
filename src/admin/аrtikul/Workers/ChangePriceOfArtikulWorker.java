//package admin.Artikul.Workers;
//
//import java.awt.Cursor;
//
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import admin.Artikul.AvailableArtikulsTable;
//import db.admin.Artikul.Artikuli_DB;
//
//public class ChangePriceOfArtikulWorker extends SwingWorker {
//	private String value = null;
//	String percentProfit = null;
//	private String artikul = null;
//	private int update = 0;
//	private JDialog jd = null;
//
//	public ChangePriceOfArtikulWorker(JDialog jd) {
//		this.jd = jd;
//	}
//
//	@Override
//	protected Object doInBackground() throws Exception {
//		// TODO Auto-generated method stub
//		try {
//
//			artikul = AvailableArtikulsTable.table.getValueAt(
//					AvailableArtikulsTable.CURRENT_ROW, 0).toString();// artikul
//			value = AvailableArtikulsTable.table.getValueAt(
//					AvailableArtikulsTable.CURRENT_ROW, 3).toString(); // value;
//
//			update = Artikuli_DB.updateArtikul_ValueInAvailable(artikul, value,
//					percentProfit);
//		} finally {
//			SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					if (update > 0) {
//						JOptionPane.showMessageDialog(null,
//								"Промените са записани успешно!");
//					}
//					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//				}
//
//			});
//		}
//		return null;
//	}
//
// }
