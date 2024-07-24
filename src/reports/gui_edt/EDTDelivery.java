package reports.gui_edt;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import reports.ReportTableDelivery;
import run.JDialoger;

public class EDTDelivery<T> implements Runnable {
	private ArrayList<T> data = null;
	private final JDialog jd;
	private final String title;

	public EDTDelivery(ArrayList<T> data, JDialog jd, String title) {
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
			JOptionPane.showMessageDialog(null, "���� �������� �� ���������!");
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
