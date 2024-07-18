package reports.gui_edt;

import reports.ReportTableSO_Pr_Br;
import run.JDialoger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EDTSO_Pr_Br<T> implements Runnable {
	private final ArrayList<T> data;
	private final JDialog jd;
	private final String title;
	private final String destination;

	public EDTSO_Pr_Br(ArrayList<T> data, JDialog jd, String No,
			String title, String destination) {
		this.data = data;
		this.jd = jd;
		this.title = title;
		this.destination = destination;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (data == null) {
			return;
		}
		if (data.size() > 0) {
			ReportTableSO_Pr_Br<T> rt = new ReportTableSO_Pr_Br<T>(data, destination);
			JDialoger jDialog = new JDialoger();
			jDialog.setContentPane(rt);
			jDialog.setTitle(title);
			jDialog.setResizable(false);
			jDialog.Show();

		} else {
			JOptionPane.showMessageDialog(null, "Няма резултат от търсенето!");
		}
	}
}
