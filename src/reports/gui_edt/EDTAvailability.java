package reports.gui_edt;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;

import reports.ReportTableAvailability;
import run.JDialoger;

public class EDTAvailability<T> implements Runnable {
	private ArrayList<T> deliveryBeforeFirstSelectedDate = null;
	private ArrayList<T> invoiceBeforeFirstSelectedDate = null;
	private ArrayList<T> deliveryBetweenSelectedDates = null;
	private ArrayList<T> invoiceBetweenSelectedDates = null;
	private final JDialog jd;
	private final String title;
	private final String from;
	private final String to;

	public EDTAvailability(ArrayList<T> deliveryBeforeFirstSelectedDate,
			ArrayList<T> invoiceBeforeFirstSelectedDate,
			ArrayList<T> deliveryBetweenSelectedDates,
			ArrayList<T> invoiceToDate, String from, String to,
			JDialog jd, String title) {
		this.deliveryBeforeFirstSelectedDate = deliveryBeforeFirstSelectedDate;
		this.invoiceBeforeFirstSelectedDate = invoiceBeforeFirstSelectedDate;
		this.deliveryBetweenSelectedDates = deliveryBetweenSelectedDates;
		this.invoiceBetweenSelectedDates = invoiceToDate;
		this.from = from;
		this.to = to;
		this.jd = jd;
		this.title = title;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		ReportTableAvailability art = new ReportTableAvailability(
				deliveryBeforeFirstSelectedDate, invoiceBeforeFirstSelectedDate, deliveryBetweenSelectedDates,
				invoiceBetweenSelectedDates, from, to, title);
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
