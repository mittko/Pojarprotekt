package Reports.ReportsWorkers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import ExportToExcell.WriteToExcellFile;

public class ExportToExcellWorkerInvocie extends SwingWorker {

	private DefaultTableModel reportTableModel;
	private String outputFile;
	private JDialog jd;

	public ExportToExcellWorkerInvocie(DefaultTableModel dftm,
			String outputFile, JDialog jd) {
		this.reportTableModel = dftm;
		this.outputFile = outputFile;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
			WriteToExcellFile export = new WriteToExcellFile();
			export.saveInExcelInvoice(reportTableModel, outputFile);

		} finally {
			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return null;
	}

}
