package Reports.ReportsWorkers;

import PDF.Invoice.InvoicePDFFromReports;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintInvoiceAndProformWorker extends SwingWorker {

	private ArrayList<String> clientInfo = null;
	private DefaultTableModel dftm = null;
	private String currentClient = null;
	private String Number = null;
	private double danOsnova = 0;
	private String payment = null;
	private String datePdf = null;
	private JDialog jd = null;
	private boolean isCreated;
	private int startIndex;
	private int endIndex;
	private String TITLE;
	private String TITLE2;
	private String PATH;
	private PrintService ps;
	private String saller;

	public PrintInvoiceAndProformWorker(PrintService ps,
			DefaultTableModel dftm, JDialog jd, int startIndex, int endIndex,
			String TITLE, String pdf_path) {
		this.ps = ps;
		this.dftm = dftm;
		this.currentClient = dftm.getValueAt(startIndex, 4).toString();
		this.Number = dftm.getValueAt(startIndex, 0).toString();
		this.danOsnova = Double.parseDouble(dftm.getValueAt(startIndex, 3)
				.toString());
		this.payment = dftm.getValueAt(startIndex, 1).toString();
		this.datePdf = dftm.getValueAt(startIndex, 6).toString();
		this.saller = dftm.getValueAt(startIndex, 5).toString();
		this.jd = jd;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.TITLE = TITLE;
		this.PATH = pdf_path;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			// get client info
			clientInfo = ClientTable.getClientDetails(currentClient);
			// System.out.printf("current client -> %s ",currentClient);
			String[] �������� = {MainPanel.personName.equals("�������������") ? " ��������" : " ��������", "", "" };
			String timeStamps[] = { MyGetDate.getTimeStamp() + "a",
					MyGetDate.getTimeStamp() + "b", MyGetDate.getTimeStamp() + "c" };
			int[] copies = { 1, 1 };
			for (int i = 0; i < 2; i++) {
				InvoicePDFFromReports pdf = new InvoicePDFFromReports();

				// this solution merge artikuls with same names but
				// may lead to errors if artikuls with same names have
				// different prices !!!
//				DefaultTableModel mergedTableModel = mergeArtikuls(dftm, startIndex, endIndex);
//				isCreated = pdf.createInvoicePDF(clientInfo, Number,
//						timeStamps[i], datePdf, payment,mergedTableModel, PATH + "\\"
//								+ TITLE + "-", TITLE, ��������[i], 0,
//						mergedTableModel.getRowCount()/*endIndex*/, saller);

				// this solution has no errors but don't merge same artikuls !!!
				isCreated = pdf.createInvoicePDF(clientInfo, Number,
						timeStamps[i], datePdf, payment,dftm, PATH + "\\"
								+ TITLE + "-", TITLE, ��������[i], startIndex,
						endIndex, saller);
				// update invoice number
				if (isCreated) {

					 OpenPDFDocument.pdfRunner(PATH + "\\" + TITLE + "-"
					 + timeStamps[i] + "-" + Number + ".pdf");

				/*	PrintWithoutOpenPdf.printWithoutDialog(PATH, "\\" + TITLE
							+ "-" + timeStamps[i] + "-" + Number + ".pdf", ps,
							copies[i]);*/

				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (!isCreated) {
						JOptionPane.showMessageDialog(null,
								"������ ��� ����������� �� ���������!");
					}
				}
			});
		}
		return null;

	}
	private DefaultTableModel mergeArtikuls(DefaultTableModel dftm, int startIndex, int stop) {
		HashMap<String, Integer> map = new HashMap<>();
		DefaultTableModel mergedData = new DefaultTableModel();
		mergedData.setColumnCount(15);
		int artikulsRow = 0;
		for(int i = 0;i < stop;i++) {
			int position = i + startIndex;
			String artikul = dftm.getValueAt(position, 9).toString();
			if(!map.containsKey(artikul)) {
				mergedData.addRow(new Object[]{
						dftm.getValueAt(position,0),
						dftm.getValueAt(position,1),
						dftm.getValueAt(position,2),
						dftm.getValueAt(position,3),
						dftm.getValueAt(position,4),
						dftm.getValueAt(position,5),
						dftm.getValueAt(position,6),
						dftm.getValueAt(position,7),
						dftm.getValueAt(position,8),
						dftm.getValueAt(position,9),
						dftm.getValueAt(position,10),
						dftm.getValueAt(position,11),
						dftm.getValueAt(position,12),
						dftm.getValueAt(position,13),
						dftm.getValueAt(position,14)
				});
				map.put(artikul, artikulsRow++);
			} else {
				int savedPos = map.get(artikul);
				int newQuantity = Integer.parseInt(dftm.getValueAt(position, 11).toString());
				int savedQuantity = Integer.parseInt(mergedData.getValueAt(savedPos, 11).toString());
				mergedData.setValueAt((savedQuantity + newQuantity)+"", savedPos, 11);
				// � ���� ����� �� �������� ���� ������ �-�� �� ����������
				// �� �� �� �������� �������� ��������
				// ������ �� �� ����� � ������ createInvoicePdf �� ����� InvoicePdfFromReports
				//System.out.println(savedQuantity + newQuantity);
			}
		}
		return mergedData;
	}
}
