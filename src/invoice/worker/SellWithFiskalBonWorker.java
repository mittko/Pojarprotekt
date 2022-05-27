package invoice.worker;

import invoice.Fiskal.CreateBon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SellWithFiskalBonWorker extends SwingWorker {

	private JDialog jd;
	private ArrayList<String> commandList;
	private CreateBon bon;

	public SellWithFiskalBonWorker(ArrayList<String> commandList,
			CreateBon bon, JDialog jd) {
		this.commandList = commandList;
		this.bon = bon;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			bon.executeFiskalOperation(commandList);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
