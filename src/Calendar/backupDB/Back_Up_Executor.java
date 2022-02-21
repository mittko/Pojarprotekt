//package Calendar.backupDB;
//
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import db.backup.BackUPDB;
//
//
//public class Back_Up_Executor extends SwingWorker {
//
//	
//	boolean isBackUp;
//	
//	public Back_Up_Executor() {
//		isBackUp = false;
//		
//	}
//   
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//    
//	}
//
//
//	@Override
//	protected Object doInBackground() throws Exception {
//		// TODO Auto-generated method stub
//		try {
//
//	    isBackUp = BackUPDB.backUpDatabase();
//	
//			
//		} finally {
//			SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					
//					if(isBackUp) {
//						JOptionPane.showMessageDialog(null, "Копирането завърши успешно !");
//					}
//				}
//				
//			});
//		}
//		return null;
//	}
//
// }
