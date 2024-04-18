package admin.àrtikul.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.àrtikul.Artikuli_DB;

public class SeeCurrentPriceOfArtikulWorker extends SwingWorker {
    
	private String value = null;
    private JDialog jd = null;
    private String item;
    
    public SeeCurrentPriceOfArtikulWorker(String item, JDialog jd) {
    	this.item = item;
    	this.jd = jd;
    }
	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
		value = Artikuli_DB.getArtikulPrice(item);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				/*	if(value != null) {
					ArtikulsMainFrame.artikulTableModel.setValueAt(value, ArtikulsMainFrame.CURRENT_ROW, 3);
					System.out.println(value);
				} else {
					System.err.println("Value is NULL!");
				}*/
				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	   }
		return value;
	}
	   
   }
