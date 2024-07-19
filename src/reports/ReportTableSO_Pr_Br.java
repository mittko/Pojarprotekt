package reports;

import models.BrakReports;
import models.ProtokolReports;
import models.ServiceOrderReports;
import reports.renderers.JTableX;
import reports.renderers.ProtokolBrackTableRenderer;
import reports.renderers.ProtokolTableRenderer;
import reports.renderers.ServiceTableRenderer;
import reports.workers.ExportToExcellWorkerSO_PR_BR;
import reports.workers.PrintProtokolBrackWorker;
import reports.workers.PrintProtokolWorker;
import reports.workers.PrintSOWorker;
import run.JustFrame;
import utils.LoadIcon;
import utils.MainPanel;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class ReportTableSO_Pr_Br<T> extends MainPanel {

	private DefaultTableModel dftm = null;
	private JTableX table = null;

	private String[] titles = null;
	private int SELECTED_INDEX = -1;

	public ReportTableSO_Pr_Br(ArrayList<T> data, String destination) {
		this.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		TooltipButton printerButton = new TooltipButton("Генерирай PDF документ");

		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (SELECTED_INDEX == -1)
					return;

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableSO_Pr_Br.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				String title = jd.getTitle();

				switch (title) {
				case "Справки " + ReportDialog.SO_Title + " ПОЖАРПРОТЕКТ":

					Integer[] selectedIndexesForSO = getSelectedIndexes(
							SELECTED_INDEX, 11);
					TreeMap<Object, Integer> soMapForSO = getSOMap(selectedIndexesForSO);
					String serviceNumber = dftm.getValueAt(SELECTED_INDEX, 11)
							.toString();
					String currentClient = dftm.getValueAt(SELECTED_INDEX, 0)
							.toString();
					PrintSOWorker printSO = new PrintSOWorker(serviceNumber,
							currentClient, jd, soMapForSO);
					printSO.execute();
					break;
				case "Справки " + ReportDialog.Protokol_Title + " ПОЖАРПРОТЕКТ":
					Integer[] selectedIndexesForProtokol = getSelectedIndexes(
							SELECTED_INDEX, 12);
					TreeMap<Object, Integer> soMapForProtokol = getSOMap(selectedIndexesForProtokol);

					String protokolNumber = dftm.getValueAt(SELECTED_INDEX, 12)
							.toString();
					String protokolDate = dftm.getValueAt(SELECTED_INDEX, 14)
							.toString();

					PrintProtokolWorker printProtokol = new PrintProtokolWorker(
							dftm, soMapForProtokol, protokolNumber,
							selectedIndexesForProtokol[0],
							selectedIndexesForProtokol.length, protokolDate, jd);
					printProtokol.execute();
					break;
				case "Справки " + ReportDialog.Brack_Title + " ПОЖАРПРОТЕКТ":
					Integer[] selectedIndexesForBrack = getSelectedIndexes(
							SELECTED_INDEX, 8);

					String brackNumber = dftm.getValueAt(SELECTED_INDEX, 8)
							.toString();
					String brackDate = dftm.getValueAt(SELECTED_INDEX, 10)
							.toString();
					PrintProtokolBrackWorker printBrack = new PrintProtokolBrackWorker(
							jd, brackNumber,
							allReasons(selectedIndexesForBrack), dftm,
							selectedIndexesForBrack[0],
							selectedIndexesForBrack.length, brackDate);
					printBrack.execute();
					break;
				default:
					break;
				}
			}

		});
		TooltipButton excellButton = new TooltipButton();
		excellButton.setPreferredSize(new Dimension((int) (printerButton
				.getPreferredSize().getWidth() * 0.3), (int) (printerButton
				.getPreferredSize().getHeight())));

		excellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		excellButton.setAutoSizedIcon(excellButton, new LoadIcon().setIcons(excellImage));
		excellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableSO_Pr_Br.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				String title = jd.getTitle();

				switch (title) {
				case "Справки " + ReportDialog.SO_Title + " ПОЖАРПРОТЕКТ":
					ExportToExcellWorkerSO_PR_BR excellWorker = new ExportToExcellWorkerSO_PR_BR(
							table, dftm, "Сервизна Поръчка.xls", jd);
					excellWorker.execute();
					break;
				case "Справки " + ReportDialog.Protokol_Title + " ПОЖАРПРОТЕКТ":
					ExportToExcellWorkerSO_PR_BR excellWorker2 = new ExportToExcellWorkerSO_PR_BR(
							table, dftm, "Протокол.xls", jd);
					excellWorker2.execute();
					break;
				case "Справки " + ReportDialog.Brack_Title + " ПОЖАРПРОТЕКТ":
					ExportToExcellWorkerSO_PR_BR excellWorker3 = new ExportToExcellWorkerSO_PR_BR(
							table, dftm, "Протокол Брак.xls", jd);
					excellWorker3.execute();
					break;
				default:
					break;
				}
			}

		});

		JLabel titleLabel = new JLabel();

		northPanel.add(printerButton);
		northPanel.add(excellButton);

		northPanel.add(titleLabel);

		JPanel centerPanel = new JPanel();

		switch (destination) {
			case SERVICE:
				titles = new String[]{"Клиент", "Вид", "Вместимост", "Баркод",
						"Монтажен номер", "Категория", "Марка", "ТО", "П", "ХИ",
						"Обработен", " \u2116 на Сервизна Поръчка",
						"Издал Документа", "Дата на Сервизна Поръчка",
						"Допълнителни данни"};// "Допълнителни данни"


				break;
			case PROTOKOL:
				titles = new String[]{"Клиент", "Вид", "Маса", "Баркод",
						"Монтажен номер", "Категория", "Марка", "ТО", "П", "ХИ",
						"Резервни Части", "Цена", " \u2116 на Протокол", "Техник",
						"Дата на издаване", "Контрагент", "Фактура по доставка",
						"Допълнителни данни", "Презаверен"};// "Допълнителни данни"


				break;
			case BRACK:
				titles = new String[]{"Клиент", "Вид", "Маса", "Баркод",
						"Монтажен номер", "Категория", "Марка", "Причини за Брак",
						" \u2116 на Протокол за Брак", "Техник", "Дата на издаване"};
				break;
		}
		dftm = new DefaultTableModel(titles, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};



		// fill data in table
		table = new JTableX(dftm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {

					SELECTED_INDEX = table.getSelectedRow();

					// call repaint() to set background correctly
					// after select client in table renderer (in reports table)
					table.repaint();


			}
		});

		table.setRowHeight(MainPanel.getFontSize() + 15);

		int totalFireExtinguishers = 0;
		switch (destination) {
			case SERVICE:
				table.setDefaultRenderer(Object.class, new ServiceTableRenderer());

				for (T t : data) {

					ServiceOrderReports<T> serviceOrderReports = (ServiceOrderReports)t;
					Object[] newObj = new Object[titles.length];

						newObj[0] = serviceOrderReports.getClient();
						newObj[1] = serviceOrderReports.getType();
						newObj[2] = serviceOrderReports.getWheight();
						newObj[3] = serviceOrderReports.getBarcod();
						newObj[4] = serviceOrderReports.getSerial();
						newObj[5] = serviceOrderReports.getCategory();
						newObj[6] = serviceOrderReports.getBrand();
						newObj[7] = serviceOrderReports.getT_O();
						newObj[8] = serviceOrderReports.getP();
						newObj[9] = serviceOrderReports.getHi();
						newObj[10] = serviceOrderReports.getDone();
						newObj[11] = serviceOrderReports.getNumber();
						newObj[12] = serviceOrderReports.getPerson();
						newObj[13] = serviceOrderReports.getDate();
						newObj[14] = serviceOrderReports.getAdditional_data();

					dftm.addRow(newObj);
				}
				break;
			case PROTOKOL:
				table.setDefaultRenderer(Object.class, new ProtokolTableRenderer());

                for(T t : data) {
					ProtokolReports<T> protokolReports = (ProtokolReports<T>) t;
					Object[] newObj = new Object[titles.length];

					newObj[0] = protokolReports.getClient();
					newObj[1] = protokolReports.getType();
					newObj[2] = protokolReports.getWheight();
					newObj[3] = protokolReports.getBarcod();
					newObj[4] = protokolReports.getSerial();
					newObj[5] = protokolReports.getCategory();
					newObj[6] = protokolReports.getBrand();
					newObj[7] = protokolReports.getT_O();
					newObj[8] = protokolReports.getP();
					newObj[9] = protokolReports.getHi();
					newObj[10] = protokolReports.getParts();
					newObj[11] = protokolReports.getValue();
					newObj[12] = protokolReports.getNumber();
					newObj[13] = protokolReports.getPerson();
					newObj[14] = protokolReports.getDate();
					newObj[15] = protokolReports.getKontragent();
					newObj[16] = protokolReports.getInvoiceByKontragent();
					newObj[17] = protokolReports.getAdditional_data();
					newObj[18] = protokolReports.getUptodate();

					dftm.addRow(newObj);

				}
				break;
			case BRACK:
				table.setDefaultRenderer(Object.class,
						new ProtokolBrackTableRenderer());

                for(T t : data) {

					BrakReports brakReports = (BrakReports)t;
					Object[] newObj = new Object[titles.length];
					newObj[0] = brakReports.getClient();
					newObj[1] = brakReports.getType();
					newObj[2] = brakReports.getWheight();
					newObj[3] = brakReports.getBarcod();
					newObj[4] = brakReports.getSerial();
					newObj[5] = brakReports.getCategory();
					newObj[6] = brakReports.getBrand();
					newObj[7] = brakReports.getReasons();
					newObj[8] = brakReports.getNumber();
					newObj[9] = brakReports.getTehnik();
					newObj[10] = brakReports.getDate();

					dftm.addRow(newObj);
				}
				break;
		}


		Object[] rowForTotalFireExtinguishers = {"","","","","","","","","",""
		,"","","","","","","","Общ брой пожарогасители: ",totalFireExtinguishers+""};
        dftm.addRow(rowForTotalFireExtinguishers);

		table.getTableHeader().setReorderingAllowed(false);
		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));

		centerPanel.add(scroll);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReportTableSO_Pr_Br r = new ReportTableSO_Pr_Br(null, "");
		JustFrame f = new JustFrame(r);
		f.pack();
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50;// 300; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			columnModel.getColumn(column).setPreferredWidth(
					width + getFontSize());
		}

	}

	// need to make SO
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

	// make map to create pdf service order
	private TreeMap<Object, Integer> getSOMap(Integer[] selectedIndexes) {
		TreeMap<Object, Integer> soMap = new TreeMap<Object, Integer>();

		for (Integer selectedIndex : selectedIndexes) {
			String[] parts = dftm.getValueAt(selectedIndex,10).toString().split(",");
            for(String part : parts) {
				Integer value = soMap.get(part);
				if (value == null) {
					value = 0;
				}
				soMap.put(part, value + 1);
			}
		}

		return soMap;
	}

	private HashMap<String, ArrayList<Object>> allReasons(
			Integer[] selectedIndexes) {
		HashMap<String, ArrayList<Object>> all = new HashMap<String, ArrayList<Object>>();

		for (Integer selectedIndex : selectedIndexes) {
			String[] reasons = dftm.getValueAt(selectedIndex, 7)
					.toString().split("[,]");
			ArrayList<Object> val = new ArrayList<Object>();
			Collections.addAll(val, reasons);
			all.put(dftm.getValueAt(selectedIndex, 3).toString(), val); // index
			// ->
			// 3
			// (barcod)
			/*
			 * for(String s : reasons) { s = s.trim(); if(!all.contains(s)) {
			 * all.add(s); } }
			 */
		}
		return all;
	}

}
