package reports.gui_edt;

import reports.CreditNoteReportTable;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTCreditNote<T> extends MainPanel implements Runnable {
	private ArrayList<T> data = null;
	private final String title;

	public EDTCreditNote(ArrayList<T> data, String title) {
		this.data = data;
		this.title = title;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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
