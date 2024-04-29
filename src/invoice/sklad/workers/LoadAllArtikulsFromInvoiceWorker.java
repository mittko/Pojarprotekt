package invoice.sklad.workers;

import invoice.sklad.ILoadArtikuls;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoadAllArtikulsFromInvoiceWorker extends SwingWorker {
	
	private ArrayList<Object[]> result = null;
	private JDialog  jd;

	private final ILoadArtikuls iLoadArtikuls;
	
	public LoadAllArtikulsFromInvoiceWorker(ILoadArtikuls iLoadArtikuls,JDialog jd) {
		this.iLoadArtikuls = iLoadArtikuls;
		this.jd = jd;
	}
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		// do request from db
		try {

			result = iLoadArtikuls.getArtikuls();
			if (result == null) {
				return null;
			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// if is success
                     if(result.size() > 0) {
						 iLoadArtikuls.loadArtikuls(result);
                   } else {
                	   JOptionPane.showMessageDialog(null, "Няма намерени резултати!");
                   }
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return null;
	}
	
}
