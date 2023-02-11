package Reports;

import Reports.ReportsRenderers.JTableX;
import Reports.ReportsRenderers.ProtokolBrackTableRenderer;
import Reports.ReportsRenderers.ProtokolTableRenderer;
import Reports.ReportsRenderers.ServiceTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerSO_PR_BR;
import Reports.ReportsWorkers.PrintProtokolBrackWorker;
import Reports.ReportsWorkers.PrintProtokolWorker;
import Reports.ReportsWorkers.PrintSOWorker;
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

public class ReportTableSO_Pr_Br extends MainPanel {

	private DefaultTableModel dftm = null;
	private JTableX table = null;

	private String[] titles = null;
	private int SELECTED_INDEX = -1;

	public ReportTableSO_Pr_Br(ArrayList<Object[]> data, String destination) {
		this.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();// GradientPanel();
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
		;
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

				System.out.println("fuck " + title);
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
				return true;
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
		switch (destination) {
			case SERVICE:
				table.setDefaultRenderer(Object.class, new ServiceTableRenderer());
				break;
			case PROTOKOL:
				table.setDefaultRenderer(Object.class, new ProtokolTableRenderer());
				break;
			case BRACK:
				table.setDefaultRenderer(Object.class,
						new ProtokolBrackTableRenderer());
				break;
		}

		// RowEditorModel rem = new RowEditorModel();
		// table.setRowEditorModel(rem);
		int totalFireExtinguishers = 0;
		for (Object[] datum : data) {
			// Object[] obj = data.get(i);
			Object[] newObj = new Object[titles.length];
			for (int init = 0; init < newObj.length; init++) {
				Object str = datum[init];
				if(init == newObj.length - 1) { // column uptodate
					if(str == null) {
						str = "не";
						totalFireExtinguishers++;
					} else {
						str = "да";
					}
				} else if (str == null) {
					str = "";
				}
				newObj[init] = str;// obj[init];
			}
			dftm.addRow(newObj);
			/*
			 * for(int j = 0;j < titles.length;j++) {
			 * if(titles[j].equals("Резервни Части")) { String[] items =
			 * obj[j].toString().split(","); rem.addEditorForRow(i, new
			 * MyComboBoxEditor(items)); } }
			 */
		}
		Object[] rowForTotalFireExtinguishers = {"","","","","","","","","",""
		,"","","","","","","","Общ брой пожарогасители: ",totalFireExtinguishers+""};
        dftm.addRow(rowForTotalFireExtinguishers);

		table.getTableHeader().setReorderingAllowed(false);
		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// JTable rowTable = new RowNumberTable(table); //****

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.getVerticalScrollBar().setUI(new YourUI());
		// scroll.getHorizontalScrollBar().setUI(new YourUI());
		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));

		// scroll.setRowHeaderView(rowTable); //****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,//****
		// rowTable.getTableHeader());//****

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
			String key = dftm.getValueAt(selectedIndex, 1) + " "
					+ table.getValueAt(selectedIndex, 2);
			Integer value = soMap.get(key);
			if (value == null) {
				value = 0;
			}
			soMap.put(key, value + 1);
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
