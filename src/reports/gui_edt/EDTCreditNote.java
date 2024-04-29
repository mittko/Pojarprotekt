package reports.gui_edt;

import reports.CreditNoteReportTable;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTCreditNote extends MainPanel implements Runnable {
	private ArrayList<ArrayList<Object>> data = null;
	private final JDialog jd;
	private final String title;

	public EDTCreditNote(ArrayList<ArrayList<Object>> data, JDialog jd, String title) {
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
		/*
		 * for(int i = 0;i < data.size();i++) {
		 * AcquittanceReportTable.dftm.addRow(data.get(i)); }
		 */
		CreditNoteReportTable creditNoteReportTable =
				new CreditNoteReportTable(data);
		JDialoger jDialog = new JDialoger();
		jDialog.setContentPane(creditNoteReportTable);
		jDialog.setTitle(title);
		jDialog.setResizable(false);
		jDialog.Show();
	}

}
