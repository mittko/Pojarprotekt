package admin.parts.quantity.workers;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import db.Part_Quantity_DB.Part_Quantity;

public class SeePartsQuantityWorker extends SwingWorker {

	DefaultTableModel dftm;
	JDialog jd;
	ArrayList<Object[]> obj;
	public SeePartsQuantityWorker(DefaultTableModel dftm, JDialog jd) {
		this.dftm = dftm;
		this.jd = jd;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
	
		/*if(dftm.getRowCount() > 0) 
			return null;*/
	
		try {
		
	
		obj = Part_Quantity.seePartsQuantity();
      
		} finally {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(dftm.getRowCount() > 0) {
					dftm.setRowCount(0);
				}
				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
				if(obj != null) {
						for(int i = 0;i < obj.size();i++) {
							dftm.addRow(obj.get(i));
					}
				}
			}
			
		});
		}
		return null;
	}

}
