package Reports.ReportsWorkers;

import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import ExportToExcell.WriteToExcellFile;

public class ExportToExcellWorkerNewExtinguishers extends SwingWorker {

	private JTable table;
	private DefaultTableModel dftm;
	private String outputFile;
	private JDialog jd;

	public ExportToExcellWorkerNewExtinguishers(JTable table,
			DefaultTableModel dftm, String outputFile, JDialog jd) {
		this.table = table;
		this.dftm = dftm;
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
			export.saveInExcelNewExtinguishers(table, dftm, outputFile);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return null;
	}

}
