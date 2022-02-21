package ExportToExcell.Diary.Workers;

import java.awt.Cursor;

import javax.swing.SwingWorker;

import run.JDialoger;
import ExportToExcell.Diary.ExportDiaryIntoExcell;

public class ExportToExcelWorker extends SwingWorker {

	private String from;
	private String to;
	private JDialoger jd;
	public ExportToExcelWorker(String from , String to, JDialoger jd) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
		this.jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		ExportDiaryIntoExcell export = new ExportDiaryIntoExcell();
		export.createDiary(from, to);
		} finally {
			this.jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.jd.dispose();
		}
		return null;
	}

}
