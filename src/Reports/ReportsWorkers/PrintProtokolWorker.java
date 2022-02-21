package Reports.ReportsWorkers;

import java.awt.Cursor;
import java.util.TreeMap;

import javax.print.PrintService;
import javax.swing.JDialog;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import utility.ChoisePrinterDialog;
import WorkingBookWorkers.ProtokolPrinter;

public class PrintProtokolWorker extends SwingWorker{

	private DefaultTableModel dftm = null;
	private TreeMap<Object,Integer> partsMap;
	//private String[] clData = null;
	private String protokolNumber = null;
	private int startIndex;
	private int endIndex;
	private JDialog jd;
	private String protokolDate;
	public PrintProtokolWorker(DefaultTableModel dftm,TreeMap<Object,Integer>partsMap,
		String protokolNumber,int startIndex,int endIndex,String protokolDate, JDialog jd) {
		this.dftm = dftm;
		this.partsMap = partsMap;
		this.protokolNumber = protokolNumber;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.protokolDate = protokolDate;
		this.jd = jd;
		
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {
			PrintService ps = ChoisePrinterDialog.showPrinters();
				if(ps != null) {
				ProtokolPrinter protokolPrinter = new ProtokolPrinter(protokolNumber,
						startIndex,endIndex, protokolDate);
				protokolPrinter.printProtokol2815(dftm, partsMap,ps);
				}
		} finally {
			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return null;
	}

}
