package reports.workers;

import java.awt.Cursor;
import java.util.TreeMap;

import javax.print.PrintService;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import http.RequestCallback2;
import utils.ChoisePrinterDialog;
import workingbook.workers.ProtokolPrinter;

public class PrintProtokolWorker {

	private DefaultTableModel dftm = null;
	private final TreeMap<Object,Integer> partsMap;
	//private String[] clData = null;
	private String protokolNumber = null;
	private final int startIndex;
	private final int endIndex;
	private final JDialog jd;
	private final String protokolDate;
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

	public Object print()  {
		// TODO Auto-generated method stub
		try {
			PrintService ps = ChoisePrinterDialog.showPrinters();
				if(ps != null) {
				ProtokolPrinter protokolPrinter = new ProtokolPrinter(protokolNumber,
						startIndex,endIndex, protokolDate);
				protokolPrinter.printProtokol2815(dftm, partsMap, ps, null);
				}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return null;
	}

}
