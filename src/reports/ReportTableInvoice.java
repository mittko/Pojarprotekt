package reports;

import db.artikul.Artikuli_DB;
import exceptions.ErrorDialog;
import http.reports.GetReportsService;
import models.BodyList;
import models.CreditNoteBody;
import models.InvoiceReports;
import reports.renderers.InvoiceTableRenderer;
import reports.workers.ExportToExcellWorkerInvocie;
import reports.workers.PrintReportsForInvoiceDocumentsType;
import reports.workers.RestoreQuantity;
import db.creditnote.CreditNoteTable;
import mydate.MyGetDate;
import run.JDialoger;
import utils.LoadIcon;
import utils.MainPanel;
import utils.MyCallback;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class ReportTableInvoice<T> extends MainPanel {

	private DefaultTableModel dftm = null;
	private final JTable table;
	private final String CURRENT_INVOICE = null;
	private int SELECTED_INDEX = -1;
	private String TITLE;
	private String PATH;
	public static int MOUSE_MOTION_ROW = -1;

	public ReportTableInvoice(ArrayList<T> childData) {
		MOUSE_MOTION_ROW = -1;

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		TooltipButton printerButton = new TooltipButton("Генерирай PDF документ");

		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_INDEX == -1) {
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableInvoice.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				//	PrintService ps = ChoisePrinterDialog.showPrinters();
				//	if (ps == null) {
				//		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				//		return;
				//	}
				//	if (ps != null) {

				switch (jd.getTitle()) {
					case "Справки Фактура ПОЖАРПРОТЕКТ":
						TITLE = "ФАКТУРА";
						PATH = MainPanel.INVOICE_PDF_PATH;
						break;
					case "Справки Проформа ПОЖАРПРОТЕКТ":
						TITLE = "ПРОФОРМА";
						PATH = MainPanel.PROFORM_PDF_PATH;
						break;
					default:
						break;
				}

				Integer[] selectedIndexes = getSelectedIndexes(
						SELECTED_INDEX, 0);
				PrintReportsForInvoiceDocumentsType invoiceAndProformWorker = new PrintReportsForInvoiceDocumentsType(
						null,
						dftm.getValueAt(selectedIndexes[0], 0).toString(),
						dftm, jd, selectedIndexes[0],
						selectedIndexes.length, TITLE, PATH,dftm.getValueAt(selectedIndexes[0], 6).toString());//
				invoiceAndProformWorker.execute();

			}
			//	}

		});

		TooltipButton exportToExcellButton = new TooltipButton();
		exportToExcellButton.setPreferredSize(new Dimension(
				(int) (printerButton.getPreferredSize().getWidth() * 0.3),
				(int) (printerButton.getPreferredSize().getHeight())));
		exportToExcellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		exportToExcellButton.setAutoSizedIcon(exportToExcellButton,
				new LoadIcon().setIcons(excellImage));
		exportToExcellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableInvoice.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				String outputFile = null;
				switch (jd.getTitle()) {
					case "Справки " + "Фактура ПОЖАРПРОТЕКТ":
						outputFile = "Фактури ПОЖАРПРОТЕКТ.xls";
						break;
					case "Справки " + "Проформа ПОЖАРПРОТЕКТ":
						outputFile = "Проформи ПОЖАРПРОТЕКТ.xls";
						break;
					default:
						break;
				}
				ExportToExcellWorkerInvocie export = new ExportToExcellWorkerInvocie(
						dftm, outputFile, jd);
				export.execute();

			}

		});
		TooltipButton creditNoteButton = new TooltipButton();
		creditNoteButton.setPreferredSize(new Dimension(
				(int)(printerButton.getPreferredSize().getWidth() * 0.3),
				(int)(printerButton.getPreferredSize().getHeight())));
		creditNoteButton.setToolTipText(getHTML_Text("Кредитно Известие"));
		creditNoteButton.setAutoSizedIcon(creditNoteButton,new LoadIcon().setIcons(invoiceImage));
		creditNoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final String client = dftm.getValueAt(SELECTED_INDEX,4).toString();
				final String invoiceNumOfDocument = dftm.getValueAt(SELECTED_INDEX,0).toString();

					JDialoger dialoger = new JDialoger();
					CreditNoteDialog creditNoteDialog = new CreditNoteDialog(client,
							invoiceNumOfDocument,
							"Желаете ли да създадете кредитно известие за този документ ?",
							new MyCallback<Object>() {
								@Override
								public void call(Object cmd) {
									if(cmd.equals("Да")) {
										Integer[] selectedIndexes = getSelectedIndexes(
												SELECTED_INDEX, 0);

										int startRow = selectedIndexes[0];
										int endRow = selectedIndexes.length;

										ArrayList<CreditNoteBody> creditNoteList = new ArrayList<>();

										for (int i = 0; i < endRow; i++) {

											String invoiceNumOfDocument = dftm.getValueAt(i + startRow, 0).toString();
											String payment = dftm.getValueAt(i + startRow, 1).toString();
											String discount = dftm.getValueAt(i + startRow, 2).toString();
											String sum = dftm.getValueAt(i + startRow, 3).toString();
											String client = dftm.getValueAt(i + startRow, 4).toString();
											String saller = dftm.getValueAt(i + startRow, 5).toString();
											String date = dftm.getValueAt(i + startRow, 6).toString();
											String protokolNumber = dftm.getValueAt(i + startRow, 7).toString();

											String artikul = dftm.getValueAt(i + startRow, 9)+"";
											String med = dftm.getValueAt(i + startRow, 10).toString();
											String quantity = dftm.getValueAt(i + startRow, 11).toString();
											String price = dftm.getValueAt(i + startRow, 12).toString();
											String value = dftm.getValueAt(i + startRow, 13).toString();

											String kontragent = dftm.getValueAt(i + startRow, 15).toString();
											String invoiceByKontragent = dftm.getValueAt(i + startRow, 16).toString();

											CreditNoteBody creditNoteBody = new CreditNoteBody();
											creditNoteBody.setId(invoiceNumOfDocument);
											creditNoteBody.setPayment(payment);
											creditNoteBody.setDiscount(Integer.parseInt(discount));
											creditNoteBody.setSum(Float.parseFloat(sum));
											creditNoteBody.setClient(client);
											creditNoteBody.setSaller(saller);
											creditNoteBody.setDate(date);
											creditNoteBody.setProtokol_id(protokolNumber);
											creditNoteBody.setArtikul(artikul);
											creditNoteBody.setMed(med);
											creditNoteBody.setQuantity(Integer.parseInt(quantity));
											creditNoteBody.setPrice(Float.parseFloat(price));
											creditNoteBody.setValue(Float.parseFloat(value));
											creditNoteBody.setKontragent(kontragent);
											creditNoteBody.setInvoiceByKontragent(invoiceByKontragent);
											creditNoteBody.setCredit_note_date(MyGetDate.getReversedSystemDate());

											creditNoteList.add(creditNoteBody);

										}

										GetReportsService service = new GetReportsService();
										BodyList bodyList = new BodyList();
										bodyList.setList(creditNoteList);
										service.createCreditNote(bodyList);


									}
								}
							});
					dialoger.setContentPane(creditNoteDialog);
					dialoger.Show();


			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		buttonPanel.add(printerButton);
		buttonPanel.add(exportToExcellButton);
		buttonPanel.add(creditNoteButton);

		northPanel.add(buttonPanel);

		JPanel centerPanel = new JPanel();

		centerPanel.setLayout(new FlowLayout());
		dftm = new DefaultTableModel(new String[] {
				"№ на Фактура/Проформа", "Начин на плащане", "Отстъпка",
				"Стойност", "Клиент", "Оператор", "Дата", "№ на Протокол",
				"№ на Фактура/Проформа", "Артикул", "Мерна ед.",
				"Количество", "Ед. Цена", "Сума", "Клиент", "Контрагент",
				"Фактура по доставка" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};


		for (T t : childData) {
			InvoiceReports invoiceReports = (InvoiceReports)t;
			Object[] childDatum = new Object[dftm.getColumnCount()];
			childDatum[0] = invoiceReports.getId();
			childDatum[1] = invoiceReports.getPayment();
			childDatum[2] = invoiceReports.getDiscount();
			childDatum[3] = invoiceReports.getValue();
			childDatum[4] = invoiceReports.getClient();
			childDatum[5] = invoiceReports.getSaller();
			childDatum[6] = invoiceReports.getDate();
//			private String id;
//			private String payment;
//			private String discount;
//			private String value;
//			private String client;
//			private String saller;
//			private String date;


			// child table varaiables
			childDatum[7] = invoiceReports.getProtokol();
			childDatum[8] = invoiceReports.get_id();
			childDatum[9] = invoiceReports.getMake();
			childDatum[10] = invoiceReports.getMed();
			childDatum[11] = invoiceReports.getQuantity();
			childDatum[12] = invoiceReports.getPrice();
			childDatum[13] = invoiceReports.get_value();

			childDatum[14] = invoiceReports.get_client();
			childDatum[15] = invoiceReports.getKontragent();
			childDatum[16] = invoiceReports.getInvoiceByKontragent();

//			private String _id;
//			private String make;
//			private String med;
//			private String quantity;
//			private String price;
//			private String _value;
//			private String _client;
			dftm.addRow(childDatum);
		}

		table = new JTable(dftm);
		table.setDefaultRenderer(Object.class, new InvoiceTableRenderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				SELECTED_INDEX = table.getSelectedRow();
				table.repaint();
			}
		});
		// hide dublicate columns
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setWidth(0);
		table.getColumnModel().getColumn(14).setMinWidth(0);
		table.getColumnModel().getColumn(14).setMaxWidth(0);
		table.getColumnModel().getColumn(14).setWidth(0);

		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH - 50, (HEIGHT - 70)));

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);
		this.add(basePanel);
	}

	private Integer[] getSelectedIndexes(int index, int targetColumn) {
		ArrayList<Integer> listIndexes = new ArrayList<Integer>();
		// get same elements above curr element
		for (int i = index - 1; i >= 0; i--) {
			if (dftm.getValueAt(index, targetColumn).equals(
					dftm.getValueAt(i, targetColumn))) {
				listIndexes.add(0, i);
			} else {
				break;
			}
		}

		// add curr element into list
		listIndexes.add(index);

		for (int i = index + 1; i < dftm.getRowCount(); i++) {
			if (dftm.getValueAt(index, targetColumn).equals(
					dftm.getValueAt(i, targetColumn))) {
				listIndexes.add(i);
			} else {
				break;
			}
		}

		return listIndexes.toArray(new Integer[listIndexes.size()]);
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50;// 200; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			columnModel.getColumn(column).setPreferredWidth(
					width + getFontSize());
		}

	}

}
