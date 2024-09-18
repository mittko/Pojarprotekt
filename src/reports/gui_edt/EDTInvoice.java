package reports.gui_edt;

import reports.ReportTableInvoice;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTInvoice<T> extends MainPanel implements Runnable {

	private ArrayList<T> childData = null;

	private final String titles;

	public EDTInvoice(ArrayList<T> data,  String No,
			String titles) {
		this.childData = data;
		// this.parentInvoiceMap = parentMap;
		this.titles = titles;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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
