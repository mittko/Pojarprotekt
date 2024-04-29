//package NewExtinguisher.SwingWorkers;
//
//import java.awt.Cursor;
//
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import NewExtinguisher.NewExtinguisher2;
//import db.SerialNumber.SerialTable;
//
//public class SerialWorker extends SwingWorker {
//	
//	private final SerialTable bt = new SerialTable();
//	private String serial = null; // curr serial
//	private JDialog jd = null;
//	private int currentRow = 0;
//	
//	public SerialWorker(JDialog jDialog,int currentRow) {
//		this.jd = jDialog;
//		this.currentRow = currentRow;
//	}
//	@SuppressWarnings("finally")
//	@Override
//	protected Boolean doInBackground() throws Exception {
//		// TODO Auto-generated method stub
//		if (currentRow >= NewExtinguisher2.dftm.getRowCount()) {
//			return false;
//		}
//		if(!NewExtinguisher2.dftm.getValueAt(currentRow, 3).equals("")) { // serial
//			JOptionPane.showMessageDialog(null, "Вече е въведен номер!");
//			return false;
//		}
//	  
//		
//		try {
//			
//			serial = bt.updateSerial(); // get and update serial
//									// from db
//		} finally {
//
//			SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//						if (!serial.equals(null)) {
//						NewExtinguisher2.dftm.setValueAt(serial, currentRow,
//								3);
//						}
//						
//					  } 
//					
//				});
//			} 
//			return true;
//		}
//	};
//
