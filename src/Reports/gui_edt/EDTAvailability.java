package Reports.gui_edt;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;

import Reports.ReportTableAvailability;
import run.JDialoger;

public class EDTAvailability implements Runnable {
	private ArrayList<Object[]> deliveryBeforeFirstSelectedDate = null;
	private ArrayList<Object[]> invoiceBeforeFirstSelectedDate = null;
	private ArrayList<Object[]> deliveryBetweenSelectedDates = null;
	private ArrayList<Object[]> invoiceBetweenSelectedDates = null;
	private final JDialog jd;
	private final String title;
	private final String from;
	private final String to;

	public EDTAvailability(ArrayList<Object[]> deliveryBeforeFirstSelectedDate,
			ArrayList<Object[]> invoiceBeforeFirstSelectedDate,
			ArrayList<Object[]> deliveryBetweenSelectedDates,
			ArrayList<Object[]> invoiceToDate, String from, String to,
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
