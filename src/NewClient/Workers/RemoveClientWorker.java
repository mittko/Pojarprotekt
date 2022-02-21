package NewClient.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.RemoveTable;

public class RemoveClientWorker extends SwingWorker {

	private String clientName;
	private String destination;
	private String name;
	private JDialog jd;
	private int delete;
	public RemoveClientWorker(String clientName, String destination,String name, JDialog jd) {
		this.clientName = clientName;
		this.destination = destination;
		this.name = name;
		this.jd = jd;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
		delete = RemoveTable.deleteClient(clientName, destination, name);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(delete == 1) {
						JOptionPane.showMessageDialog(null, "Данните са изтрити успешно!");
					} else {
						JOptionPane.showMessageDialog(null, "Възникна грешка при изтриването на данните !", "Error", JOptionPane.ERROR_MESSAGE);
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
		}
		return null;
	}

}
