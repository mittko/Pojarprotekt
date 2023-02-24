package Reports;

import Reports.ReportsRenderers.InvoiceTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerInvocie;
import Reports.ReportsWorkers.PrintInvoiceAndProformWorker;
import utility.LoadIcon;
import utility.MainPanel;
import utility.TooltipButton;

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

public class ReportTableInvoice extends MainPanel {

	// private TreeMap<String, ParentInvoiceInfo> parentMap = null;
	private DefaultTableModel dftm = null;
	private JTable table = null;
	// private int CURRENT_ROW = 0;
	private final String CURRENT_INVOICE = null;
	private int SELECTED_INDEX = -1;
	// private ParentInvoiceInfo info = null;
	private String TITLE;
	private String PATH;
	public static int MOUSE_MOTION_ROW = -1;

	public ReportTableInvoice(ArrayList<Object[]> childData, boolean isInvoiceReport) {
		MOUSE_MOTION_ROW = -1;

		// this.parentMap = parentMap;

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		northPanel.setPreferredSize(new Dimension(this.WIDTH - 20, 50));

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
						// System.out.printf("%s %s\n", TITLE, PATH);
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
					PrintInvoiceAndProformWorker invoiceAndProformWorker = new PrintInvoiceAndProformWorker(
							null, dftm, jd, selectedIndexes[0],
							selectedIndexes.length, TITLE, PATH);//
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
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		buttonPanel.add(printerButton);
		buttonPanel.add(exportToExcellButton);

		northPanel.add(buttonPanel);

		JPanel centerPanel = new JPanel();

		centerPanel.setLayout(new FlowLayout());
		dftm = new DefaultTableModel(new String[] {
				"\u2116 на Фактура/Проформа", "Начин на плащане", "Отстъпка",
				"Стойност", "Клиент", "Оператор", "Дата", "\u2116 на Протокол","Наименование",
				"\u2116 на Фактура/Проформа", "Артикул", "Мерна ед.",
				"Количество", "Ед. Цена", "Сума", "Клиент", "Контрагент",
				"Фактура по доставка" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if(!isInvoiceReport) {
			// В проформата няма колона нименование
			dftm = new DefaultTableModel(new String[]{
					"\u2116 на Фактура/Проформа", "Начин на плащане", "Отстъпка",
					"Стойност", "Клиент", "Оператор", "Дата", "\u2116 на Протокол",
					"\u2116 на Фактура/Проформа", "Артикул", "Мерна ед.",
					"Количество", "Ед. Цена", "Сума", "Клиент", "Контрагент",
					"Фактура по доставка"}, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		}

		for (Object[] childDatum : childData) {
			dftm.addRow(childDatum);
		}


		table = new JTable(dftm);
		table.setDefaultRenderer(Object.class, new InvoiceTableRenderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				table.repaint();
				SELECTED_INDEX = table.getSelectedRow();
			}
		});
		// hide dublicate columns
		table.getColumnModel().getColumn(9).setMinWidth(0);
		table.getColumnModel().getColumn(9).setMaxWidth(0);
		table.getColumnModel().getColumn(9).setWidth(0);
		table.getColumnModel().getColumn(15).setMinWidth(0);
		table.getColumnModel().getColumn(15).setMaxWidth(0);
		table.getColumnModel().getColumn(15).setWidth(0);

		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// JTable rowTable = new RowNumberTable(table); //****

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
