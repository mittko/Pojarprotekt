package Reports.gui_edt;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Reports.ReportTableDelivery;
import run.JDialoger;

public class EDTDelivery implements Runnable {
	private ArrayList<Object[]> data = null;
	private JDialog jd;
	private String title;

	public EDTDelivery(ArrayList<Object[]> data, JDialog jd, String title) {
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
		ReportTableDelivery art = new ReportTableDelivery(data, title);
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
