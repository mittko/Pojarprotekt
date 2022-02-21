package Reports.ReportsWorkers;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import PDF.Invoice.InvoicePDFFromReports;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;

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
			String[] �������� = { " ��������", "", "" };
			String timeStamps[] = { MyGetDate.getTimeStamp() + "a",
					MyGetDate.getTimeStamp() + "b", MyGetDate.getTimeStamp() + "c" };
			int[] copies = { 1, 1 };
			for (int i = 0; i < 2; i++) {
				InvoicePDFFromReports pdf = new InvoicePDFFromReports();
				isCreated = pdf.createInvoicePDF(clientInfo, Number,
						timeStamps[i], datePdf, payment, dftm, PATH + "\\"
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

	float width = 8.3f * 72f;// 595f
	float height = 11.7f * 72f; // 842f
	float tolerance = 1f;

	/*
	 * private void resize2(String inFile, String outFile) { Document document =
	 * new Document(); document.open();
	 * 
	 * PdfReader reader = null; PdfStamper stamper = null; try { reader = new
	 * PdfReader(inFile); stamper = new PdfStamper(reader, new
	 * FileOutputStream(outFile)); } catch (IOException e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); } catch
	 * (DocumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * int editingPage = 1;
	 * 
	 * PdfContentByte cb = stamper.getOverContent(editingPage); cb.rectangle(10,
	 * 10, 550, 30); cb.setRGBColorFill(255, 255, 255); cb.fill();
	 * 
	 * // PdfContentByte cByte = stamper.getOverContent(editingPage); //
	 * editFooterText(cByte); reader.selectPages(Integer.toString(editingPage));
	 * 
	 * Rectangle pageSize = reader.getPageSize(1); float heightSize =
	 * Math.round(Utilities.pointsToInches(pageSize.getHeight())); float
	 * widthSize = Math.round(Utilities.pointsToInches(pageSize.getWidth()));
	 * System.out.println(widthSize + " ---- " + heightSize); //check page is A4
	 * if(heightSize == 11.7f && widthSize == 8.3f){
	 * System.out.println("isA4Sized = true"); } else {
	 * System.out.println("isA4Sized = false"); } try { stamper.close(); } catch
	 * (DocumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } document.close(); }
	 */
	// private void resize(String path, String pdf) {
	// PdfReader reader = null;
	// try {
	// reader = new PdfReader(path + pdf);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// for (int i = 1; i <= reader.getNumberOfPages(); i++)
	// {
	// Rectangle cropBox = reader.getCropBox(i);
	// float widthToAdd = width - cropBox.getWidth();
	// float heightToAdd = height - cropBox.getHeight();
	// if (Math.abs(widthToAdd) > tolerance || Math.abs(heightToAdd) >
	// tolerance)
	// {
	// System.out.println("in loop");
	// float[] newBoxValues = new float[] {
	// cropBox.getLeft() - widthToAdd / 2,
	// cropBox.getBottom() - heightToAdd / 2,
	// cropBox.getRight() + widthToAdd / 2,
	// cropBox.getTop() + heightToAdd / 2
	// };
	// PdfArray newBox = new PdfArray(newBoxValues);
	//
	// PdfDictionary pageDict = reader.getPageN(i);
	// pageDict.put(PdfName.CROPBOX, newBox);
	// pageDict.put(PdfName.MEDIABOX, newBox);
	// }
	// }
	// //reader is a pdfReader instance and pageNumber is the page in the loop
	// Rectangle pageSize = reader.getPageSize(1);
	// float heightSize =
	// Math.round(Utilities.pointsToInches(pageSize.getHeight()));
	// float widthSize =
	// Math.round(Utilities.pointsToInches(pageSize.getWidth()));
	// System.out.println(widthSize + " ---- " + heightSize);
	// //check page is A4
	// if(heightSize == 11.7f && widthSize == 8.3f){
	// System.out.println("isA4Sized = true");
	// } else {
	// System.out.println("isA4Sized = false");
	// }
	//
	// PdfStamper stamper = null;
	// try {
	// stamper = new PdfStamper(reader, new FileOutputStream(path +
	// "success.pdf"));
	// stamper.close();
	//
	// } catch (FileNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (DocumentException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// }
}
