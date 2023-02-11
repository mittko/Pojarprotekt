package admin.Parts.Workers;

import ExportToExcell.WriteToExcellFile;
import utils.MainPanel;

import javax.swing.*;

public class SeeAllPriceWorker extends SwingWorker {

	public SeeAllPriceWorker() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		 WriteToExcellFile excell = new WriteToExcellFile();
		excell.Export(MainPanel.PARTS_PRICE, "Цени на Части.xls");
		return null;
	}

}
