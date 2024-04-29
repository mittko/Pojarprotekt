package reports.gui_edt;

import reports.ReportTableNewExtinguishers;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTNewExtinguishers extends MainPanel implements Runnable {
	private ArrayList<Object[]> data = null;
	private final JDialog jd;
	private final String title;

	public EDTNewExtinguishers(ArrayList<Object[]> data, JDialog jd,
			String title) {
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
		ReportTableNewExtinguishers ext = new ReportTableNewExtinguishers(data);
		/*
		 * for(int i = 0;i < data.size();i++) {
		 * AcquittanceReportTable.dftm.addRow(data.get(i)); }
		 */
		JDialoger jDialog = new JDialoger();
		jDialog.setContentPane(ext);
		jDialog.setTitle(title);
		jDialog.setResizable(false);
		jDialog.Show();
	}

}
