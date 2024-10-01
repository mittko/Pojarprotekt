package workingbook.workers;

import http.RequestCallback2;
import workingbook.View;
import mydate.MyGetDate;
import utils.ChoisePrinterDialog;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.TreeMap;


public class PrintProtokolWorker extends SwingWorker {
	
	private String PROTOKOL_NUMBER = null;
	private JDialog jd = null;
	private DefaultTableModel dftm = null;
	private TreeMap<Object,Integer> partsMap = null;
	private boolean pdf = false;
	private PrintService ps;
	
	public PrintProtokolWorker(DefaultTableModel dftm,JDialog jd,
			String protokolNumber,TreeMap<Object,Integer> partsMap) {
		this.jd = jd;
		this.PROTOKOL_NUMBER = protokolNumber;
		this.dftm = dftm;
		this.partsMap = partsMap;
	}

	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub

            ps = ChoisePrinterDialog.showPrinters();
            if(ps != null) {
            ProtokolPrinter protokolPrinter = 
            		new ProtokolPrinter(PROTOKOL_NUMBER,0,dftm.getRowCount(), 
            				MyGetDate.getReversedSystemDate());
            pdf = protokolPrinter.printProtokol2815(dftm, partsMap, ps, new RequestCallback2() {
				@Override
				public <T> void callback(T t) {
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					Boolean success = (Boolean) t;
					if(success) {
						if(pdf) {
							dftm.setRowCount(0);
							if(View.partsList.getModel().getSize() > 0) {
								DefaultListModel<Object> listModel = (DefaultListModel<Object>)
										View.partsList.getModel();
								listModel.removeAllElements();
							}
							View.printProtokolButton.setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(null, "Грешка при създаването на документа");
						}
					}
				}
			});
            }

		return null;
	}
	   
}
