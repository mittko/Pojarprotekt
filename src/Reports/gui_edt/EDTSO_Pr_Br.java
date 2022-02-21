package Reports.gui_edt;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import run.JDialoger;
import Reports.ReportTableSO_Pr_Br;

public class EDTSO_Pr_Br implements Runnable {
	private String No;
	private ArrayList<Object[]> data;
	private JDialog jd;
	private String title;
	private String destination;

	public EDTSO_Pr_Br(ArrayList<Object[]> data, JDialog jd, String No,
			String title, String destination) {
		this.No = No;
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
			ReportTableSO_Pr_Br rt = new ReportTableSO_Pr_Br(data, destination);
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
