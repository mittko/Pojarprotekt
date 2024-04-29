package admin.�rtikul.workers;

import invoice.sklad.ILoadArtikuls;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoadAllArtikulsWorker extends SwingWorker {
	private JDialog jd;
	private ArrayList<Object[]> data = null;

	private final ILoadArtikuls iLoadArtikuls;

	public LoadAllArtikulsWorker(ILoadArtikuls iLoadArtikuls,JDialog jd) {
		this.iLoadArtikuls = iLoadArtikuls;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			data = iLoadArtikuls.getArtikuls();

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (data != null && data.size() > 0) {
						iLoadArtikuls.loadArtikuls(data);
					} else {
						JOptionPane.showMessageDialog(null,
								"���� �������� �� ���������!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
