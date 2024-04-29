package reports.workers;

import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import jxl.write.WriteException;
import exportoexcell.WriteToExcellFile;

public class ExportToExcellWorkerAvailable extends SwingWorker {

	private final JTable table;
	private final DefaultTableModel dftm;
	private final String outputFile;
	private final JDialog jd;
	private final String from;
	private final String to;

	public ExportToExcellWorkerAvailable(JTable table, DefaultTableModel dftm,
			String from, String to, String outputFile, JDialog jd) {
		this.table = table;
		this.dftm = dftm;
		this.from = from;
		this.to = to;
		this.outputFile = outputFile;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		WriteToExcellFile export = new WriteToExcellFile();
		try {
			export.saveInExcelAvailables(table, dftm, from, to, outputFile);
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return null;
	}

}
