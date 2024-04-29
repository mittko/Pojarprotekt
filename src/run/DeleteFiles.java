package run;

import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DeleteFiles  {

	
	static final String FILES_PATH[] = {
/*		MainPanel.SERVICE_ORDER_PDF_PATH,
		    MainPanel.PROTOKOL_PDF_PATH,
			MainPanel.BRACK_PDF_PATH,
			MainPanel.INVOICE_PDF_PATH,
			MainPanel.PROFORM_PDF_PATH,
			MainPanel.ACQUITTANCE_PDF_PATH,*/
			MainPanel.BARCODE_PDF_PATH};
	
	public DeleteFiles() {}
	
	public void removeTmpFile() {
		remover r = new remover(FILES_PATH);
		r.execute();
	}
	
	static class remover extends SwingWorker {

		private String[] files;
		final int TEMP_DAYS = 1;
	    final JDialog jd = new JDialog();
	    final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		public remover(String[] files) {
			this.files = files;
		}
		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			try {
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			deleteTempFiles(files);
			} finally {
				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			return null;
		}
		public void deleteTempFiles(String[] FILES_PATH) {
			Date currDay = MyGetDate.getDateFromString(MyGetDate.getReversedSystemDate());
			Date stampDay;

			for (String s : FILES_PATH) {
				File tempFile = new File(s);
				File[] names = tempFile.listFiles();
				for (File name : names) {
					if (name.getName().endsWith(".pdf")) {
						stampDay = MyGetDate.getDateFromString(sdf.format(name.lastModified()));
						String differents = MyGetDate.getDifference(stampDay, currDay);
						if (Integer.parseInt(differents) >= TEMP_DAYS) {
							//	System.out.println(names[j].getName());
							name.delete();
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    DeleteFiles r = new DeleteFiles();
    r.removeTmpFile();
	}

}
