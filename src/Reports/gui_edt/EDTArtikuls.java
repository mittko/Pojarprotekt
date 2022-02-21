package Reports.gui_edt;

import Reports.ReportTableArtikuls;
import run.JDialoger;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTArtikuls extends MainPanel implements Runnable {
	private ArrayList<Object[]> data = null;
	private JDialog jd;
	private String title;

	public EDTArtikuls(ArrayList<Object[]> data, JDialog jd, String title) {
		this.data = data;
		this.jd = jd;
		this.title = title;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (data == null) {
			return;
		}
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "Няма резултат от търсенето!");
			return;
		}
		ReportTableArtikuls art = new ReportTableArtikuls(data);
		/*
		 * for(int i = 0;i < data.size();i++) {
		 * AcquittanceReportTable.dftm.addRow(data.get(i)); }
		 */
		JDialoger jDialog = new JDialoger();
		jDialog.setContentPane(art);
		jDialog.setTitle(title);
		jDialog.setResizable(false);
		jDialog.Show();
	}

}
