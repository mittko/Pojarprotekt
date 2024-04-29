package reports.gui_edt;

import reports.ReportTableInvoice;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTInvoice extends MainPanel implements Runnable {

	private ArrayList<Object[]> childData = null;
	private JDialog jd = null;
	private final String titles;

	public EDTInvoice(ArrayList<Object[]> data, JDialog jd, String No,
			String titles) {
		this.childData = data;
		// this.parentInvoiceMap = parentMap;
		this.jd = jd;
		this.titles = titles;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (childData == null) {
			return;
		}
		if (childData.size() > 0) {

			ReportTableInvoice irt = new ReportTableInvoice(childData);
			JDialoger jDialog = new JDialoger();
			jDialog.setContentPane(irt);
			jDialog.setTitle(titles);
			jDialog.setResizable(false);
			jDialog.Show();

		} else {
			JOptionPane.showMessageDialog(null, "Няма резултат от търсенето!");
		}
	}

}
