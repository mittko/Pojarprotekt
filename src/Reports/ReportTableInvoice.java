package Reports;

import Exceptions.ErrorDialog;
import Reports.ReportsRenderers.InvoiceTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerInvocie;
import Reports.ReportsWorkers.PrintReportsForInvoiceDocumentsType;
import Reports.ReportsWorkers.RestoreQuantity;
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

public class ReportTableInvoice extends MainPanel {

	private DefaultTableModel dftm = null;
	private JTable table;
	private final String CURRENT_INVOICE = null;
	private int SELECTED_INDEX = -1;
	private String TITLE;
	private String PATH;
	public static int MOUSE_MOTION_ROW = -1;

	public ReportTableInvoice(ArrayList<Object[]> childData) {
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

				final HashSet<String> set = CreditNoteTable.getCreditNotesNumberSet();
				boolean isDocumentWritten = set.contains(invoiceNumOfDocument);
				if(isDocumentWritten) {
					ErrorDialog.showErrorMessage("Вече има записано кредитно известие за този документ");
				} else {
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
										final HashSet<String> set = CreditNoteTable.getCreditNotesNumberSet();
										final String creditNoteNumOfDocument = String.valueOf(set.size()+1);

										RestoreQuantity restoreQuantity =
												new RestoreQuantity(dftm, selectedIndexes[0], selectedIndexes.length,
														creditNoteNumOfDocument, new MyCallback<Object>() {
													@Override
													public void call(Object obj) {
														JDialoger jDialoger = new JDialoger();
														CreditNoteDialog creditNoteDialog1 = new CreditNoteDialog(
																client,
																invoiceNumOfDocument,
																"Желаете ли да принтирате кредитно известие ?",
																new MyCallback<Object>() {
																	@Override
																	public void call(Object s) {

																		JDialog jd = (JDialog) SwingUtilities
																				.getWindowAncestor(ReportTableInvoice.this);
																		jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

																		//	PrintService ps = ChoisePrinterDialog.showPrinters();
																		//	if (ps == null) {
																		//		jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
																		//		return;
																		//	}
																		//	if (ps != null) {

																		TITLE = "Кредитно Известие";
																		PATH = MainPanel.CREDIT_NOTE_PDF_PATH;

																		Integer[] selectedIndexes = getSelectedIndexes(
																				SELECTED_INDEX, 0);

																		System.out.println(TITLE);
																		PrintReportsForInvoiceDocumentsType invoiceAndProformWorker
																				= new PrintReportsForInvoiceDocumentsType(
																				null,
																				creditNoteNumOfDocument,
																				dftm, jd, selectedIndexes[0],
																				selectedIndexes.length, TITLE, PATH
																				, MyGetDate.getReversedSystemDate());
																		invoiceAndProformWorker.execute();

																	}
																}
														);
														jDialoger.setContentPane(creditNoteDialog1);
														jDialoger.Show();
													}
												});
										restoreQuantity.execute();

									}
								}
							});
					dialoger.setContentPane(creditNoteDialog);
					dialoger.Show();
				}

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


		for (Object[] childDatum : childData) {
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
