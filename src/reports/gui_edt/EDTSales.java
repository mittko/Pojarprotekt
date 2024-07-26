package reports.gui_edt;

import reports.ReportTableSales2;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTSales<T> extends MainPanel implements Runnable {
	private ArrayList<T> invoices = null;
	private ArrayList<T> delivery = null;

	private final JDialog jd;
	private final String title;

	public EDTSales(ArrayList<T> invoices, ArrayList<T> delivery,
			JDialog jd, String title) {
		this.invoices = invoices;
		this.delivery = delivery;
		this.jd = jd;
		this.title = title;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (invoices == null || delivery == null) {
			return;
		}
		if (invoices.size() == 0 || delivery.size() == 0) {
			JOptionPane.showMessageDialog(null, "Няма резултат от търсенето!");
			return;
		}
		ReportTableSales2 art = new ReportTableSales2(invoices, delivery, title);
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
